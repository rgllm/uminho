var express = require('express');
var router = express.Router();
var users_service_URL = "http://206.189.27.195"

router.get('/login/success', function(req,res,next){
  res.redirect(users_service_URL + '/login/success')
})

router.get('/login/failed', function(req,res,next){
  res.redirect(users_service_URL + '/login/failed')
})

router.post('/balance/add', function(req,res,next){
  res.redirect(users_service_URL + '/balance/add')
})

router.get('/portfolio', function(req,res,next){
  res.redirect(users_service_URL + '/portfolio')
})

router.post('/portfolio/add', function(req,res,next){
  res.redirect(users_service_URL + '/portfolio/add')
})

router.get('/portfolio/close/:action_id', function(req,res,next){
  res.redirect(users_service_URL + '/portfolio/close')
})

router.get('/watchlist', function(req,res,next){
  res.redirect(users_service_URL + '/watchlist')
})

router.post('/watchlist/add', function(req,res,next){
  res.redirect(users_service_URL + '/watchlist/add')
})

router.post('/watchlist/remove', function(req,res,next){
  res.redirect(users_service_URL + '/watchlist/remove')
})

router.get('/history', function(req,res,next){
  res.redirect(users_service_URL + '/history')
})

router.get('/auth/facebook', function(req,res,next){
  res.redirect(users_service_URL + '/auth/facebook')
})

module.exports = router;
