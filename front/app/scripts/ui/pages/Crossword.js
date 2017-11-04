'use strict';

const React = require('react'),
    {connect} = require('react-redux'),

    Navigator = require('../Navigator'),
    Renderers = require('../Renderers'),
    Actions = require('../actions/Actions'),
    DataService = require('../services/DataService'),
    Utils = require('../Utils'),
    Dictionaries = require('../Dictionaries'),
    {Button, Select} = require('../components/CompactGrid'),
    Icons = require('../components/Icons'),
    ValidationMessage = require('../components/ValidationMessage'),
    CrosswordGrid = require('../components/CrosswordGrid'),
    TextInput = require('../components/TextInput');

const {PropTypes} = React;

const Crossword = React.createClass({

    render(){
        const self = this, p = self.props;
        return <div className="container content">
            <h3 className="text-centered">{Utils.message('crossword.app.title')}</h3>
            <ValidationMessage message={Utils.message(p.errorCode)}/>
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
        const self = this, p = self.props;

        return <table className="inner-table">
            <thead className="inner-table__head">{Utils.message('crossword.table.containers')}</thead>
            <tr>
                <td className="inner-table__header-cell">{Utils.message('crossword.placement.orientation')}</td>
                <td className="inner-table__header-cell">{Utils.message('crossword.placement.column')}</td>
                <td className="inner-table__header-cell">{Utils.message('crossword.placement.row')}</td>
                <td className="inner-table__header-cell">{Utils.message('crossword.placement.length')}</td>
                <td className="inner-table__header-cell"/>
            </tr>
            {p.placements.map((placement, i) =>
                <tr key={'pl' + i}>
                    <td className="inner-table__body-cell">{placement.v ?
                        Utils.message('crossword.placement.orientation.vertical') :
                        Utils.message('crossword.placement.orientation.horizontal')}</td>
                    <td className="inner-table__body-cell">{placement.x}</td>
                    <td className="inner-table__body-cell">{placement.y}</td>
                    <td className="inner-table__body-cell">{placement.l}</td>
                    <td>
                        <Button
                            onClick={() => p.dispatch(Actions.deleteEntity('placements', null, i))}>
                            {Icons.glyph.trash()}
                        </Button>
                    </td>
                </tr>
            )}
            <tr>
                <td>
                    <Select name="orientation"
                            value={p.newPlacement.orientation || Dictionaries.orientation.byId('horizontal')}
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
                               placeholder={Utils.message('crossword.placement.column')}
                               defaultValue={p.newPlacement.column || 0}
                               onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'column', val || 0))}/>
                </td>
                <td>
                    <TextInput owner={p.newPlacement}
                               name="row"
                               placeholder={Utils.message('crossword.placement.row')}
                               defaultValue={p.newPlacement.row || 0}
                               onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'row', val || 0))}/>
                </td>
                <td>
                    <TextInput owner={p.newPlacement}
                               name="length"
                               placeholder={Utils.message('crossword.placement.length')}
                               defaultValue={p.newPlacement.length || 0}
                               onChange={val => p.dispatch(Actions.setEntityValue('newPlacement', 'length', val || 0))}/>
                </td>
                <td>
                    <Button onClick={() => p.dispatch(Actions.addEntity('placements', p.newPlacement)) }>
                        {Icons.glyph.plus()}
                    </Button>
                </td>
            </tr>
            <tfoot className="inner-table__foot">
            <Button classes="btn-default btn-outline-default"
                    onClick={self.createCrosswordVariants}>
                {Utils.message('button.create.crossword')}&nbsp;{Icons.glyph.chevronRight()}
            </Button>
            </tfoot>
        </table>
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
        DataService.operations.crosswords.create({placements: p.placements}).then(solutions => {
            p.dispatch(Actions.setEntityValues('solutions', solutions));
            if (solutions && solutions.length) {
                p.dispatch(Actions.selectEntity('solutions', 0));
            }
        });
    }
});

function mapStateToProps(state) {
    return state.pages.Crossword
}

function mapDispatchToProps(dispatch) {
    return {dispatch}
}

module.exports = connect(mapStateToProps, mapDispatchToProps)(Crossword);
