import React from 'react';
import { STRIPE_KEY } from '../../config';
import './Account.css';
import StripeCheckout from 'react-stripe-checkout';
import * as firebase from 'firebase';
import firebaseApp from '../../firebase/Firebase';
import {UserContext} from '../../UserContext';

const fromUSDToCent = amount => amount * 100;

class Account extends React.Component{
  constructor(){
    super();

    this.state = {
      logged: false,
      userProfile: "",
      balance: 0,
      depositValue: '',
    };

    this.handleChange = this.handleChange.bind(this);
    this.onToken = this.onToken.bind(this);
  }

  componentDidMount() {
     firebase.auth().onAuthStateChanged(function(user) {
       if (user) {
         this.setState({
           logged: true,
           userProfile: user,
         });
       } else {
       }
     }.bind(this));
   }


  handleChange(event){
    const amount = Number(event.target.value);

    this.setState({ depositValue: Number(amount) });

    if(!amount){
      return '';
    }
  }

  refreshState(user){
    if(  user.logged != this.state.logged
      || user.data.email != this.state.userProfile.email
      || user.data.displayName != this.state.displayName
      || user.data.balance != this.state.balance){
        console.log(user)
        this.setState({
          logged: user.logged,
          userProfile: {...user.data}
        })
    }
  }

  onToken(token) {
    const { balance, depositValue } = this.state;
    this.setState({ balance: Number(balance) + Number(depositValue)});
  }

  render(){
    
    return (
      <UserContext.Consumer>
        {({user, setUser})=>{
          if(!user.logged){
            return(
              <div className="Account">
                <h1>Please login first</h1>
              </div>
            )
          }
          else{
            let login
            console.log(user)
            this.refreshState(user)
            const { logged, userProfile, balance, depositValue } = this.state;
            return(
              <div className="Account">
                <h1 className="Account-heading">
                  Your Account Information
                </h1>
                <div className="Account-container">
                  <div className="Account-item">
                    Name: <span className="Account-value">{userProfile.displayName}</span>
                  </div>
                  <div className="Account-item">
                    Email: <span className="Account-value">{userProfile.email}</span>
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
                      email={userProfile.email}
                      token={this.onToken}
                      stripeKey={STRIPE_KEY}
                      amount={fromUSDToCent(depositValue)}
                      currency="USD"
                      bitcoin={true}
                      >
                      <button className="Pay-button">
                        Pay with Credit Card
                      </button>
                    </StripeCheckout>
                  </div>
                </div>
              </div>

            )
          }
        }
        }
      </UserContext.Consumer>
    )
  }
}

export default Account;
