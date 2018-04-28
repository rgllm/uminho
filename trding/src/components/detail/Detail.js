import React from 'react';
import { API_URL } from '../../config';
import { handleResponse, renderChangePercent } from '../../helpers';
import Loading from '../common/Loading';
import './Detail.css';

class Detail extends React.Component{
  constructor(){
    super();

    this.state = {
      currency: {},
      loading: false,
      error: null,
    };
  }

  componentDidMount(){
    const currencyId = this.props.match.params.id;
    this.fetchCurrency(currencyId);
  }

  componentWillReceiveProps(nextProps){
    if(this.props.location.pathname !== nextProps.location.pathname) {

      //get new currency id from url
      const newCurrency = nextProps.match.params.id;
          this.fetchCurrency(newCurrency);
    }
  }

  fetchCurrency(currencyId){
    this.setState({ loading:true });

    fetch(`${API_URL}/cryptocurrencies/${currencyId}`)
      .then(handleResponse)
      .then((currency) => {
        this.setState({
          loading: false,
          error: null,
          currency: currency,
      });
      })
      .catch((error) => {
        this.setState({
          loading: false,
          error: error.errorMessage,
      });
      });

  }

  render(){
    const { loading, error, currency } = this.state;
    if(loading){
      return <div className="loading-container"><Loading/></div>
    }

    if(error){
      return <div className="error">{error}</div>
    }
    return(
        <div className="Detail">
          <h1 className="Detail-heading">
            {currency.name} ({currency.symbol})
          </h1>
          <div className="Detail-container">
            <div className="Detail-item">
              Price <span className="Detail-value">$ {currency.price}</span>
            </div>
            <div className="Detail-item">
              Rank <span className="Detail-value">{currency.rank}</span>
            </div>
            <div className="Detail-item">
              24h Change
              <span className="Detail-value">{renderChangePercent(currency.percentChange24h)}</span>
            </div>
            <div className="Detail-item">
              <span className="Detail-title">Market cap</span>
              <span className="Detail-dollar">$</span>
              {currency.marketCap}
            </div>
            <div className="Detail-item">
              <span className="Detail-title">24H Volume</span>
              <span className="Detail-dollar">$</span>
              {currency.volume24h}
            </div>
            <div className="Detail-item">
              <span className="Detail-title">Total supply</span>
              {currency.totalSupply}
            </div>
          </div>
        </div>
      );
  }
}

export default Detail;
