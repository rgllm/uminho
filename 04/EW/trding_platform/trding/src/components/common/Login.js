import React from 'react'
import FacebookLogin from 'react-facebook-login'
import './Login.css';
import * as firebase from 'firebase';
import firebaseApp from '../../firebase/Firebase';

class Login extends React.Component {
	constructor(props) {
    	super(props);
      this.state = {
        name: "",
        email: "",
        picture: "",
  		};
  }

  handleFacebook(e) {
      e.preventDefault();
      var provider = new firebase.auth.FacebookAuthProvider();
      firebaseApp.auth().signInWithPopup(provider).then(function(result) {
        // This gives you a Facebook Access Token. You can use it to access the Facebook API.
        // var token = result.credential.accessToken;
        // The signed-in user info.
        var user = result.user;
        const name = user.displayName;
        //this.setState({ name: user.displayName, email: user.email, picture: user.photoURL});
        console.log(user);
        console.log("Name", user.displayName);
      }).catch(function(error) {
        var errorMessage = error.message;
        alert("Facebook sign in error: "+ errorMessage);
      });
    }

    render() {
    return (
      <div>
        <a className="btn btn-block btn-social btn-facebook" onClick={this.handleFacebook}>
          Sign in with Facebook
        </a>
      </div>
    );
  }
}

export default Login;
