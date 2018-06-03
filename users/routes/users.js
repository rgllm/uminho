var express = require('express');
var User = require('../models/User')
var router = express.Router();

/* GET user's portfolio */
router.get('/portfolio', function(req, res, next) {
  if(req.isAuthenticated()){
    res.send(req.user.portfolio)
  }
  else{
    res.send({error: "User not authenticated"})
  }
});

/* GET user's watchlist */
router.get('/watchlist', function(req, res, next) {
  if(req.isAuthenticated()){
    res.send(req.user.watchlist)
  }
  else{
    res.send({error: "User not authenticated"})
  }
});

/* Add currency to user's watchlist  */
router.post('/watchlist/add', function(req, res, next) {
  if(req.isAuthenticated()){
    var currency_id = req.body.currency_id
    if(currency_id){
      User.findOne({email: req.user.email}, function(err, user){
        if(!err){
          var currencyAlreadyInWatchlist = user.watchlist.find(c=> c == currency_id)
          if(currencyAlreadyInWatchlist){
            res.send({error: "User already has this currency in watchlist"})
          }
          else{
            user.watchlist.push(currency_id)
            user.save(function(errSave, u){
              if(!errSave){
                res.send({success: "Currency added to watchlist successfully"})
              }
              else{
                res.send({error: "Error occurred in communication with database (save user)"})
              }
            })
          }
        }
        else{
          res.send({error: "Error occurred in communication with database (get user)"})
        }
      })
    }
    else{
      res.send({error: "It is required a field named 'currency_id'"})
    }
  }
  else{
    res.send({error: "User not authenticated"})
  }
});

/* GET user's watchlist */
router.get('/history', function(req, res, next) {
  if(req.isAuthenticated()){
    res.send(req.user.history)
  }
  else{
    res.send({error: "User not authenticated"})
  }
});

module.exports = router;
