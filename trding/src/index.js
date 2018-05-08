import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Header from './components/common/Header';
import Footer from './components/common/Footer'
import './index.css';
import List from './components/list/List';
import NotFound from './components/notfound/NotFound';
import Detail from './components/detail/Detail';
import MyAssets from './components/myassets/MyAssets';
<<<<<<< HEAD
import Account from './components/account/Account';
=======
import MyHistory from './components/myhistory/MyHistory';
>>>>>>> 3a13ba0cd0f96bc3daad639d7e2dc07b0c29cdbb

const Main = () => {
	return(
    <BrowserRouter>
			<div>
			<Header />
				<Switch>
					<Route path="/" component={List} exact />
					<Route path="/currency/:id" component={Detail}  exact />
<<<<<<< HEAD
					<Route path="/history" component={MyAssets} exact />
					<Route path="/account" component={Account} exact />
=======
					<Route path="/myassets" component={MyAssets} exact />
					<Route path="/myhistory" component={MyHistory} exact />
>>>>>>> 3a13ba0cd0f96bc3daad639d7e2dc07b0c29cdbb
					<Route component={NotFound} />
				</Switch>
				<Footer />
			</div>
    </BrowserRouter>
	);
}

ReactDOM.render(
	<Main />,
	document.getElementById('root')
);

/*
simple presentation components use functional components
class based component for state or lifecycle hooks
*/
