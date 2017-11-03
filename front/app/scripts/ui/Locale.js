'use strict';

const rcCalendar_ru_RU = require('rc-calendar/lib/locale/ru_RU'),
    rcCalendar_en_US = require('rc-calendar/lib/locale/en_US'),
    LocalStorageService = require('./services/LocalStorageService');

const Locale = {
    curLocale: 'ru',
    existingLocales: ['en', 'ru'],

    rcCalendarLocales: {en: rcCalendar_en_US, ru: rcCalendar_ru_RU},
    shortMonthsLabels: {
        en: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        ru: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек']
    },

    current(){
        let newLocale = arguments[0];
        if (newLocale) {
            if (Locale.existingLocales.indexOf(newLocale) >= 0) {
                Locale.curLocale = newLocale;
                LocalStorageService.setLocale(newLocale);
            } else {
                throw Error('Unable to set locale ' + newLocale + '. Valid locales are ' + this.existingLocales.join(', '))
            }
        } else {
            return Locale.curLocale;
        }
    },

    shortMonths(){
        return Locale.shortMonthsLabels[Locale.curLocale]
    },

    rcCalendarLocale(){
        return Locale.rcCalendarLocales[Locale.curLocale]
    },

    pluralize(count, word, pluralForm){
        const pluralFn = Locale.curLocale === 'en' ? pluralizeEn : pluralizeCn;
        return pluralFn(count, word, pluralForm)
    },

    printCount(count, word, pluralForm) {
        return count + ' ' + Locale.pluralize(count, word, pluralForm)
    }
};

function pluralizeEn(count, word, pluralForm) {
    return count === 1 ? word : (pluralForm || word + 's')
}

function pluralizeCn(count, word, pluralForm) {
    return count === 1 ? word : (pluralForm || word)
}

// @if ENV='dev'
Locale.curLocale = 'ru';
// @endif

const savedLocale = LocalStorageService.getLocale();
if (savedLocale) {
    Locale.curLocale = savedLocale;
}

module.exports = Locale;
