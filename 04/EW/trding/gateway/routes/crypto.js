var express = require('express');
var router = express.Router();
var request = require('request')


var crypto_service_URL = "http://167.99.193.161"
/* GET home page. */
router.get('/cryptocurrency/:id', function(req, res, next) {
  var url = crypto_service_URL + req.url
  req.pipe(request(url)).pipe(res)
});

router.get('/cryptocurrencies/', function(req, res, next) {
  var url = crypto_service_URL + req.url
  req.pipe(request(url)).pipe(res)
});

router.get('/autocomplete/', function(req, res, next) {
  var url = crypto_service_URL + req.url
  req.pipe(request(url)).pipe(res)
});

module.exports = router;
