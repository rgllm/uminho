import React, { Component } from 'react';
import logo from './logo.svg';
import './SideBar.css';

export class SideBar extends Component {
  constructor(props){
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e){
    this.props.changePage(e.target.text.toLowerCase());
  }

  render() {
    return (
      <div className="SideBar">
        <a href="javascript:void()" onClick={this.handleClick}>Watchlist</a>
        <a href="javascript:void()" onClick={this.handleClick}>Portfolio</a>
        <a href="javascript:void()" onClick={this.handleClick}>Funds</a>
        <a href="javascript:void()" onClick={this.handleClick}>History</a>
      </div>
    );
  }
}

