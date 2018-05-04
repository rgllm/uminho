import React from 'react';
import { API_URL } from '../../config';
import { handleResponse, renderChangePercent } from '../../helpers';
import Loading from '../common/Loading';
import './Account.css';
import Checkout from '../checkout/Checkout';

class Account extends React.Component{
  constructor(){
    super();

    this.state = {
      currency: {},
      loading: false,
      error: null,
    };
  }

  render(){
    return(
        <div className="Account">
          <h1 className="Account-heading">
            Your Account Information
          </h1>
          <div className="Account-container">
            <div className="Account-item">
              Name: <span className="Account-value">Rogério Moreira</span>
            </div>
            <div className="Account-item">
              Email: <span className="Account-value">r@rgllm.com</span>
            </div>
            <div className="Account-item">
              Balance: <span className="Account-value">1000 €</span>
            </div>
            <div className="Account-item">
              <Checkout />
            </div>
          </div>
        </div>
      );
  }
}

export default Account;
