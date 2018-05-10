import React from 'react';
import ReactDOM from 'react-dom';
import { handleResponse } from '../../helpers';
import './MyAssets.css';
import { API_URL } from '../../config';
import TransactionTable from './TransactionTable'
import Loading from '../common/Loading';
import Pagination from '../list/Pagination'

class MyAssets extends React.Component {

	constructor(){
	    super();

	    this.state = {
	      loading: false,
	      transactions: [],
	      currencies: [],
	      error: null,
	      totalPages: 0,
	      page: 1,
	    };

	    this.handlePaginationClick = this.handlePaginationClick.bind(this);
	    this.handleCloseClick = this.handleCloseClick.bind(this);

	}

	componentDidMount(){
		this.fetchTransaction();
	}

	fetchTransaction(){
		this.setState({loading:true});

		fetch(`//api.jsonbin.io/b/5af1dc79c2e3344ccd96b341`)
			.then(handleResponse)
			.then((data) => {

				this.setState({
					transactions: data.transactions,
					totalPages: data.totalPages,
				});

			    data.transactions.map((transaction) => {
			    	fetch(`${API_URL}/cryptocurrencies/${transaction.code}`)
			      	.then(handleResponse)
		      		.then(( currency) => {
			        	this.setState(previousState => ({
    						currencies: previousState.currencies.concat(currency.price.replace(/,/g, ''))
						}));
			      	})
			      	.catch((error) => {
			        	this.setState({
			          		loading: false,
				          	error: error.errorMessage,
			      		});
			        });
		      	});

		      	this.setState({loading:false});

			})
			.catch((error) => {
				this.setState({error: error.error, loading: false})
			});
	}



	handlePaginationClick(direction){
		 let nextPage = this.state.page;

		 if(direction === 'next'){
		   nextPage++;
		 }else{
		   nextPage--;
		 }
		 this.setState({page: nextPage}, () => {
		   this.fetchTransaction();
		 });
	}

	handleCloseClick(){
		window.confirm("Close asset?");
	}

	render(){
		const { loading, error, transactions, currencies, page, totalPages } = this.state;

		if(loading){
			return <div className="loading-container"><Loading /></div>
		}

		if(error){
			return <div className="error">{error}</div>
		}

		return(
				<div>
				<TransactionTable
					transactions={transactions}
					currencies={currencies}
					handleCloseClick={this.handleCloseClick}
				/>

			<Pagination
				page={page}
				totalPages={totalPages}
				handlePaginationClick={this.handlePaginationClick}
			/>
			</div>
		);
	}
}

export default MyAssets;
