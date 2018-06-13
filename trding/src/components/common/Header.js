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
import { API_URL } from '../../config';
import { handleResponse } from '../../helpers';
import {UserContext} from '../../UserContext';
import FacebookLogin from 'react-facebook-login';


class Header extends React.Component {

constructor(props) {
  super(props);

  this.state = {
    user: {logged: false, user: undefined},
  }

  this.handleFacebook = this.handleFacebook.bind(this);
}

handleFacebook(setUser) {
  return (response)=>{
    setUser(true, {email: response.email, displayName: response.name, photoURL: response.picture.data.url})
    fetch("http://209.97.129.204/users/auth", {
      method: "post",
      body: {user_email: response.email}
    }).then((resp)=>{
        console.log(resp)
    })
  }
}


componentDidMount() {
   
 }

componentWillUnmount() {
    this.unregisterAuthObserver();
}

  render(){
    return (<UserContext.Consumer>
      {({user, setUser})=>{
        if(!user.logged){
          return (
            <div className="Header">
   
             <Link to="/">
                 <img src={logo} alt='logo' className="Header-logo"/>
             </Link>
   
             <Search />
             <FacebookLogin
                appId="141112822652545"
                autoLoad={false}
                fields="name,email,picture"
                
                callback={this.handleFacebook(setUser)} />
              {/*<button type="button" className="loginBtn loginBtn--facebook" onClick={this.handleFacebook(setUser)}> Sign in with Facebook </button>*/}
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
                <span className="Header-name">{user.data.displayName}</span>
              </div>
              <img src={user.data.photoURL} alt='profile-picture' className="Header-picture"/>
              <DropDownButton/>
            </div>
    
          );
        }
      }}
      </UserContext.Consumer>
    )
     
  }
}

 export default Header;
