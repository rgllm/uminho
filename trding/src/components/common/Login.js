import React from 'react'
import FacebookLogin from 'react-facebook-login'
import './Login.css';

export default function Login (props) {
  return (
    <FacebookLogin
      appId="141112822652545"
      autoLoad={true}
      fields="name,email,picture"
      cssClass="loginBtn loginBtn--facebook"
      textButton="Login"
      callback={props.onLogin}
    />
  )
}
