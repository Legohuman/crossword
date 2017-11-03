'use strict';

const {browserHistory} = require('react-router'),
    Utils = require('./Utils');

const Navigator = {
    routes: {
        index: '/',
        login: '/login',
        products: '/products',
        product: id => ('/product/' + id),

        reports: '/reports/',

        administration: '/',
        participants: '/participants',
        participant: id => ('/participant/' + id),
        createParticipant: 'participant',
        users: '/users',
        user: id => ('/user/' + id),
        createProduct: '/product',

        packCodes: '/cis/detail',
        packCode: code => ('/cis/detail/' + code),
        orders: '/reports/orders',
        order: id => ('/reports/order/' + id),

        emissions: '/emissions',
        emissionRegistrar: id => ('/emission/' + id),
        createEmissionRegistrar: '/emission',

        communications: '/tracefailures',
        communication: id => ('/tracefailure/' + id),

    },

    navigate(route, query){
        browserHistory.push(route + Utils.objToQueryString(query))
    },

    query(location, parameter){
        return location && location.query && location.query[parameter]
    },

    back(){
        browserHistory.goBack()
    }
};

module.exports = Navigator;
