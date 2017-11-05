'use strict';
let store = null;

const Store = {
    init(s) {
        store = s
    },
    dispatch(action) {
        store.dispatch(action);
    },
    state(){
        return store.getState();
    }
};

module.exports = Store;