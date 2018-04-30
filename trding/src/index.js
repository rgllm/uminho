import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Header from './components/common/Header';
import Footer from './components/common/Footer'
import './index.css';
import List from './components/list/List';
import NotFound from './components/notfound/NotFound';
import Detail from './components/detail/Detail';

const Main = () => {
	return(
    <BrowserRouter>
			<div>
			<Header />
				<Switch>
					<Route path="/" component={List} exact />
					<Route path="/currency/:id" component={Detail}  exact />
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
