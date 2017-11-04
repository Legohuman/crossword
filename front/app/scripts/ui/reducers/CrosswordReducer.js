const moment = require('moment-timezone'),
    Config = require('../Config'),
    Dictionaries = require('../Dictionaries'),
    Utils = require('../Utils'),
    Actions = require('../actions/Actions'),
    ReducersMap = require('./ReducersMap');

const reducers = new ReducersMap(getInitialState())
    .add({type: 'setEntityValues', entity: 'solutions'},
        (state, action) => {
            const solutions = action.obj || [];
            return Utils.extend(state, {
                solutions: solutions
            });
        })
    .add({type: 'selectEntity', entity: 'solutions'},
        (state, action) => {
            const containers = state.solutions && state.solutions[action.index] || [];
            return Utils.extend(state, {
                selectedSolutionIndex: action.index,
                containers: containers,
                crosswordCells: Utils.containersToCells(containers)
            });
        })
    .add({type: 'setEntityValue', entity: 'newPlacement'},
        (state, action) => {
            return Utils.merge(state, {
                newPlacement: {[action.fieldId]: action.newValue},
            });
        })
    .add({type: 'addEntity', entity: 'containers'},
        (state, action) => {
            const placement = {
                    v: action.obj.orientation === 'vertical',
                    x: +action.obj.column || 0,
                    y: +action.obj.row || 0,
                    l: +action.obj.length || 0
                },
                errorCode = validatePlacement(placement, state.containers);

            if (!errorCode) {
                const containers = Utils.arr.push(state.containers, placement);
                return Utils.merge(state, {
                    containers: () => containers,
                    crosswordCells: () => Utils.containersToCells(containers),
                    solutions: () => [],
                    selectedSolutionIndex: -1,
                    errorCode: null
                });
            } else {
                return Utils.merge(state, {
                    errorCode: errorCode,
                });
            }
        })
    .add({type: 'deleteEntity', entity: 'containers'},
        (state, action) => {
            const containers = Utils.arr.removeAt(state.containers, action.index);
            return Utils.merge(state, {
                containers: () => containers,
                crosswordCells: () => Utils.containersToCells(containers),
                solutions: () => [],
                selectedSolutionIndex: -1
            });
        });

function validatePlacement(placement, exisitingPlacements) {
    if (!Utils.isNumber(placement.x) || !Utils.isNumber(placement.y) || !Utils.isNumber(placement.l)) {
        return Utils.message('placement.error.parameter.not.number');
    } else if (placement.x < 0 || placement.y < 0) {
        return Utils.message('placement.error.negative.coordinate');
    } else if (placement.x > Config.containerMaxCoordinate || placement.y > Config.maxCoordinate) {
        return Utils.message('placement.error.too.big.coordinate');
    } else if (placement.l < Config.containerMinLength) {
        return Utils.message('placement.error.too.small.length', Config.containerMinLength);
    } else if (placement.l > Config.containerMaxLength) {
        return Utils.message('placement.error.too.big.length', Config.containerMaxLength);
    } else if (exisitingPlacements.some(p => p.v === placement.v &&
        (p.v ? p.x === placement.x && Utils.overlaps(p.y, p.l, placement.y, placement.l) :
            p.y === placement.y && Utils.overlaps(p.x, p.l, placement.x, placement.l)))) {
        return Utils.message('placement.error.same.orientation.touched');
    }
    return null
}

function getInitialState() {
    const containers = [
        {v: false, x: 0, y: 0, l: 6},
        {v: false, x: 0, y: 2, l: 7},
        {v: false, x: 0, y: 5, l: 6},
        {v: true, x: 0, y: 0, l: 6},
        {v: true, x: 2, y: 0, l: 7},
        {v: true, x: 5, y: 0, l: 6}
    ];
    return {
        containers,
        crosswordCells: Utils.containersToCells(containers),
        newPlacement: {},
        solutions: [],
        errorCode: null,
        selectedSolutionIndex: -1
    };
}

function CrosswordReducer(state = getInitialState(), action) {
    return reducers.reduce(state, action);
}

module.exports = CrosswordReducer;
