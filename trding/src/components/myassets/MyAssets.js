import React from 'react';
import ReactDOM from 'react-dom';
import { handleResponse } from '../../helpers';
import './MyAssets.css';
import { API_URL } from '../../config';
import TransactionTable from './TransactionTable'
import Loading from '../common/Loading';
import Pagination from '../list/Pagination'
import {UserContext} from '../../UserContext';

class MyAssets extends React.Component {

	constructor(props){
	    super(props);
		
	    this.handlePaginationClick = this.handlePaginationClick.bind(this);
		this.handleCloseClick = this.handleCloseClick.bind(this);
		this.fetchPortfolio = this.fetchPortfolio.bind(this);

			this.state = {
				loading: false,
				currencies: [],
				error: null,
				totalPages: 1,
				page: 1,
			};
		
	}

	componentWillMount(){	
		this.fetchPortfolio(this.props.user).then((currencies)=>{
			this.setState({currencies: currencies, loading:false})
		})

	}


	 async fetchPortfolio (user) {
		this.setState({loading: true})
		const portfolio = await fetch(`${API_URL}/users/portfolio`, {
			method: "post",
			headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'}),
			body: "user_email="+user.data.email
		})
		const json = await handleResponse(portfolio)
		
		if(json.success){
			const currencies = new Array();
			const port = json.success
				
			for(let currency of port){
				const currency_id = currency.currency_id;
				const curr = await fetch(`${API_URL}/crypto/cryptocurrency/${currency_id}`)
				const currJSON = await handleResponse(curr)
				currencies.push(currJSON)
			}
			return currencies
		}
		else{
			return null
		}
	}


	handlePaginationClick(user){
		return (direction)=>{
			let nextPage = this.state.page;

			if(direction === 'next'){
				nextPage++;
			}else{
				nextPage--;
			}
			this.setState({page: nextPage}, () => {
			});
		}
	}

	handleCloseClick(user, setUser){
		return (action_id)=>{
			if(window.confirm("Close asset?")){
				this.setState({loading:true})
				alert(action_id)
				fetch(`${API_URL}/users/portfolio/close/${action_id}`,{
					method: "post",
					headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'}),
					body: "user_email="+user.data.email
				  })
				  .then(handleResponse)
				  .then(res=>{
					if(res.success){
						setUser(true, {...user.data, balance: res.user.balance, history: res.user.history, portfolio: res.user.portfolio})

						alert(res.success)
						this.setState({loading:false})
					}
					else{
						alert(res.error)
					}
				  })
			}
		}
	}

	render(){
		const { loading, error, portfolio, currencies, page, totalPages } = this.state;	
		const {user, setUser} = this.props
		if(loading){
			return <div className="loading-container"><Loading /></div>
		}

		if(error){
			return <div className="error">{error}</div>
		}

		if(user.logged==false){
			return(<div className="MyAssets">
				<div>Please login first</div>
			</div>)
		}else if(this.state.currencies.length==0){
			return (<div>Your portfolio is empty at the moment</div>)
		}
		else{
			
			return(
				<div>
					<TransactionTable
						portfolio={user.data.portfolio}
						currencies={this.state.currencies}
						handleCloseClick={this.handleCloseClick(user, setUser)}
					/>

					<Pagination
						page={page}
						totalPages={totalPages}
						handlePaginationClick={this.handlePaginationClick(user)}
					/>
				</div>
			)
			
		}
	}
}

export default props => ( <UserContext.Consumer>
	{({user, setUser}) => {
	   return <MyAssets {...props} user={user} setUser={setUser} />
	}}
  </UserContext.Consumer>
)

//export default MyAssets;
