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
	      portfolio: [],
	      currencies: [],
	      error: null,
	      totalPages: 0,
	      page: 1,
	    };

	    this.handlePaginationClick = this.handlePaginationClick.bind(this);
	    this.handleCloseClick = this.handleCloseClick.bind(this);

	}

	componentDidMount(){
		this.fetchPortfolio();
	}

	fetchPortfolio(){
		this.setState({loading:true});

		fetch(`//api.jsonbin.io/b/5b203550c83f6d4cc734b323`)
			.then(handleResponse)
			.then((data) => {

				this.setState({
					portfolio: data,
					loading: false,
				});

			    this.portfolio.map((action) => {
					fetch(`${API_URL}/cryptocurrency/${action.currency_id}`)
			      	.then(handleResponse)
		      		.then((currency) => {
			        	this.setState({
    						currencies: currency,
						});
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
		   this.fetchPortfololio();
		 });
	}

	handleCloseClick(){
		window.confirm("Close asset?");
	}

	render(){
		const { loading, error, portfolio, currencies, page, totalPages } = this.state;

		if(loading){
			return <div className="loading-container"><Loading /></div>
		}

		if(error){
			return <div className="error">{error}</div>
		}

		return(
				<div>
				<TransactionTable
					portfolio={portfolio}
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
