import React from 'react';
import { withRouter } from 'react-router-dom';
import './HistoryTable.css';
import PropTypes from 'prop-types';
import { renderChangePercent, renderProfit } from '../../helpers';

const HistoryTable = (props) => {
  const {transactions,history} = props;

  return(
    <div className="Table-container">
      <table className="Table">
        <thead className="Table-header">
          <tr>
            <th>Cryptocurrency</th>
            <th>Units</th>
            <th>Open Date</th>
            <th>Open Value</th>
            <th>Invested</th>
            <th>Close Value</th>
            <th>Close Date</th>
            <th>Profit/Loss</th>
          </tr>
        </thead>
        <tbody className="Table-body">
          {transactions.map((transaction) => (
            <tr
              key={transaction.id}
              onClick={() => history.push({javascript:void(0)})}
              >
              <td>
                <span className="Table-rank">{}</span>
                {transaction.currency_id}
              </td>
              <td>
                <span className="Table-rank">{}</span>
                {transaction.invested/transaction.open_value}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.open_date}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.open_value}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.invested}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                {transaction.close_value}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                {transaction.close_date}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                {transaction.profit_loss}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

HistoryTable.propTypes = {
  transactions: PropTypes.array.isRequired,
  history: PropTypes.object.isRequired,
};

export default withRouter(HistoryTable);
