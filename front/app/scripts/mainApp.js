require('./Polyfills');

const React = window.React = require('react'),
    ReactDOM = require("react-dom"),
    NotificationSystem = require('react-notification-system'),
    {Provider} = require('react-redux'),
    thunkMiddleware = require('redux-thunk').default,

    {createStore, applyMiddleware} = require('redux'),
    {Router, Route, browserHistory} = require('react-router'),

    NotificationService = require('./ui/services/NotificationService'),
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

const App = React.createClass({
    componentDidMount: function () {
        NotificationService.init(this.refs.notificationSystem);
    },

    render: function () {
        return (
            <div>
                <Provider store={store}>
                    <Router history={browserHistory}>
                        <Route path="/" component={Crossword}/>
                        <Route path="*" component={NoMatch}/>
                    </Router>
                </Provider>
                <NotificationSystem ref="notificationSystem" style={false}/>
            </div>
        );
    }
});

ReactDOM.render(<App/>, appContainer);
