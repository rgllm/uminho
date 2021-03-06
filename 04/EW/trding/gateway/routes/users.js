var express = require('express');
var router = express.Router();
var users_service_URL = "http://206.189.27.195"
var request = require('request')

router.post('/auth', function(req,res,next){
  res.redirect(307, users_service_URL + '/auth')
})

router.post('/balance/add', function(req,res,next){
  res.redirect(307, users_service_URL + '/balance/add')
})

router.post('/portfolio', function(req,res,next){
  res.redirect(307, users_service_URL + '/portfolio')
})

router.post('/portfolio/add', function(req,res,next){
  res.redirect(307, users_service_URL + '/portfolio/add')
})

router.post('/portfolio/close/:action_id', function(req,res,next){
  res.redirect(307, users_service_URL + '/portfolio/close/' + req.params.action_id)
})

router.post('/watchlist', function(req,res,next){
  res.redirect(307, users_service_URL + '/watchlist')
})

router.post('/watchlist/add', function(req,res,next){
  res.redirect(307, users_service_URL + '/watchlist/add')
})

router.post('/watchlist/remove', function(req,res,next){
  res.redirect(307, users_service_URL + '/watchlist/remove')
})

router.post('/history', function(req,res,next){
  res.redirect(307, users_service_URL + '/history')
})

router.post('/auth/facebook', function(req,res,next){
  res.redirect(307, users_service_URL + '/auth/facebook')
})

module.exports = router;
