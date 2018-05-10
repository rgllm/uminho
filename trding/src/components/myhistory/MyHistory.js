import React from 'react';
import ReactDOM from 'react-dom';
import { handleResponse } from '../../helpers';
import './MyHistory.css';
import myData from './transaction_2.json';
import HistoryTable from './HistoryTable'
import Loading from '../common/Loading';
import Pagination from '../list/Pagination'

class MyHistory extends React.Component {

  constructor() {
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

  componentDidMount() {
    this.fetchCurrencies();
  }

  handlePaginationClick(direction) {
    let nextPage = this.state.page;

    if (direction === 'next') {
      nextPage++;
    } else {
      nextPage--;
    }
    this.setState({ page: nextPage }, () => {
      //call fetchCurrencies inside setState callback
      //to make sure first state page is updated
      this.fetchCurrencies();
    });
  }

  fetchCurrencies() {

    this.setState({ loading: true });

    fetch(`http://api.jsonbin.io/b/5af07f23c2e3344ccd96b22d`)
      .then(handleResponse)
      .then((data) => {
        this.setState({
          transactions: data.transactions,
          loading: false,
          totalPages: data.totalPages,
        });
      })
      .catch((error) => {
        this.setState({ error: error.error, loading: false })
      });
  }

  render() {
    const { loading, error, transactions, page, totalPages } = this.state;

    if (loading) {
      return <div className="loading-container"><Loading /></div>
    }

    if (error) {
      return <div className="error">{error}</div>
    }

    return (
      <div>
        <HistoryTable
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

export default MyHistory;
