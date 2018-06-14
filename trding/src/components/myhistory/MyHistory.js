import React from 'react';
import ReactDOM from 'react-dom';
import { handleResponse } from '../../helpers';
import './MyHistory.css';
import myData from './transaction_2.json';
import HistoryTable from './HistoryTable'
import Loading from '../common/Loading';
import Pagination from '../list/Pagination'
import { API_URL } from '../../config';
import {UserContext} from '../../UserContext'
class MyHistory extends React.Component {

  constructor(props) {
    super();

    this.state = {
      loading: false,
      transactions: [],
      error: null,
      totalPages: 1,
      page: 1,
    };
    this.handlePaginationClick = this.handlePaginationClick.bind(this);
  }

  componentWillMount() {
    this.fetchCurrencies(this.props.user);
  }

  handlePaginationClick(direction) {
    let nextPage = this.state.page;

    if (direction === 'next') {
      nextPage++;
    } else {
      nextPage--;
    }
    this.setState({ page: nextPage }, () => {
      this.fetchCurrencies(this.props.user);
    });
  }

  fetchCurrencies(user) {

    this.setState({ loading: true });

    fetch(`${API_URL}/users/history`,{
      method: "post",
      headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'}),
      body: "user_email="+user.data.email
    })
      .then(handleResponse)
      .then((data) => {
        alert(JSON.stringify(data, null, 2))
        this.setState({
          transactions: data.success,
          loading: false,
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

export default props => ( <UserContext.Consumer>
	{({user, setUser}) => {
	   return <MyHistory {...props} user={user} setUser={setUser} />
	}}
  </UserContext.Consumer>
)

//export default MyHistory;
