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
import MyHistory from './components/myhistory/MyHistory';
import Account from './components/account/Account';
import {UserContext} from './UserContext';
class Main extends React.Component{
	constructor(props) {
		super(props);
		
		this.setUser = (logged, user) =>{
			//console.log(user) 
			this.setState({
				user:{
					logged: logged,
					data: {...user}
				}
			})
		};
			
		
		
		this.state = {
			user: {logged: false, data: undefined},
			setUser: this.setUser
		}

	  }

	render() {
		return(
		<BrowserRouter>
			<UserContext.Provider value={this.state}>
				<div>
				<Header />
					<Switch>
						<Route path="/" component={List} exact />
						<Route path="/currency/:id" component={Detail}  exact />
						<Route path="/assets" component={MyAssets} exact />
						<Route path="/history" component={MyHistory} exact />
						<Route path="/account" component={Account} exact />
						<Route component={NotFound} />
					</Switch>
					<Footer />
				</div>
			</UserContext.Provider>
		</BrowserRouter>
		);
	}
}

ReactDOM.render(
	<Main />,
	document.getElementById('root')
);
