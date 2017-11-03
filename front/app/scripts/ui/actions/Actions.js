'use strict';

const DataService = require('../services/DataService'),
    moment = require('moment-timezone'),
    Config = require('../Config'),
    Utils = require('../Utils');

const creators = _enhanceCreators({
    toggleSelectedObject: (entity, obj) => ({entity, obj}),
    setSearchRequest: (entity, request) => ({entity, request}),


    /**
     * Action creator defining initialization of new entity, usually after move to new page
     * @param entity name of entity that is beigng initialized
     * @returns {{entity: *}}
     */
    initEntity: (entity) => ({entity}),

    /**
     * Action creator defining that ajax request is about to be initiated
     * @param entity name of entity that is fetched, modified or deleted by request
     * @param operation name of operation that is in progress
     * @param request payload of request
     * @param context context that holds request related data, but that is not send to server
     * @returns {{}} action declaration
     */
    ajaxStarted: (entity, operation, request, context) => ({entity, operation, request, context}),

    /**
     * Action creator defining that ajax response was completed
     * @param entity name of entity that is fetched, modified or deleted by request
     * @param operation name of operation that is in progress
     * @param response response data
     * @param request request data
     * @param context context that holds request related data, but that is not send to server
     * @returns {{}} action declaration
     */
    ajaxFinishedSuccess: (entity, operation, response, request, context) => ({
        entity,
        operation,
        response,
        request,
        context
    }),

    /**
     * Action creator defining that ajax response was completed
     * @param entity name of entity that is fetched, modified or deleted by request
     * @param operation name of operation that is in progress
     * @param error error data
     * @param request request data
     * @param context context that holds request related data, but that is not send to server
     * @returns {{}} action declaration
     */
    ajaxFinishedError: (entity, operation, error, request, context) => ({entity, operation, error, request, context}),


    setEntityValue: (entity, fieldId, newValue, options = defaultSetValueOptions()) => ({
        entity,
        fieldId,
        newValue,
        options
    }),
    setEntityValues: (entity, obj, options = defaultSetValueOptions()) => ({
        entity,
        obj,
        options
    }),
    addEntity: (entity, obj) => ({
        entity,
        obj
    }),
    deleteEntity: (entity, obj, index) => ({
        entity,
        obj,
        index
    }),
    setValidationMessages: (entity, messages) => ({entity, messages}),
    clearValidationMessages: (entity) => ({entity}),
    toggleNavSidebar: (opened) => ({opened}),
    setPageMode: (value) => ({value}),
    setErrorCode: (entity, code) => ({entity, code}),
    setEntityList: (entity, list, recordsCount) => ({entity, list, recordsCount}),
    setShowModal: (entity, value) => ({entity, value}),
    setDeletedObject: (entity, obj) => ({entity, obj}),
});
creators._dispatch = null;

function defaultSetValueOptions() {
    return {
        refreshDependentFields: true
    }
}

function _enhanceCreators(creators) {
    let resultCreators = {};
    Object.keys(creators).forEach(function (prop) {
        resultCreators[prop] = function () {
            const creator = creators[prop];
            let action = creator.apply(creator, arguments);
            action.type = prop;
            return action;
        }
    });
    return resultCreators;
}


function ajaxFn(entity, operation, requestFn, requestData, context) {
    const self = this;
    return function (dispatch) { //thunk function
        dispatch(creators.ajaxStarted(entity, operation, requestData, context));

        return requestFn.call(self, requestData).then(
            data => {
                dispatch(creators.ajaxFinishedSuccess(entity, operation, data, requestData, context));
                return Promise.resolve(data)
            },
            error => {
                if (Config.loggingEnabled) {
                    console.log("Error during ajax operation", entity, operation, error);
                }
                dispatch(creators.ajaxFinishedError(entity, operation, error, requestData, context));
                return Promise.reject(error)
            }
        );
    }
}

function ajaxResource(entity, resource = entity) {
    const operations = DataService.operations[resource];
    return {
        findAll(searchRequest){
            const request = Utils.extend(searchRequest, {pageRecordsCount: Config.pageRecordsCount});
            return ajaxFn(entity, 'findAll', operations.findAll, request)
        },
        findOne(id){
            return ajaxFn(entity, 'findOne', operations.findOne, id)
        },
        save(obj){
            return ajaxFn(entity, 'save', obj.id ? operations.modify : operations.create, obj)
        },
        delete(id){
            return ajaxFn(entity, 'delete', operations.delete, id)
        }
    }
}

creators.ajax = {
    dictionaries: {
        load(){
            return ajaxFn('dictionaries', 'load', DataService.operations.dictionaries.load)
        }
    },
    products: ajaxResource('products'),
    participants: ajaxResource('participants'),
    emissions: ajaxResource('emissions'),
    packCode: ajaxResource('packCode')
};


module.exports = creators;
