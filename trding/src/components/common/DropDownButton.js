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
        <MenuItem><Link to="/watchlist">WatchList</Link></MenuItem>
        <MenuItem><Link to="/myassets">Portfolio</Link></MenuItem>
        <MenuItem><Link to="/myhistory">History</Link></MenuItem>
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
          <button class="dropDownButton"><i class="fas fa-angle-down fa-lg"></i></button>
        </Dropdown>
      </div>
    </div>
  );
}


export default DropDownButton;
