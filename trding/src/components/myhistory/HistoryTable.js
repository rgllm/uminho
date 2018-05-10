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
            <th>Current price</th>
            <th>Purchase Price</th>
            <th>Transaction Type</th>
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
                {transaction.name}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.current_price}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.purchase_price}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.type}
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
