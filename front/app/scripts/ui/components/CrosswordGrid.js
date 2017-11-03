'use strict';

const React = require('react'),
    Utils = require('../Utils');

const CrosswordGrid = React.createClass({
    render() {
        const p = this.props;
        console.log(p.cells);

        return p.cells && <table className="cw-grid">
                {p.cells.map((rowCells, y) =>
                    rowCells && <tr>{
                        rowCells.map((cell, x) =>
                            <td key={x + '_' + y}
                                className={'cw-cell ' + Utils.select(cell, 'cw-cell-letter')}>
                                {cell}
                            </td>)
                    }</tr>
                )}
            </table>
    }
});

module.exports = CrosswordGrid;
