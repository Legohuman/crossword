'use strict';

const Utils = require('./Utils'),
    moment = require('moment-timezone'),
    Config = require('./Config'),
    Navigator = require('./Navigator'),
    Dictionaries = require('./Dictionaries'),
    Icons = require('./components/Icons');

const Renderers = {
    valOrDefault(defaultVal, renderFn){
        return val => val ? (renderFn ? renderFn(val) : val) : defaultVal || '';
    },

    bool(val, trueMessage, falseMessage){
        return val ? trueMessage : falseMessage
    },

    numRange(start, end, defaultVal = '-', separator = ' - '){
        if (Utils.isDefined(start) && Utils.isDefined(end) && start != end) {
            return start + separator + end
        } else if (Utils.isDefined(start)) {
            return start
        } else if (Utils.isDefined(end)) {
            return end
        } else {
            return defaultVal
        }
    },

    dictOption(opt, defaultVal){
        return _objPropOrDefault(opt, 'title', defaultVal);
    },

    objName: _objNameOrDefault,

    validation: {
        iconForArrIndex(arr, i){
            return Utils.select(arr && !Utils.isAllValuesEmpty(arr[i]), Icons.glyph.warningSign('icon-needs-validation'))
        }
    },

    arr(arr, delimiter = ' ', renderFn = Utils.obj.self, filterFn = Utils.fn.truth){
        if (!Array.isArray(arr)) {
            return '';
        }
        return arr.filter(filterFn.bind(this)).map(renderFn.bind(this)).join(delimiter);
    },

    product: {
        gtin(product){
            return product && product.gtin || '';
        },
        name(product){
            return product && product.name || '';
        },
        nameGtin(product){
            return product && Renderers.arr([product.name, product.gtin])
        }
    },
    participant: {
        name(participant){
            return participant && (participant.name || participant.fio) || '';
        }
    },
    er: {
        producerName(er){
            return er && Renderers.participant.name(er.producer) || '';
        }
    }
};

function redFont(component) {
    return <span className="label-red">{component}</span>
}

function _objNameOrDefault(obj, defaultVal) {
    return _objPropOrDefault(obj, 'name', defaultVal);
}

function _objPropOrDefault(obj, prop, defaultVal = '') {
    return obj && obj[prop] || defaultVal;
}

module.exports = Renderers;
