'use strict';

//noinspection JSUnresolvedFunction
const Config = require('../Config'),
    Utils = require('../Utils'),
    Dictionaries = require('../Dictionaries'),
    Store = require('../Store'),
    LocalStorageService = require('./LocalStorageService'),
    SockJS = require('sockjs-client'),
    Stomp = require('stompjs');

const DataService = {
    tokenProp: 'token',
    postPutHeaders: {
        'Content-Type': 'application/json;charset=UTF-8'
    },
    deleteHeaders: {
        'Access-Control-Request-Headers': 'Origin',
        'x-fix-spring-cors': 'fix'
    },

    status: {
        badRequest: '400',
        conflict: '409',
    },

    /**
     *  Array of Functions to process each response
     */
    responseProcessors: [],

    /**
     *  Array of listener Functions that will be notified about failed request.
     *  They will be invoked with argument errorObj:{status, data}
     */
    afterRequestFailedListeners: [],

    /**
     * Makes GET request to server
     * @param url relative server url. Will be prefixed with Config.baseServiceUrl
     * @param options additional request options
     * @return {*} promise
     */
    get(url, options){
        //noinspection JSUnresolvedFunction
        return this.fetchData(new Request(Config.baseServiceUrl + url), options)
    },

    /**
     * Makes POST request to server
     * @param url relative server url. Will be prefixed with Config.baseServiceUrl
     * @param data object that will be serialized to JSON and sent to server
     * @param options additional request options
     * @return {*} promise
     */
    post(url, data, options){
        const self = this;
        //noinspection JSUnresolvedFunction
        return self.fetchData(new Request(Config.baseServiceUrl + url),
            Object.assign({
                method: 'POST',
                headers: self.postPutHeaders,
                body: JSON.stringify(data || {})
            }, options))
    },

    /**
     * Makes PUT request to server
     * @param url relative server url. Will be prefixed with Config.baseServiceUrl
     * @param data object that will be serialized to JSON and sent to server
     * @param options additional request options
     * @return {*} promise
     */
    put(url, data, options){
        const self = this;
        //noinspection JSUnresolvedFunction
        return self.fetchData(new Request(Config.baseServiceUrl + url),
            Object.assign({
                method: 'PUT',
                headers: self.postPutHeaders,
                body: JSON.stringify(data || {})
            }, options))
    },

    /**
     * Makes DELETE request to server
     * @param url relative server url. Will be prefixed with Config.baseServiceUrl
     * @param options additional request options
     * @return {*} promise
     */
    delete(url, options){
        const self = this;
        //noinspection JSUnresolvedFunction
        return self.fetchData(new Request(Config.baseServiceUrl + url),
            Object.assign({
                method: 'DELETE',
                headers: self.deleteHeaders
            }, options))
    },

    fetchData (request, init){
        init = this.appendJwtTokenHeader(init);

        const self = this;
        try {
            //noinspection JSUnresolvedFunction
            return fetch(request, init).then(
                response => {
                    self.responseProcessors.forEach(fn => fn(response));
                    if (response.ok) {
                        //noinspection JSUnresolvedFunction
                        return response.json().then(
                            data => data,
                            () => {
                                if (!init || init.method === 'GET') {
                                    return self.onAfterRequestFailed(self.error('404')); //no content returned from server
                                } else {
                                    return {};
                                }
                            }
                        )
                    } else {
                        //noinspection JSUnresolvedFunction
                        return response.json().then(
                            data => self.onAfterRequestFailed(Utils.assign(data, {status: response.status + ''}) || self.error('0')),
                            error => self.onAfterRequestFailed(self.error(response.status))
                        );
                    }
                }
            )
        } catch (e) {
            //noinspection JSUnresolvedVariable
            return self.onAfterRequestFailed({text: e.stack || e.toString()});
        }
    },

    onAfterRequestFailed(error){
        this.afterRequestFailedListeners.forEach(listener => listener(error));
        return Promise.reject(error);
    },

    appendJwtTokenHeader(init){
        let token = LocalStorageService.getToken();
        if (token) {
            init = Utils.merge(init, {
                headers: {
                    'Authorization': Config.authSchemePrefix + token
                }
            })
        }
        return init
    },

    /**
     * Append saved auth token to query part of secured resource like image, file, etc.
     * @param queryPart query part of url to be appended with token. Should be null, undefined, empty or in format: ?param1=value1&param2=value2
     * @return string modified query part in format ?param1=value1&param2=value2&token=secret
     */
    appendJwtTokenParam(queryPart){
        let token = LocalStorageService.getToken();
        if (token) {
            const tokenParam = "auth=" + token;
            if (queryPart && queryPart.startsWith("?")) {
                return queryPart + "&" + tokenParam
            } else {
                return "?" + tokenParam
            }
        }
        return queryPart
    },

    unknownErrorMessage: Dictionaries.responseStatus.byId('0').title,

    error (status){
        const statusStr = status + '',
            opt = Dictionaries.responseStatus.byId(statusStr);
        return {status: statusStr, text: opt && opt.title || this.unknownErrorMessage}
    },

    resourceUrl(url, queryPart){
        return Config.baseServiceUrl + url + this.appendJwtTokenParam(queryPart)
    },

    ajaxResource(entitiesId){
        const self = this;
        return {
            findAll(searchRequest){
                return self.get('data/' + entitiesId + '/' + Utils.objToQueryString(searchRequest))
            },
            findOne(id){
                return self.get('data/' + entitiesId + '/' + id)
            },
            create(obj){
                return self.post('data/' + entitiesId + '/', obj)
            },
            modify(obj){
                return self.put('data/' + entitiesId + '/' + obj.id, obj)
            },
            delete(id){
                return self.delete('data/' + entitiesId + '/' + (id || ''))
            }
        }
    },

    addResponseProcessor(fn){
        if (Utils.isFunction(fn)) {
            this.responseProcessors.push(fn)
        } else {
            console.error('Unable to register response processor. It should be function')
        }
    },

    removeResponseProcessor(fn){
        Utils.arr.remove(this.responseProcessors, fn)
    },

    addAfterRequestFailedListener(fn){
        if (Utils.isFunction(fn)) {
            this.afterRequestFailedListeners.push(fn)
        } else {
            console.error('Unable to register after request failed listener. It should be function')
        }
    },

    removeAfterRequestFailedListener(fn){
        Utils.arr.remove(this.afterRequestFailedListeners, fn)
    },

    socket: {
        sessionId: Utils.guid(),
        client: null,
        initPromise: null,

        init(){
            if (!DataService.socket.initPromise) {
                DataService.socket.initPromise = new Promise((resolve, reject) => {
                    if (!DataService.socket.client) {
                        const socket = new SockJS(Config.baseServiceUrl + 'ws/', [], {sessionId: () => DataService.socket.sessionId}),
                            client = Stomp.over(socket);
                        DataService.socket.client = client;

                        client.connect({},
                            frame => resolve(frame),
                            error => reject(error)
                        );
                    } else {
                        resolve(null)
                    }
                });
            }
            return DataService.socket.initPromise;
        },

        close(){
            const client = DataService.socket.client;
            if (client) {
                client.disconnect();
                DataService.socket.client = null
            }
        },

        subscribeQueue(queue, onMessage){
            DataService.socket.init().then(() => {
                DataService.socket.client.subscribe(queue + DataService.socket.sessionId, onMessage);
            })
        }
    }
};

DataService.operations = {
    crosswords: DataService.ajaxResource('crosswords')
};

module.exports = DataService;
