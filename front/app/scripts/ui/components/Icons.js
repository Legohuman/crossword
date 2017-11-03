'use strict';

const React = require('react');


const GlyphIcon = ({name, iconClassName}) => {
    let className = 'glyphicon glyphicon-' + name;
    if (iconClassName) {
        className += ' ' + iconClassName;
    }
    return <span className={className} aria-hidden='true'></span>
};

const glyphs = {
    ok(className){
        return custom('ok', className)
    },
    save(className){
        return custom('save', className)
    },
    arrowLeft(className){
        return custom('arrow-left', className)
    },
    chevronLeft(className){
        return custom('chevron-left', className)
    },
    chevronRight(className){
        return custom('chevron-right', className)
    },
    check(className){
        return custom('check', className)
    },
    cog(className){
        return custom('cog', className)
    },
    signal(className){
        return custom('signal', className)
    },
    plus(className){
        return custom('plus', className)
    },
    minus(className){
        return custom('minus', className)
    },
    remove(className){
        return custom('remove', className)
    },
    move(className){
        return custom('move', className)
    },
    star(className){
        return custom('star', className)
    },
    pencil(className){
        return custom('pencil', className)
    },
    search(className){
        return custom('search', className)
    },
    user(className){
        return custom('user', className)
    },
    calendar(className){
        return custom('calendar', className)
    },
    camera(className){
        return custom('camera', className)
    },
    list(className){
        return custom('list', className)
    },
    newWindow(className){
        return custom('new-window', className)
    },
    menuUp(className){
        return custom('menu-up', className)
    },
    triangleTop(className){
        return custom('triangle-top', className)
    },
    triangleBottom(className){
        return custom('triangle-bottom', className)
    },
    logOut(className){
        return custom('log-out', className)
    },
    optionVertical(className){
        return custom('option-vertical', className)
    },
    banCircle(className){
        return custom('ban-circle', className)
    },
    refresh(className){
        return custom('refresh', className)
    },
    send(className){
        return custom('send', className)
    },
    warningSign(className){
        return custom('warning-sign', className)
    },
    dashboard(className){
        return custom('dashboard', className)
    },
    th(className){
        return custom('th', className)
    },
    export(className){
        return custom('export', className)
    },
    forward(className){
        return custom('forward', className)
    },
    backward(className){
        return custom('backward', className)
    },
    user(className){
        return custom('user', className)
    },
    listAlt(className){
        return custom('list-alt', className)
    },
    transfer(className){
        return custom('transfer', className)
    },
    eyeOpen(className){
        return custom('eye-open', className)
    },
    lock(className){
        return custom('lock', className)
    },
    floppy(className) {
        return custom('floppy-disk', className)
    },
    asterisk(className) {
        return custom('asterisk', className)
    },
    trash(className) {
        return custom('trash', className)
    },
};

function custom(name, iconClassName) {
    return <GlyphIcon name={name} iconClassName={iconClassName}/>
} 

module.exports = {
    glyph: glyphs,
    caret (){
        return <span className='caret' aria-hidden='true'></span>
    },
    spinner(){
        return <img src='/images/spinner.gif' aria-hidden='true'></img>
    },
    spinnerInverse(){
        return <img src='/images/spinner-inverse.gif' aria-hidden='true'></img>
    }
};
