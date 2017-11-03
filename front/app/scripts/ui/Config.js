'use strict';

const Config = {
    baseServiceUrl: '/', //flag to retrieve data from client datastores, for ui development only
    dateFormat: 'DD.MM.YYYY',
    dateTimeFormat: 'DD.MM.YYYY HH:mm:ss',
    pageRecordsCount: 50,
    uploadPhotoMaxSizeBytes: 25 * 1024 * 1024,
    inputValuePropagationDebounceTimeoutMs: 800,
    loggingEnabled: true,
    authSchemePrefix: 'Bearer ',
    notificationFadeTimeMs: 5000,
    containerMaxCoordinate: 20,
    containerMinLength: 3,
    containerMaxLength: 8
};
Config.uploadPhotoMaxSizeMB = Math.floor(Config.uploadPhotoMaxSizeBytes * 100 / (1024 * 1024)) / 100;

//@exclude
// Config.baseServiceUrl='/';
//@endexclude

module.exports = Config;
