'use strict';

const Utils = require('../Utils');

const NotificationService = {
    notificationSystemRef: null,
    defaultNotificationOptions: {
        autoDismiss: 3,
        position: 'tc',
    },
    notificationLevels: ['success', 'error', 'warning', 'info'],

    init(notificationSystemRef) {
        this.notificationSystemRef = notificationSystemRef;
    }
};

NotificationService.notificationLevels.forEach(level => {
    NotificationService[level] = (notification, onRemoveCb) => {
        const notificationObj = Utils.isString(notification) ? {message: notification} : notification;
        if (Utils.isFunction(onRemoveCb)) {
            notificationObj.onRemove = onRemoveCb
        }
        NotificationService.notificationSystemRef.addNotification(Utils.extend(
            NotificationService.defaultNotificationOptions, notificationObj, {level}
        ))
    }
});

//noinspection JSUnresolvedVariable
module.exports = NotificationService;
