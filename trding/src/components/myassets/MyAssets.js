import React from 'react';
import ReactDOM from 'react-dom';
import './MyAssets.css';

class MyAssets extends React.Component {

  render(){
  return(
    <div>
    <div className="Table-container">
      <table className="Table">
        <thead className="Table-header">
          <tr>
            <th>Cryptocurrency</th>
            <th>Current Price</th>
            <th>Purchase Price</th>
            <th>Change</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
);
}
}

export default MyAssets;
