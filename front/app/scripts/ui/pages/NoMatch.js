'use strict';

const React = require('react'),
    Utils = require('../Utils');

const NoMatch = React.createClass({
    render() {
        return (
            <div className="single-page-message">
                {Utils.message('common.pages.not.found')}
            </div>
        )
    }
});

module.exports = NoMatch;
