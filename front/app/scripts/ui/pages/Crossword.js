'use strict';

const React = require('react'),
    {connect} = require('react-redux'),

    Navigator = require('../Navigator'),
    Renderers = require('../Renderers'),
    Actions = require('../actions/Actions'),
    DataService = require('../services/DataService'),
    NotificationService = require('../services/NotificationService'),
    Utils = require('../Utils'),
    Dictionaries = require('../Dictionaries'),
    {Button, Select} = require('../components/CompactGrid'),
    Icons = require('../components/Icons'),
    ValidationMessage = require('../components/ValidationMessage'),
    CrosswordGrid = require('../components/CrosswordGrid'),
    TextInput = require('../components/TextInput');

const {PropTypes} = React;

const Crossword = React.createClass({

    componentWillReceiveProps(nextProps){
        const self = this, p = self.props;

        let nextErrorCode = nextProps.errorCode;
        if (nextErrorCode && p.errorCode != nextErrorCode) {
            NotificationService.warning(nextErrorCode, () => p.dispatch(Actions.setError('containers', null)));
        }
    },

    componentDidMount(){
        const self = this, p = self.props;

        DataService.socket.subscribeQueue('/crosswords/solutions/', message => {
            const payload = JSON.parse(message.body);
            if (payload.status) { //status message
                p.dispatch(Actions.setEntityValues('progress', payload));
            } else { //solution
                p.dispatch(Actions.addEntity('solutions', payload));
                if (!p.solutions || !p.solutions.length) {
                    p.dispatch(Actions.selectEntity('solutions', 0));
                }
            }
        })
    },

    render(){
        const self = this, p = self.props;
        return <div className="container content">
            <h3 className="text-centered">{Utils.message('crossword.app.title')}</h3>
            <div className="row">
                <div className="form-group">
                    <div className="col-md-6">
                        {self.renderContainersList()}
                    </div>
                    <div className="col-md-6">
                        {self.renderSolutionsList()}
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="form-group">
                    <div className="col-md-12 cw-wrapper">
                        <CrosswordGrid cells={p.crosswordCells}/>
                    </div>
                </div>
            </div>
        </div>
    },

    renderContainersList(){
        const self = this, p = self.props,
            operationStatusOpt = Dictionaries.operationStatus.byId(p.progress.status);

        return <div>
            <table className="inner-table">
                <thead className="inner-table__head">{Utils.message('crossword.table.containers')}</thead>
                <tr>
                    <td className="inner-table__header-cell">{Utils.message('crossword.container.orientation')}</td>
                    <td className="inner-table__header-cell">{Utils.message('crossword.container.column')}</td>
                    <td className="inner-table__header-cell">{Utils.message('crossword.container.row')}</td>
                    <td className="inner-table__header-cell">{Utils.message('crossword.container.length')}</td>
                    <td className="inner-table__header-cell"/>
                </tr>
                {p.containers.map((placement, i) =>
                    <tr key={'pl' + i}>
                        <td className="inner-table__body-cell">{placement.v ?
                            Utils.message('crossword.container.orientation.vertical') :
                            Utils.message('crossword.container.orientation.horizontal')}</td>
                        <td className="inner-table__body-cell">{placement.x + 1}</td>
                        <td className="inner-table__body-cell">{placement.y + 1}</td>
                        <td className="inner-table__body-cell">{placement.l}</td>
                        <td>
                            <Button
                                onClick={() => p.dispatch(Actions.deleteEntity('containers', null, i))}>
                                {Icons.glyph.trash()}
                            </Button>
                        </td>
                    </tr>
                )}
                <tr>
                    <td>
                        <Select name="orientation"
                                value={Dictionaries.orientation.byId(p.newPlacement.orientation || 'horizontal')}
                                valueRenderer={Renderers.dictOption}
                                optionRenderer={Renderers.dictOption}
                                clearable={false}
                                options={Dictionaries.orientation}
                                onChange={opt => p.dispatch(Actions.setEntityValue('newPlacement', 'orientation', opt && opt.id || Dictionaries.orientation.byId('horizontal')))}
                        />

                    </td>
                    <td>
                        <TextInput owner={p.newPlacement}
                                   name="column"
                                   placeholder={Utils.message('crossword.container.column')}
                                   defaultValue={p.newPlacement.column}
                                   onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'column', val))}/>
                    </td>
                    <td>
                        <TextInput owner={p.newPlacement}
                                   name="row"
                                   placeholder={Utils.message('crossword.container.row')}
                                   defaultValue={p.newPlacement.row}
                                   onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'row', val))}/>
                    </td>
                    <td>
                        <TextInput owner={p.newPlacement}
                                   name="length"
                                   placeholder={Utils.message('crossword.container.length')}
                                   defaultValue={p.newPlacement.length}
                                   onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'length', val))}/>
                    </td>
                    <td>
                        <Button onClick={() => p.dispatch(Actions.addEntity('containers', p.newPlacement)) }>
                            {Icons.glyph.plus()}
                        </Button>
                    </td>
                </tr>
            </table>
            <div className="inner-table__foot">
                <Button classes="btn-default btn-outline-default"
                        onClick={self.createCrosswordVariants}>
                    {Utils.message('button.create.crossword')}
                    &nbsp;
                    {Utils.selectFn(p.progress.status === 'inProgress',
                        () => Icons.glyph.refresh('glyphicon-animate-spin'),
                        () => Icons.glyph.chevronRight()
                    )}
                </Button>
            </div>
            <div>{Utils.message('crossword.creation.status', operationStatusOpt && operationStatusOpt.title, p.progress.iterations || 0)}</div>
        </div>
    },

    renderSolutionsList(){
        const self = this, p = self.props;

        return <table className="inner-table">
            <thead className="inner-table__head">{Utils.message('crossword.table.solutions')}</thead>
            <tr>
                <td className="inner-table__header-cell"
                    style={{width: '50px'}}>
                    {Utils.message('crossword.solution.number')}
                </td>
                <td className="inner-table__header-cell">{Utils.message('crossword.solution.words')}</td>
            </tr>
            {p.solutions.map((solution, i) =>
                <tr key={'pl' + i}
                    className={Utils.select(p.selectedSolutionIndex === i, 'inner-table__body-cell--selected')}
                    onClick={() => {
                        p.dispatch(Actions.selectEntity('solutions', i));
                    }}>
                    <td className="inner-table__body-cell">{i + 1}</td>
                    <td className="inner-table__body-cell">{Renderers.arr(solution, ', ', s => s && s.t)}</td>
                </tr>
            )}
        </table>
    },

    createCrosswordVariants(){
        const self = this, p = self.props;

        if (p.containers && p.containers.length) {
            if (p.progress.status !== 'inProgress') {
                p.dispatch(Actions.setEntityValues('solutions', []));
                p.dispatch(Actions.setEntityValues('progress', {status: 'inProgress', iterations: 0}));
                DataService.socket.client.send("/ws/crosswords/create", {}, JSON.stringify(p.containers));
            }
        } else {
            p.dispatch(Actions.setError('containers', Utils.message('crossword.error.no.containers')))
        }
    }
});

function mapStateToProps(state) {
    return Utils.extend(state.pages.Crossword)
}

function mapDispatchToProps(dispatch) {
    return {dispatch}
}

module.exports = connect(mapStateToProps, mapDispatchToProps)(Crossword);
