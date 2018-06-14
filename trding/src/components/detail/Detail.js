import React from 'react';
import { API_URL } from '../../config';
import { handleResponse, renderChangePercent } from '../../helpers';
import Loading from '../common/Loading';
import './Detail.css';
import Modal from 'react-modal';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import { UserContext } from '../../UserContext'

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

    fetch(`${API_URL}/crypto/cryptocurrency/${currencyId}`)
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

  buyCurrency(user, setUser){
    return () =>{
      console.log(JSON.stringify(this.state,null,2))
      /* currency_id,open_value,invested,method */
      this.setState({ loading: true });
      var invested = this.state.amount == "" ? 0 : parseFloat(this.state.amount) 
      console.log("111 - " + invested)
      if(this.nunits!=""){
        invested = parseInt(this.state.nunits)* this.state.currency.price
      }
      console.log("222 - " + invested)
      console.log("##########")
      console.log(this.state.method_)
      fetch(`${API_URL}/users/portfolio/add`, {
        method: 'post',
        headers: new Headers({'Content-Type': 'application/x-www-form-urlencoded'}),
        body: `user_email=${user.data.email}&currency_id=${this.state.currency.id}&open_value=${this.state.currency.price}&invested=${invested}&method=${this.state.method_}`
      })
      .then((res) => res.json())
      .then(res=>{
        console.log(res)
        if(res.success){
          setUser(true, {...user.data, balance: res.user.balance, portfolio: res.user.portfolio})
        }
        else{
          alert(res.error)
        }
        this.setState({loading:false})
        this.closeModal()
      })
      .catch((error) => {
        this.setState({
          loading: false,
          error: error.errorMessage,
        });
      });
    }
  }

  publish(user, setUser) {
    console.log("AMOUNT: "+this.state.amount, "NUNITS: "+this.state.nunits, "METHOD: "+this.state.method_);
    
    

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
      <UserContext.Consumer>
        {({user, setUser})=>{
          if(user.logged){
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
                      <input placeholder="Number of Units" value={this.state.nunits} onChange={(e) => { this.setState({ nunits: e.target.value , amount: ""}) }}
        />
                  </form>
                </TabPanel>
                <TabPanel>
                  <form className="inputs">
                      <input placeholder="Amount" value={this.state.amount} id="amount" onChange={(e) => { this.setState({ amount: e.target.value, nunits: "" }) }}
        />
                  </form>
                </TabPanel>
                <div className="radio">
                    <input type="radio" name="method" value="buy" onChange={(e) => { this.setState({ method_: e.target.value }) }}/> BUY
                    <input type="radio" name="method" value="sell" onChange={(e) => { this.setState({ method_: e.target.value }) }} /> SELL
                </div>
                </div>
                <button className="confirm-button" onClick={this.buyCurrency(user, setUser)} >Confirm</button>
              </Tabs>
            );
          }
          else{
            return(<div>Please login first</div>)
          }
        }}
      </UserContext.Consumer>
    )
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
            type="button" className="close" aria-label="Close" className = "closebutton">
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
      <UserContext.Consumer>
        {({user, setUser})=>{
          if(user.logged){
            return (
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
          else{
            return(<div>Please login first</div>)
          }
        }}
        </UserContext.Consumer>
    )
      
  }
}


export default Detail;
