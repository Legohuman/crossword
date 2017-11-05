'use strict';

const React = require('react'),
    Utils = require('../Utils');

const CrosswordGrid = React.createClass({
    render() {
        const p = this.props;

        return <div>{
            p.cells && p.cells.length && <table className="cw-grid">

                {p.cells[0] && p.cells[0].length && <tr>{
                    [<td key={'side_head'}
                         className="cw-cell--head"/>]

                        .concat(p.cells[0].map((cell, x) =>
                            <td key={x + '_head'}
                                className="cw-cell--head">
                                {x + 1}
                            </td>))
                }</tr>}
                {p.cells.map((rowCells, y) =>
                    rowCells && rowCells.length && <tr>{
                        [<td key={y + '_side'}
                             className="cw-cell--side">
                            {y + 1}
                        </td>]
                            .concat(rowCells.map((cell, x) =>
                                <td key={x + '_' + y}
                                    className={'cw-cell ' + Utils.select(cell, 'cw-cell-letter', '')}>
                                    {cell}
                                </td>))
                    }</tr>
                )}
            </table> ||
            <div className="cw-placeholder-message">
                {Utils.message('crossword.grid.no.cells')}
            </div>
        }</div>
    }
});

module.exports = CrosswordGrid;
