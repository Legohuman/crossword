'use strict';

const DataService = require('../services/DataService'),
    moment = require('moment-timezone'),
    Config = require('../Config'),
    Utils = require('../Utils');

const creators = _enhanceCreators({

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
    selectEntity: (entity, index) => ({
        entity,
        index,
    }),
    setError: (entity, error) => ({
        entity,
        error,
    }),
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


module.exports = creators;
