const moment = require('moment-timezone'),
    Utils = require('../Utils');

function getInitialState() {
    return {
        operations: {}
    }
}

function CommonReducer(state = getInitialState(), action) {
    if (action.type === 'startOperation') {
        return Utils.merge(state, {operations: {[action.operation]: true}})
    } else if (action.type === 'endOperation') {
        return Utils.merge(state, {operations: {[action.operation]: null}})
    }
    return state;
}

module.exports = CommonReducer;
