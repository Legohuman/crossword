require('./Polyfills');

const React = window.React = require('react'),
    ReactDOM = require("react-dom"),
    {Provider} = require('react-redux'),
    thunkMiddleware = require('redux-thunk').default,

    {createStore, applyMiddleware} = require('redux'),
    {Router, Route, browserHistory} = require('react-router'),

    Reducers = require('./ui/reducers/Reducers'),
    Utils = require('./ui/Utils'),
    Locale = require("./ui/Locale"),
    NoMatch = require("./ui/pages/NoMatch"),
    Crossword = require("./ui/pages/Crossword");

// @if ENV='dev'
const createLogger = require('redux-logger');
// @endif

const appContainer = document.getElementById("app");

const store = createStore(Reducers, applyMiddleware(
    thunkMiddleware
    // @if ENV='dev'
    ,
    createLogger()
    // @endif
));

ReactDOM.render((
    <div>
        <Provider store={store}>
            <Router history={browserHistory}>
                <Route path="/" component={Crossword}/>
                <Route path="*" component={NoMatch}/>
            </Router>
        </Provider>
    </div>), appContainer);
