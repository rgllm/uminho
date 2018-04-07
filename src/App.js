import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {SearchBar} from './SearchBar';
import {SideBar} from './SideBar';
import {BodyContainer} from './BodyContainer';

class App extends Component {
  constructor(props){
    super(props);
    this.state = {page: 'watchlist'};
    this.changePage = this.changePage.bind(this);  
  }

  changePage(page){
    this.setState({page: page});
  }

  render() {
    return (
      <div className="App">
        <SearchBar />
        <SideBar changePage={this.changePage} />
        <BodyContainer page={this.state.page} />
        
      </div>
    );
  }
}

export default App;
