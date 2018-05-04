import React from 'react'
import StripeCheckout from 'react-stripe-checkout';
import './Checkout.css';

class Checkout extends React.Component {

  onToken = (token) => {
    console.log(token)
  }

  // ...

  render() {
    return (
      // ...
      <StripeCheckout
        token={this.onToken}
        stripeKey="pk_test_6pRNASCoBOKtIshFeQd4XMUh"
        amount={100000} // cents
        currency="EUR"
      />
    )
  }
}

 export default Checkout;
