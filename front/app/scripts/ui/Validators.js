'use strict';

const Utils = require('./Utils'),
    Config = require('./Config'),
    Dictionaries = require('./Dictionaries');

const nameMaxLength = 255;
const idMaxLength = 255;
const mobileLength = 11;
const literalNumericRegex = /^[a-z0-9]+$/i;
const stringIdRegex = /^[a-z0-9_]+$/i;
const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;


const Validators = {

    /**
     * @param entityId id of the specified validator
     * @param entity object to validate
     * @returns error messages for specified entity or null if no validators found
     */
    getByEntityId(entityId, entity)
    {
        const validator = Validators[entityId];
        return validator && validator(entity);
    },

    products (obj, fieldId){
        const messages = {};
        const validateFn = receiveValidateFunction(obj, fieldId, messages);

        validateFn("name", val => validateByChain(val, [validateNotBlank, validateMaxLengthFactory(nameMaxLength)]));
        return messages;
    },

    participants (obj, fieldId){
        const messages = {};
        const validateFn = receiveValidateFunction(obj, fieldId, messages);

        validateFn("type", val => validateByChain(val, [validateNotBlank]));
        return messages;
    },

    emissions(obj, fieldId) {
        const messages = {};
        const validateFn = receiveValidateFunction(obj, fieldId, messages);

        validateFn("producer", val => validateByChain(val, [validateNotBlank]));
        validateFn("ipAddress", val => validateByChain(val, [
            validateNotBlank,
            validateRegexpFactory(ipv4Regex, Utils.message('common.emissions.validation.ipAddress.id.not.valid'))
        ]));
        return messages;
    },

    users(obj, fieldId) {
        const messages = {};
        const validateFn = receiveValidateFunction(obj, fieldId, messages);

        validateFn("id", val => validateByChain(val, [
            validateNotBlank,
            validateRegexpFactory(stringIdRegex, Utils.message('common.users.validation.user.id.not.valid')),
            validateMaxLengthFactory(idMaxLength)
        ]));
        validateFn("name", val => validateByChain(val, [validateNotBlank, validateMaxLengthFactory(nameMaxLength)]));

        validateFn("pass", val => Utils.select(Utils.isNotDefinedOrBlank(val) && !obj.id, Utils.message('common.validation.field.not.empty')));
        validateFn("passConfirm", val => Utils.select((!obj.id || val || obj.pass) && val !== obj.pass, Utils.message('common.validation.pass.confirm.not.match')));
        return messages;
    },

};

/**
 * Checks if val is defined in array as id of one of the element.
 * @param array objects with ids array
 * @param val value to check
 */
function validateIdInArray(array, val) {
    return Utils.select(!array.byId(val), Utils.message('common.validation.no.object.found.for.value'));
}

/**
 * Checks if val is number and greater than 0.
 * @param val value to check
 */
function validateNotNegativeNumber(val) {
    return validateByChain(val, [
        objValue => Utils.select(Utils.isDefined(objValue) && isNaN(objValue), Utils.message('common.validation.field.only.digits')),
        objValue => Utils.select(Utils.isDefined(objValue) && objValue < 0, Utils.message('common.validation.not.negative'))
    ]);
}

/**
 * Checks if val is defined.
 * @param val value to check
 */
function validateDefined(val) {
    return Utils.select(!Utils.isDefined(val), Utils.message('common.validation.field.not.empty'));
}

/**
 * Checks if val is not blank string.
 * @param val value to check
 */
function validateNotBlank(val) {
    console.log('notBlank:' + val);
    return Utils.select(!Utils.isNotBlankString(val), Utils.message('common.validation.field.not.empty'));
}

function validateEmail(val) {
    return Utils.select(val && !Utils.validateEmail(val), Utils.message('common.validation.mail.not.valid'));
}

function validateDigits(val) {
    return Utils.select(val && !Utils.validateDigits(val), Utils.message('common.validation.field.only.digits'));
}

function validateRegexpFactory(regexp, message) {
    return val => Utils.select(val && !Utils.validateRegexp(val, regexp), message);
}

/**
 * Checks if val is not empty array.
 * @param val value to check
 * @param message custom validation message
 */
function validateNotEmptyArr(val, message = Utils.message('common.validation.field.not.empty')) {
    return Utils.select(!Utils.isNotEmptyArray(val), message);
}

/**
 * Creates functions that checks if val is greater than or equals to limit value
 * @param limitVal value to check against
 * @param message custom validation message
 */
function validateGeFactory(limitVal, message = Utils.message('common.validation.field.ge.val', limitVal)) {
    return val => Utils.select(!Utils.isDefined(val) || +val >= limitVal, null, message)
}

/**
 * Creates functions that checks if val is less than or equals to limit value
 * @param limitVal value to check against
 * @param message custom validation message
 */
function validateLeFactory(limitVal, message = Utils.message('common.validation.field.le.val', limitVal)) {
    return val => Utils.select(!Utils.isDefined(val) || +val <= limitVal, null, message)
}

/**
 * Creates functions that checks if string val has length not greater than maxLength
 * @param maxLength max length limit
 * @param message custom validation message
 */
function validateMaxLengthFactory(maxLength, message = Utils.message('common.validation.field.max.length', maxLength)) {
    return val => Utils.select(val && Utils.lengthMoreThan(val, maxLength), message)
}

/**
 * Creates functions that checks if string val has length not less than minLength
 * @param minLength min length limit
 * @param message custom validation message
 */
function validateMinLengthFactory(minLength, message = Utils.message('common.validation.field.min.length', minLength)) {
    return val => Utils.select(val && Utils.lengthLessThan(val, minLength), message)
}

/**
 * Creates functions that checks if string val has length not less than minLength
 * @param length min length limit
 * @param message custom validation message
 */
function validateSpecificLengthFactory(length, message = Utils.message('common.validation.field.specific.length', length)) {
    return val => Utils.select(val && Utils.lengthNotEqual(val, length), message)
}

/**
 * Creates functions that checks specified object by specified function
 * Suitable to include object based validations to chain
 * @param obj object that will be validated
 * @param fn function that will validate object
 */
function validateObjByFnFactory(obj, fn) {
    return val => fn(obj, val)
}

/**
 * Creates function that validates object specific fieldId.
 * If field focus is lost function is invoked with currentFieldId.
 * During save function is invoked with undefined currentFieldId, so every field is validated.
 * @param obj target object to check
 * @param currentFieldId field id to check
 * @param messages messages holder object
 * @return Function function which accepts (fieldId, validateFn, value) parameters.
 *
 * fieldId is field id to bind validation function to
 * validateFn is function that validates field. It accepts (val, obj) parameters
 * Val is obj value to be validated.
 * Obj is validated object.
 *
 * value is optional real value to pass to validation function
 */
function receiveValidateFunction(obj, currentFieldId, messages) {
    return function (fieldId, validateFn, value) {
        if (Utils.notDefinedOrEqual(currentFieldId, fieldId)) {
            messages[fieldId] = validateFn(Utils.isDefined(value) ? value : obj[fieldId], obj)
        }
    };
}

function validateByChain(obj, fns) {
    if (Utils.isNotEmptyArray(fns)) {
        for (let i = 0; i < fns.length; i++) {
            const fn = fns[i],
                message = fn(obj);
            if (message) {
                return message;
            }
        }
    }
    return null;
}

module.exports = Validators;
