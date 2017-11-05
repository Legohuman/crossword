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
                    x: +action.obj.column - 1 || 0,
                    y: +action.obj.row - 1 || 0,
                    l: +action.obj.length || 0
                },
                errorCode = validateContainer(placement, state.containers);

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
        })
    .add({type: 'setError', entity: 'containers'},
        (state, action) => {
            return Utils.merge(state, {
                errorCode: action.error
            });
        });

function validateContainer(container, existingContainers) {
    if (!Utils.isNumber(container.x) || !Utils.isNumber(container.y) || !Utils.isNumber(container.l)) {
        return Utils.message('container.error.parameter.not.number');
    } else if (container.x < 0 || container.y < 0) {
        return Utils.message('container.error.not.positive.coordinate');
    } else if (container.x >= Config.containerMaxCoordinate || container.y >= Config.containerMaxCoordinate) {
        return Utils.message('container.error.too.big.coordinate');
    } else if (container.l < Config.containerMinLength) {
        return Utils.message('container.error.too.small.length', Config.containerMinLength);
    } else if (container.l > Config.containerMaxLength) {
        return Utils.message('container.error.too.big.length', Config.containerMaxLength);
    } else {
        let oc = existingContainers.find(c => Utils.containerOverlapsOrTouchesSameOrientation(container, c));
        if (oc) {
            return Utils.message('container.error.same.orientation.touch', Utils.message('crossword.container.description', oc.v, oc.x + 1, oc.y + 1));
        }

        oc = existingContainers.find(c => Utils.containerTouchesOtherOrientation(container, c));
        if (oc) {
            return Utils.message('container.error.other.orientation.touch', Utils.message('crossword.container.description', oc.v, oc.x + 1, oc.y + 1));
        }

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
