var express = require('express');
var router = express.Router();
var User = require('../models/User');

/* route to return login success */
router.get('/login/success', function(req, res, next) {
  res.send({success: true});
});
/* route to return login insuccess */
router.get('/login/failed', function(req, res, next) {
  res.send({success: false});
});



/* GET user's portfolio */
router.get('/portfolio', function(req, res, next) {
  if(req.isAuthenticated()){
    res.send(req.user.portfolio)
  }
  else{
    res.send({error: "User not authenticated"})
  }
});

/* Add currency to user's portfolio */
router.post('/portfolio/add', function(req, res, next) {
  if(req.isAuthenticated()){
    var currency_id = req.body.currency_id
    var open_date = new Date()
    var open_value = req.body.open_value
    var invested = req.body.invested
    var method = req.body.method
    if(currency_id && open_date && open_value && invested && method){
      User.findOne({email: req.user.email}, function(err, user){
        if(!err){
          
          user.portfolio.push({currency_id: currency_id,
                                open_date: open_date,
                                open_value: open_value,
                                invested: invested,
                                method: method })
          user.save(function(errSave, u){
            if(!errSave){
              res.send({success: "Action added to portfolio successfully", date: open_date})
            }
            else{
              res.send({error: "Error occurred in communication with database (save user)"})
            }
          })
          
        }
        else{
          res.send({error: "Error occurred in communication with database (get user)"})
        }
      })
    }
    else{
      res.send({error: "Fields required: currency_id,open_value,invested,method"})
    }
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
            res.send({error: "User already has the given currency in watchlist"})
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

/* Remove currency from user's watchlist  */
router.post('/watchlist/remove', function(req, res, next) {
  if(req.isAuthenticated()){
    var currency_id = req.body.currency_id
    if(currency_id){
      User.findOne({email: req.user.email}, function(err, user){
        if(!err){
          var currencyIsInWatchlist = user.watchlist.find(c=> c == currency_id)
          if(!currencyIsInWatchlist){
            res.send({error: "User doesn't have the given currency in watchlist"})
          }
          else{
            user.watchlist = user.watchlist.filter(c => c!=currency_id)
            user.save(function(errSave, u){
              if(!errSave){
                res.send({success: "Currency removed from watchlist successfully"})
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
