import React, { Component } from 'react';
import './BodyContainer.css';
import {Footer} from './Footer';

export class BodyContainer extends Component {
  render() {
    switch(this.props.page){
      case 'watchlist':
        return (
          <div className="BodyContainer">
            <div className="bodyContent">
              <p>Watchlist</p>
              <Footer />
            </div>
          </div>
        );
        break;
      case 'portfolio':
        return (
          <div className="BodyContainer">
            <div className="bodyContent">
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
              <p>Portfolio</p>
            </div>

            <Footer />
          </div>
        );
        break;
      case 'funds':
        return (
          <div className="BodyContainer">
            <div className="bodyContent">
              <p>Funds</p>
              <Footer />
            </div>
          </div>
        );
        break;
      case 'history':
        return (
          <div className="BodyContainer">
            <div className="bodyContent">
              <p>History</p>
              <Footer />
            </div>
          </div>
        );
        break;
    }
    
  }
}

