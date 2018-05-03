import React from 'react';
import ReactDOM from 'react-dom';
import { handleResponse } from '../../helpers';
import './MyAssets.css';
import TransactionTable from './TransactionTable'
import Loading from '../common/Loading';
import Pagination from '../list/Pagination'

class MyAssets extends React.Component {
  
	constructor(){
	    super();

	    this.state = {
	      loading: false,
	      transactions: [],
	      error: null,
	      totalPages: 0,
	      page: 1,
	    };

	    this.handlePaginationClick = this.handlePaginationClick.bind(this);
	}

	componentDidMount(){
		this.fetchCurrencies();
	}

	fetchCurrencies(){

		this.setState({loading:true});

		fetch(`http://api.jsonbin.io/b/5aeb51fd7a973f4ce578224d`)
			.then(handleResponse)
			.then((data) => {
				this.setState({
					transactions: data.transactions,
					loading: false,
					totalPages: data.totalPages,
				});
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
		   //call fetchCurrencies inside setState callback
		   //to make sure first state page is updated
		   this.fetchCurrencies();
		 });
	}

	render(){
		const { loading, error, transactions, page, totalPages } = this.state;

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
