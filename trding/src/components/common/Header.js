import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import './Header.css';
import logo from './logo.png';
import Search from './Search';
import FacebookLogin from 'react-facebook-login';

 const Header = () =>{
 	return (
 		<div className="Header">

      <Link to="/">
 		     <img src={logo} alt='logo' className="Header-logo"/>
      </Link>

      <Search />

        <FacebookLogin
            appId="1088597931155576"
            autoLoad={true}
            fields="name,email,picture"
            cssClass="Login-button"/>
 		</div>
 	);
 }

 export default Header;
