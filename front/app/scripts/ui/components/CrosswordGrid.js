'use strict';

const React = require('react'),
    Utils = require('../Utils');

const CrosswordGrid = React.createClass({
    render() {
        const p = this.props;
        console.log(p.cells);

        return <div>{
            p.cells && p.cells.length && <table className="cw-grid">
                {p.cells.map((rowCells, y) =>
                    rowCells && <tr>{
                        rowCells.map((cell, x) =>
                            <td key={x + '_' + y}
                                className={'cw-cell ' + Utils.select(cell, 'cw-cell-letter', '')}>
                                {cell}
                            </td>)
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
