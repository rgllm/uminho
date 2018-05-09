import React from 'react';
import { STRIPE_KEY } from '../../config';
import './Account.css';
import StripeCheckout from 'react-stripe-checkout';
import Login from '../common/Login.js';
import config from 'react-global-configuration';

const fromUSDToCent = amount => amount * 100;

class Account extends React.Component{
  constructor(){
    super();

    this.state = {
      logged: false,
      name: '',
      email: '',
      balance: 0,
      depositValue: '',
    };

    this.handleChange = this.handleChange.bind(this);
    this.onToken = this.onToken.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
  }

  handleLogin(response){
      const name = response.name;
      const email = response.email;
      this.setState({ logged: true, name: name, email: email});
  }

  componentDidMount(){
    const name = config.get('name');
    if(name != null){
      const email = config.get('email');
      this.setState({ logged: true, name: name, email: email});
    }
  }

  handleChange(event){
    const amount = Number(event.target.value);

    this.setState({ depositValue: Number(amount) });

    if(!amount){
      return '';
    }
  }

  onToken(token) {
    const { balance, depositValue } = this.state;
    this.setState({ balance: Number(balance) + Number(depositValue)});
  }

  render(){
    let login


    const { logged, name, email, balance, depositValue } = this.state;

    if(!logged){
      return(
          <div className="Account">
            <h1>Please login first</h1>
            <Login onLogin={this.handleLogin}/>
          </div>
      );
    }
    else{
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
              Balance: <span className="Account-value">{Number(balance)} $</span>
            </div>
            <div className="Account-item">
              Deposit Funds:
              <input
                className="Deposit-input"
                type="number"
                min="0.00"
                step="10000.00"
                placeholder="Value to deposit"
                onChange={this.handleChange}
                value={depositValue}
                /> $
              <br/>
              <br/>
              <StripeCheckout
                name="Trding App"
                description="Deposit funds on your account."
                email={email}
                token={this.onToken}
                stripeKey={STRIPE_KEY}
                amount={fromUSDToCent(depositValue)}
                currency="USD"
                bitcoin={true}
                alipay={true}
                >
                <button className="Pay-button">
                  Pay with Credit Card
                </button>
              </StripeCheckout>
            </div>
          </div>
        </div>
  );}
}
}

export default Account;
