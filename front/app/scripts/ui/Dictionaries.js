'use strict';

const Utils = require('./Utils');

const allItem = {
    id: 'all',
    title: Utils.message('common.label.all'),
    shortTitle: Utils.message('common.label.all')
};

const Dictionaries = {
    responseStatus: create(['0', '400', '404', '401', '403', '409', '502']
        .map(Utils.wrapIdentity)
        .map(o => Utils.addLocalizedProp(o, 'title', 'common.error.responseStatus.'))),

    orientation: create(['horizontal', 'vertical']
        .map(Utils.wrapIdentity)
        .map(o => Utils.addLocalizedProp(o, 'title', 'crossword.placement.orientation.'))),

};

function create(opts) {
    const optsObj = {};
    opts.forEach(o => {
        optsObj[o.id] = o
    });
    opts.byId = (id) => optsObj[id];
    opts.byIds = (ids) => {
        if (Utils.isArray(ids)) {
            return ids.map(id => optsObj[id])
        }
        return []
    };
    opts.obj = () => optsObj;
    opts.withAll = opts.concat(allItem);
    return opts;
}

module.exports = Dictionaries;
