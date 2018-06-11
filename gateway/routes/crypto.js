var express = require('express');
var router = express.Router();

var crypto_service_URL = "http://167.99.193.161"
/* GET home page. */
router.get('/cryptocurrency/:id', function(req, res, next) {
  res.redirect(crypto_service_URL + "/cryptocurrency" + "/" + req.params.id)
});

router.get('/cryptocurrencies/', function(req, res, next) {
  res.redirect(crypto_service_URL + "/cryptocurrencies")
});

router.get('/autocomplete/', function(req, res, next) {
  res.redirect(crypto_service_URL + "/autocomplete")
});

module.exports = router;
