import React from 'react';
import { API_URL } from '../../config';
import { handleResponse, renderChangePercent } from '../../helpers';
import Loading from '../common/Loading';
import './Detail.css';
import Modal from 'react-modal';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';


const appElement = document.createElement("el");
document.body.appendChild(appElement);
Modal.setAppElement(appElement);


class Detail extends React.Component{

  constructor(){
    super();

    this.state = {
      currency: {},
      loading: false,
      error: null,
      modalIsOpen: false,
      };

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);

  }

  openModal() {
    this.setState({ modalIsOpen: true });
  }

  afterOpenModal() {
  }

  closeModal() {
    this.setState({ modalIsOpen: false });
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

  render_tabs() {
    return (
      <Tabs>
        <TabList>
          <Tab className = "button-units">
            Number of Units
          </Tab>
          <Tab className = "button-amount">
           Amount
          </Tab> 
        </TabList>
        <div className="form">
        <TabPanel>
          <form className="inputs">
            <input placeholder="Number of Units" />
          </form>
        </TabPanel>
        <TabPanel>
          <form className="inputs" >
            <input placeholder="Amount" />
          </form>
        </TabPanel>
        </div>
        <button className="confirm-button">Confirm</button>
      </Tabs>
    );
  }

  render_modal(currency_name,current_price) {
    return(

      <el>
        <Modal
          ariaHideApp={true}
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          overlayClassName="backdrop"
          className= "modal"
        >
 
          <div className="currencyname">{currency_name}</div>
          <div className="currencyname">$ {current_price}</div>
          
          <div> {this.render_tabs()} </div>

          <button 
            onClick={this.closeModal}
            type="button" class="close" aria-label="Close" className = "closebutton">
            <span aria-hidden="true">&times;</span>
          </button>
        </Modal>
      </el>
    );
  }

  


  render(){

    const { loading, error, currency} = this.state;
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
        <div>
          <button
            className="Buy-button"
            onClick={this.openModal}>
            Buy {currency.symbol}
          </button>
          <button
          className="Sell-button"
          onClick={this.openModal}>
          Sell {currency.symbol}
          </button>
          {this.render_modal(currency.name, currency.price)}

        </div>
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
