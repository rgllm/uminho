import React from 'react';
import { API_URL } from '../../config';
import { handleResponse, renderChangePercent } from '../../helpers';
import Loading from '../common/Loading';
import './Account.css';
import StripeCheckout from 'react-stripe-checkout';

class Account extends React.Component{
  constructor(){
    super();

    this.state = {
      name: 'John Doe',
      email: 'doe@coelho.pt',
      balance: '1',
    };
  }

  onToken = (token) => {
    console.log(token)
    this.setState({ balance: Number(this.state.balance) + Number(1000)});
  }

  render(){

    const { name, email, balance } = this.state;

    return(
        <div className="Account">
          <h1 className="Account-heading">
            Your Account Information
          </h1>
          <div className="Account-container">
            <div className="Account-item">
              Name: <span className="Account-value">{name}</span>
            </div>
            <div className="Account-item">
              Email: <span className="Account-value">{email}</span>
            </div>
            <div className="Account-item">
              Balance: <span className="Account-value">{balance} €</span>
            </div>
            <div className="Account-item">
              <StripeCheckout
                token={this.onToken}
                stripeKey="pk_test_6pRNASCoBOKtIshFeQd4XMUh"
                amount={100000} // cents
                currency="EUR"
              />
            </div>
          </div>
        </div>
      );
  }
}

export default Account;
