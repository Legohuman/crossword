'use strict';

const {browserHistory} = require('react-router'),
    Utils = require('./Utils');

const Navigator = {
    routes: {
        index: '/',
    },

    navigate(route, query){
        browserHistory.push(route + Utils.objToQueryString(query))
    },

    query(location, parameter){
        return location && location.query && location.query[parameter]
    },

    back(){
        browserHistory.goBack()
    }
};

module.exports = Navigator;
