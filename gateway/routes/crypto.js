var express = require('express');
var router = express.Router();
var request = require('request')


var crypto_service_URL = "http://167.99.193.161"
/* GET home page. */
router.get('/cryptocurrency/:id', function(req, res, next) {
  res.redirect(crypto_service_URL + "/cryptocurrency" + "/" + req.params.id)
});

router.get('/cryptocurrencies/', function(req, res, next) {
  /*
  var query = "?"
  if(req.query.page){
    query+= "page=" + req.query.page
    if(req.query.perPage){
      query+= "&perPage=" + req.query.perPage
    }
  }*/
  request({
    url: req.query.url,
    method: req.query.method
  }).pipe(res);
  ///res.redirect(crypto_service_URL + `/cryptocurrencies${query!="?" ? query : "" }`)
});

router.get('/autocomplete/', function(req, res, next) {
  res.redirect(crypto_service_URL + "/autocomplete" + `?searchQuery=${req.query.searchQuery}`  )
});

module.exports = router;
