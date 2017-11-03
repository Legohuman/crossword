'use strict';

const React = require('react'),
    Utils = require('../Utils');

const ValidationMessage = React.createClass({

    render() {
        const p = this.props;
        if (p.message) {
            return <div className={p.messageClass + ' ' + Utils.select(p.centered, 'text-centered', '')}>
                {p.message}
            </div>
        }
        return null
    }
});

ValidationMessage.defaultProps = {
    messageClass: 'alert-field-warning',
};

module.exports = ValidationMessage;
