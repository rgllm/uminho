import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import './Header.css';
import logo from './logo.png';
import Search from './Search';
import MyAssets from '../myassets/MyAssets';
import DropDownButton from './DropDownButton';
import * as firebase from 'firebase';
import firebaseApp from '../../firebase/Firebase';

class Header extends React.Component {

constructor(props) {
  super(props);

  this.state = {
    logged: false,
    userProfile: "",
  };

  this.handleFacebook = this.handleFacebook.bind(this);
}

handleFacebook() {
 
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

  componentWillUnmount() {
      this.unregisterAuthObserver();
  }

  render(){

    const{ logged, userProfile } = this.state;

    if(!logged){
      return (
     		<div className="Header">

          <Link to="/">
     		     <img src={logo} alt='logo' className="Header-logo"/>
          </Link>

          <Search />

            <button type="button" className="loginBtn loginBtn--facebook" onClick={this.handleFacebook}> Sign in with Facebook </button>
     		</div>
     	);
    }
    else{
      return (
        <div className="Header">

          <Link to="/">
             <img src={logo} alt='logo' className="Header-logo"/>
          </Link>

          <Search />

          <div>
            <span className="Header-name">{userProfile.displayName}</span>
          </div>
          <img src={userProfile.photoURL} alt='profile-picture' className="Header-picture"/>
          <DropDownButton/>
        </div>

      );
    }
  }
}

 export default Header;
