import React, { Component } from 'react';
import logo from './logo.svg';
import './SearchBar.css';

export class SearchBar extends Component {
  render() {
    return (
      <div className="SearchBar">
        <input type="text" placeholder="Search.." name="search"/>
        <button type="submit">⌕</button>
      </div>
    );
  }
}

