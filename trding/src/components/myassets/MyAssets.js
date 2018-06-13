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
		this.fetchPortfolio = this.fetchPortfolio.bind(this);
		this.fetchAll = this.fetchAll.bind(this);

	}

	componentDidMount(){	
		this.fetchPortfolio();
		
	}

	/*fetchPortfolio() {

		this.setState({ loading: true });

		fetch(`//api.jsonbin.io/b/5b203550c83f6d4cc734b323`)
			.then(handleResponse)
			.then((data) => {
				this.setState({
					portfolio: data,
				})
				return data;
			})
			.then((data) => {

				var arr = [];
				data.map((transaction) => {
					fetch(`${API_URL}/cryptocurrency/${transaction.currency_id}`)
						.then(handleResponse)
						.then((currency) => {
							arr.push(currency)
						})
						.catch((error) => {
							this.setState({
								loading: false,
								error: error.errorMessage,
							});
						});
				});
				console.log(arr);

				return (data,arr);
			})
			.then((data,arr) => {
				console.log("DATAA" , data);
				console.log("CUUURENCIES", arr)
				this.setState({
					loading: false,
					portfolio: data,
					currencies : arr
				})
			})

	}*/

	fetchAll(){

		return fetch(`${API_URL}/autocomplete/`)
			.then(handleResponse)
			.then((data) => {
				return data;
			})
			.catch(() => {
				console.log("error");
			});
	}


	fetchPortfolio() {

		var all = this.fetchAll();
		fetch(`//api.jsonbin.io/b/5b203550c83f6d4cc734b323`)
			.then(handleResponse)
			.then((data) => {
				var oldcurrencies = new Array();
				var currencies = new Array();
				all.then((allObjects) => {
					
					data.forEach(function(entry) {
						const currency_id = entry.currency_id;
						const result = allObjects.filter(function (entry) { return entry.id === currency_id;});
						oldcurrencies.push(result);
					});
					console.log("ALLOBJETS: ",allObjects);
					console.log("DATA: ", data);
					oldcurrencies.forEach(function(nentry){ 
						currencies.push(nentry["0"]);
					});
					console.log("CURRENCIES: ", currencies);
				});
			return([data, currencies]);
			})
			.then(([data,currencies]) => {
				console.log("DATA STATE", data);
				console.log("CURRENCIES STSTE",currencies);
				this.setState({
					loading: false,
					portfolio: data,
					currencies: currencies,
				});
			})
			
			
			
			/*then(() => {
				Promise.all(
				history.forEach(function(index) {
					fetch(`${API_URL}/cryptocurrency/${index.currency_id}`)
						.then(handleResponse)
						.then((currency) => {
							//user[i] = currency.price;
							//i++;
							console.log("CURRENCY: ", currency);
							//novo.push(currency);
							//console.log("CUREENCY",currency);
							//user.push(currency);
							//user.push(currency.price);
							//user.push(new Object(currency));
							//user.push(currency);
						})
						.catch((error) => {
							this.setState({
								error: error.errorMessage
							});
						});
				}));
				//console.log("NOVO::::: ",novo);
				history.map((action) => {
					fetch(`${API_URL}/cryptocurrency/${action.currency_id}`)
						.then(handleResponse)
						.then((currency) => {
							//user[i] = currency.price;
							//i++;
							console.log(currency);
							user.push(currency);
							//user.push(currency.price);
							//user.push(new Object(currency));
							//user.push(currency);
						})
						.catch((error) => {
							this.setState({
								error: error.errorMessage
							});
						});
				});
			})*/
			/*.then(() => {
				this.setState({
					loading: false,
					portfolio: history,
					currencies: user,
				})
			})*/
			.catch((error) => {
				this.setState({
					error: error.errorMessage
				});
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
