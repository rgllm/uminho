import React from 'react';
import { withRouter } from 'react-router-dom';
import './TransactionTable.css';
import PropTypes from 'prop-types';
import { renderChangePercent, renderProfit } from '../../helpers';

const TransactionTable = (props) => {
  const {transactions, currencies, history} = props;
  console.log(transactions);

  return(
    <div className="Table-container">
      <table className="Table">
        <thead className="Table-header">
          <tr>
            <th>Cryptocurrency</th>
            <th>Type</th>
            <th>Opening price</th>
            <th>Current price</th>
            <th>Profit</th>
          </tr>
        </thead>
        <tbody className="Table-body">
          {transactions.map((transaction, i) => (
            <tr
              key={transaction.id}
              onClick={() => history.push(`/currency/${transaction.code}`)}
              >
              <td>
                <span className="Table-rank">{}</span>
                {transaction.name}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.type}
              </td> 
              <td>
                <span className="Table-dollar">{}</span>
                 {transaction.purchase_price}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {currencies[i]}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {renderProfit(currencies[i], transaction.purchase_price, transaction.type)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

TransactionTable.propTypes = {
  transactions: PropTypes.array.isRequired,
  currencies: PropTypes.array.isRequired,
  history: PropTypes.object.isRequired,
};

export default withRouter(TransactionTable);
