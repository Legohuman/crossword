'use strict';

const L = require('./Locale');

const Messages = {
    en: {
        'button.ok': 'OK',
        'button.cancel': 'Cancel',
        'button.back': 'Back',
        'button.next': 'Next',
        'button.yes': 'Yes',
        'button.no': 'No',
        'button.open': 'Open',
        'button.close': 'Close',
        'button.new': 'New',
        'button.create': 'Create',
        'button.edit': 'Edit',
        'button.save': 'Save',
        'button.save.continue': 'Save and continue',
        'button.saved': 'Saved',
        'button.add': 'Add',
        'button.delete': 'Delete',
        'button.remove': 'Remove',
        'button.find': 'Find',
        'button.select': 'Select',
        'button.refresh': 'Refresh',
        'button.clear.all': 'Clear all',
        'button.register': 'Register',
        'button.export': 'Export',
        'button.import': 'Import',
        'button.logOut': 'Log out',
        'button.create.crossword': 'Create crossword',

        'common.unit.year': 'y',
        'common.label.none': 'None',
        'common.label.all': 'All',

        'common.label.year': 'Year',
        'common.label.month': 'Month',

        'common.audit.created': 'Created',
        'common.audit.modified': 'Modified',
        'common.search.text': 'Search text',
        'common.select.no.results': 'No results found',
        'common.select.search.prompt': 'Type to search',
        'common.select.loading.placeholder': 'Loading...',

        'common.stat.sum': 'Sum',
        'common.dialog.confirm': 'Confirm',
        'common.dialog.confirm.common.message': 'Do you really want to do it?',
        'common.dialog.confirm.delete.message': 'Do you want to delete item?',
        'common.dialog.confirm.back.message': 'Do you want to return without saving item?',
        'common.form.item.not.found': 'Item was not found. Probably it was deleted.',
        'common.table.item.not.deleted': (entitiesName) => 'Item can not be deleted. It`s used by ' + entitiesName + '.',
        'common.auth.unauthorized': 'Unauthorized access',
        'common.auth.forbidden': 'Access is forbidden',
        'common.bad.request': reason => 'Bad request. ' + reason,
        'common.table.no.items': 'No items found',

        'common.error.responseStatus.0': 'Server connection error',
        'common.error.responseStatus.400': 'Internal error, invalid request is sent',
        'common.error.responseStatus.404': 'Object is not found, probably it was removed',
        'common.error.responseStatus.401': 'User is unauthorized',
        'common.error.responseStatus.403': 'User has no permissions for this content',
        'common.error.responseStatus.409': 'Operation encountered conflict',
        'common.error.responseStatus.502': 'Server connection error',

        'common.image.load.failed': 'Image load failed',
        'common.pages.no.access': 'You have no access to this page',
        'common.pages.not.found': 'This page does not exist',

        'common.validation.mail.not.valid': 'Email is not valid. Example: email@example.com',
        'common.validation.mobile.wrong.format': (digits) => 'Incorrect phone number format. ' + L.printCount(digits, 'digit') + ' number is expected.',
        'common.validation.field.not.empty': 'This field should not be empty',
        'common.validation.field.not.blank': 'This field should not be blank',
        'common.validation.field.only.digits': 'This field must contain only digits',
        'common.validation.field.max.length': (maxLength) => 'Value must not be longer than ' + L.printCount(maxLength, 'character'),
        'common.validation.field.min.length': (minLength) => 'Value must not be shorter than ' + L.printCount(minLength, 'character'),
        'common.validation.field.specific.length': (length) => 'Value length must be equal to ' + L.printCount(length, 'character'),
        'common.validation.field.ge.val': (limitVal) => 'Value must be greater than or equal to ' + limitVal,
        'common.validation.field.le.val': (limitVal) => 'Value must be less than or equal to ' + limitVal,
        'common.validation.not.negative': 'Value must be greater than or equal to 0',
        'common.validation.pass.confirm.not.match': 'Password confirmation is different from password.',
        'common.validation.login.already.exist': 'User with this login is already exist. Choose another login.',
        'common.emissions.validation.ipAddress.id.not.valid': 'IP address is invalid, it should have format n.n.n.n',
        'common.users.validation.user.id.not.valid': 'User id is invalid, it can contain alphanumeric characters and digits.',
        'common.users.validation.user.id.can.not.change': 'User id can not be changed.',
        'common.validation.no.object.found.for.value': 'Value was not found in available options',

        'crossword.app.title': 'Crossword creator',
        'crossword.table.containers': 'Containers',
        'crossword.table.solutions': 'Solutions',

        'crossword.grid.no.cells': 'No cells',
        'crossword.container.orientation': 'Orientation',
        'crossword.container.column': 'Column',
        'crossword.container.row': 'Row',
        'crossword.container.length': 'Length',

        'crossword.container.orientation.horizontal': 'Horizontal',
        'crossword.container.orientation.vertical': 'Vertical',

        'crossword.solution.number': 'No.',
        'crossword.solution.words': 'Words',

        'placement.error.parameter.not.number': 'Container parameter must be a number',
        'placement.error.negative.coordinate': 'Container coordinate must not be negative',
        'placement.error.same.orientation.touched': 'Containers of same orientation touches or overlaps each other',
        'placement.error.too.big.coordinate': maxCoordinate => 'Container coordinate must not be bigger than  ' + maxCoordinate,
        'placement.error.too.big.length': maxLength => 'Container length must not be bigger than ' + maxLength,
        'placement.error.too.small.length': minLength => 'Container length must not be less than ' + minLength,
    },
    ru: { //not translated yet
        'button.ok': 'OK',
        'button.cancel': 'Отмена',
        'button.back': 'Предыдущий',
        'button.next': 'Следующий',
        'button.yes': 'Да',
        'button.no': 'Отмена',
        'button.open': 'Открыть',
        'button.close': 'Закрыть',
        'button.new': 'Новый',
        'button.create': 'Создать',
        'button.edit': 'Редактировать',
        'button.save': 'Сохранить',
        'button.save.continue': 'Сохранить и продолжить',
        'button.saved': 'Сохранено',
        'button.add': 'Добавить',
        'button.delete': 'Удалить',
        'button.remove': 'Удалить',
        'button.find': 'Найти',
        'button.select': 'Выбрать',
        'button.refresh': 'Обновить',
        'button.clear.all': 'Очистить все',
        'button.register': 'Зарегистрировать',
        'button.export': 'Экспорт',
        'button.import': 'Импорт',
        'button.logOut': 'Выйти',
        'button.create.crossword': 'Создать кроссворд',

        'common.unit.year': 'г',
        'common.label.none': 'Пусто',
        'common.label.all': 'Все',

        'common.label.year': 'Год',
        'common.label.month': 'Месяц',

        'common.audit.created': 'Создано',
        'common.audit.modified': 'Изменено',
        'common.search.text': 'Текст поиска',
        'common.select.no.results': 'По вашему запросу ничего не найдено',
        'common.select.search.prompt': 'Поиск',
        'common.select.loading.placeholder': 'Загрузка...',

        'common.stat.sum': 'Сумма',
        'common.dialog.confirm': 'Подтвердить',
        'common.dialog.confirm.common.message': 'Подтверждаете ли вы это действие?',
        'common.dialog.confirm.delete.message': 'Вы уверены, что хотите удалить объект?',
        'common.dialog.confirm.back.message': 'Вернуться без сохранения?',
        'common.form.item.not.found': 'Ресурс не найден. Возможно, он был удален.',
        'common.table.item.not.deleted': (entitiesName) => 'Объект не может быть удален, т.к. он используется ' + entitiesName + '.',
        'common.auth.unauthorized': 'Неавторизованный доступ',
        'common.auth.forbidden': 'Доступ запрещен',
        'common.bad.request': reason => 'Неверный запрос. ' + reason,
        'common.table.no.items': 'Не найдено',

        'common.error.responseStatus.0': 'Ошибка соединения с сервером',
        'common.error.responseStatus.400': 'Внутренняя ошибка, отправлен неверный запрос',
        'common.error.responseStatus.404': 'Ресурс не найден. Возможно, он был удален.',
        'common.error.responseStatus.401': 'Неавторизованный доступ',
        'common.error.responseStatus.403': 'Доступ к ресурсу запрещен',
        'common.error.responseStatus.409': 'Запрос не может быть выполнен из-за конфликта',
        'common.error.responseStatus.502': 'Ошибка соединения с сервером',

        'common.image.load.failed': 'Ошибка загрузки изображения',
        'common.pages.no.access': 'У вас нет прав доступа к этой странице',
        'common.pages.not.found': 'Эта страница еще не создана',

        'common.validation.mail.not.valid': 'Недопустимый Email. Пример: email@example.com',
        'common.validation.mobile.wrong.format': (digits) => 'Неправильный формат номера. Ожидается ' + L.printCount(digits, 'digit') + '-значный формат.',
        'common.validation.field.not.empty': 'Это поле обязательно к заполнению',
        'common.validation.field.not.blank': 'Это поле обязательно к заполнению',
        'common.validation.field.only.digits': 'Это поле может содержать только цифры',
        'common.validation.field.max.length': (maxLength) => 'Значение должно быть не длиннее ' + L.printCount(maxLength, ' символов'),
        'common.validation.field.min.length': (minLength) => 'Значение должно быть не короче ' + L.printCount(minLength, ' символов'),
        'common.validation.field.specific.length': (length) => 'Длина поля должна быть равна ' + L.printCount(length, ' символов'),
        'common.validation.field.ge.val': (limitVal) => 'Значение должно быть больше или равно ' + limitVal,
        'common.validation.field.le.val': (limitVal) => 'Значение должно быть меньше или равно ' + limitVal,
        'common.validation.not.negative': 'Значение должно быть больше или равно 0',
        'common.validation.pass.confirm.not.match': 'Подтверждение пароля не совпадает с паролем.',
        'common.validation.login.already.exist': 'Пользователь с таким логином уже существует. Выберите другой логин.',
        'common.emissions.validation.ipAddress.id.not.valid': 'Неправильный формат IP адреса. Ожидается n.n.n.n',
        'common.users.validation.user.id.not.valid': 'Некорректный ID пользователя, он может содержать буквы, цифры и символы.',
        'common.users.validation.user.id.can.not.change': 'Имя пользователя не может быть изменено.',
        'common.validation.no.object.found.for.value': 'Значение не найдено в списке доступных опций.',

        'crossword.app.title': 'Составитель кроссвордов',
        'crossword.table.containers': 'Контейнеры',
        'crossword.table.solutions': 'Решения',

        'crossword.grid.no.cells': 'Нет данных',
        'crossword.container.orientation': 'Ориентация',
        'crossword.container.column': 'Колонка',
        'crossword.container.row': 'Ряд',
        'crossword.container.length': 'Длина',

        'crossword.container.orientation.horizontal': 'Горизонтально',
        'crossword.container.orientation.vertical': 'Вертикально',

        'crossword.solution.number': '№',
        'crossword.solution.words': 'Слова',

        'placement.error.parameter.not.number': 'Параметр контейнера должен быть числом',
        'placement.error.negative.coordinate': 'Координата контейнера должна быть неотрицательной',
        'placement.error.same.orientation.touched': 'Контейнеры одной ориентации накладываются или касаются друг друга',
        'placement.error.too.big.coordinate': maxCoordinate => 'Координата контейнера не должна быть более ' + maxCoordinate,
        'placement.error.too.big.length': maxLength => 'Длина контейнера не должна быть более ' + maxLength,
        'placement.error.too.small.length': minLength => 'Длина контейнера не должна быть менее ' + minLength,
    }
};

module.exports = Messages;
