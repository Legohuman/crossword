const {combineReducers} = require('redux'),
    CommonReducer = require('./CommonReducer'),
    Crossword = require('./CrosswordReducer');

const reducers = combineReducers({
    common: CommonReducer,
    pages: combineReducers({
        Crossword
    })
});

module.exports = reducers;
