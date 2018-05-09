import React from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import './Header.css';
import logo from './logo.png';
import Search from './Search';
import DropDownButton from './DropDownButton';
import Login from './Login.js';
import config from 'react-global-configuration';

class Header extends React.Component {
  constructor(){
    super();

    this.state = {
			login: false,
      name: null,
      email: null,
      picture: null,
		};

    this.handleLogin = this.handleLogin.bind(this);
  }

  handleLogin(response){
      const name = response.name;
      const email = response.email;
      const picture = response.picture.data.url;
      this.setState({ login: true, name: name, email: email, picture: picture});
      config.set({ login: true, name: name, email: email  });

}

  render(){

    const { login, name, email, picture } = this.state;

    if(!login){
      return (
     		<div className="Header">

          <Link to="/">
     		     <img src={logo} alt='logo' className="Header-logo"/>
          </Link>

          <Search />

            <Login onLogin={this.handleLogin}/>
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
            <span className="Header-name">{name}</span>
          </div>
          <img src={picture} alt='profile-picture' className="Header-picture"/>
          <DropDownButton/>
        </div>

      );
    }
  }
 }

 export default Header;
