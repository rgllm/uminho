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
      currency_id: null,
      invested_: '',
      method_: null,
      loading: false,
      error: null,
      modalIsOpen: false,
      nunits: '',
      amount: '',
      };

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.publish = this.publish.bind(this);

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

    fetch(`${API_URL}/cryptocurrency/${currencyId}`)
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

  buyCurrency(currencyId){
    this.setState({ loading: true });

    return (fetch('http://206.189.27.195/portfolio/add', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          currency_id: this.currency,
          open_value: this.currency.price,
          invested: this.amount,
          method: this.method_,
        })
      })
      .then((res) => res.json())
      .catch((error) => {
        this.setState({
          loading: false,
          error: error.errorMessage,
        });
      }));
  }

  publish() {
    console.log("AMOUNT"+this.state.amount, "NUNITS"+this.state.nunits, "METHOD"+this.state.method_);
  }

  renderInvested(){
    if(this.state.amount === '') {
      this.setState({invested_ : this.nunits*this.open_value});
    }
    else this.setState({invested_: this.amount});
    
    console.log("INVESTED"+this.state.invested_);
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
              <input placeholder="Number of Units" value={this.state.nunits} onChange={(e) => { this.setState({ nunits: e.target.value }) }}
 />
          </form>
        </TabPanel>
        <TabPanel>
          <form className="inputs">
              <input placeholder="Amount" value={this.state.amount} id="amount" onChange={(e) => { this.setState({ amount: e.target.value }) }}
/>
          </form>
        </TabPanel>
        <div className="radio">
            <input type="radio" name="method" value="buy" onChange={(e) => { this.setState({ method_: e.target.value }) }}/> BUY
            <input type="radio" name="method" value="sell" onChange={(e) => { this.setState({ method_: e.target.value }) }} /> SELL
        </div>
        </div>
        <button className="confirm-button" onClick={this.publish} >Confirm</button>
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
          <div className="currencyname">${current_price}</div>
          
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

    const { loading, error, currency, method_, nunits, amount, invested_} = this.state;
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
              <span className="Detail-value">{renderChangePercent(currency.percentage24)}</span>
            </div>
            <div className="Detail-item">
              <span className="Detail-title">Market cap</span>
              <span className="Detail-dollar">$</span>
              {currency.market_cap}
            </div>
            <div className="Detail-item">
              <span className="Detail-title">24H Volume</span>
              <span className="Detail-dollar">$</span>
              {currency.volume24}
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
