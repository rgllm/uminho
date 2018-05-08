import React from 'react';
import './DropDownButton.css';
import PropTypes from 'prop-types';
import Dropdown from 'rc-dropdown';
import 'rc-dropdown/assets/index.css';
import { Link } from 'react-router-dom';

import Menu, { Item as MenuItem, Divider } from 'rc-menu';
//var DDButton = require('react-dropdown-button')

const DropDownButton = (props) => {
  function onSelect({ key }) {
    console.log(`${key} selected`);
  }

  function onVisibleChange(visible) {
    console.log(visible);
  }
  const menu = (
    <Menu onSelect={onSelect}>
<<<<<<< HEAD
        <MenuItem><Link to="/account">My Account</Link></MenuItem>
        <MenuItem><Link to="/portfolio">Portfolio</Link></MenuItem>
        <MenuItem><Link to="/history">Transaction History</Link></MenuItem>
=======
        <MenuItem><Link to="/watchlist">WatchList</Link></MenuItem>
        <MenuItem><Link to="/myassets">Portfolio</Link></MenuItem>
        <MenuItem><Link to="/myhistory">History</Link></MenuItem>
>>>>>>> 3a13ba0cd0f96bc3daad639d7e2dc07b0c29cdbb
    </Menu>
  );

  return (
    <div style={{ margin: 20 }}>
      <div>
        <Dropdown
          trigger={['click']}
          overlay={menu}
          animation="slide-up"
          onVisibleChange={onVisibleChange}
          overlayClassName="dropDownButton"
        >
          <button className="dropDownButton"><i class="fas fa-angle-down fa-lg"></i></button>
        </Dropdown>
      </div>
    </div>
  );
}


export default DropDownButton;
