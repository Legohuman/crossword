const {combineReducers} = require('redux'),
    CommonReducer = require('./CommonReducer'),
    Product = require('./ProductReducer');

const reducers = combineReducers({
    common: CommonReducer,
    pages: combineReducers({
        Product
    })
});

module.exports = reducers;
