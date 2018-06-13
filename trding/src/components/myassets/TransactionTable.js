import React from 'react';
import { withRouter } from 'react-router-dom';
import './TransactionTable.css';
import PropTypes from 'prop-types';
import { renderChangePercent, renderProfit, renderTypeBox } from '../../helpers';


const TransactionTable = (props) => {
  const {portfolio, currencies, handleCloseClick, history} = props;
  console.log(portfolio);

  return(
    <div className="Table-container">
      <table className="Table">
        <thead className="Table-header">
          <tr>
            <th>Cryptocurrency</th>
            <th>Type</th>
            <th>Opening price</th>
            <th>Current price</th>
            <th>Invested</th>
            <th>Profit</th>
            <th>Close</th>
          </tr>
        </thead>
        <tbody className="Table-body">
          {portfolio.map((action, i) => (
            <tr>
              <td>
                <span className="Table-rank">{}</span>
                {action.currency_id}
              </td>
              <td>
                {renderTypeBox(action.method)}
              </td> 
              <td>
                <span className="Table-dollar">$</span>
                 {action.open_value}
              </td>
              <td>
                <span className="Table-dollar">$</span>
                 {currencies[i]}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                {action.invested}
              </td>
              <td>
                <span className="Table-dollar">{}</span>
                 {renderProfit(currencies[i], action.open_value, action.method)}
              </td>
              <td>
                <span className="Close-button" onClick={() => handleCloseClick()}>✖</span>
              </td>

            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

TransactionTable.propTypes = {
  portfolio: PropTypes.array.isRequired,
  currencies: PropTypes.array.isRequired,
  history: PropTypes.object.isRequired,
};

export default withRouter(TransactionTable);
