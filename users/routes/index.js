var express = require('express');
var router = express.Router();
var User = require('../models/User');
var request = require('request')

/* route to return login success */
router.get('/login/success', function(req, res, next) {
  res.send({success: "User logged in successfully", user: req.user});
});
/* route to return login insuccess */
router.get('/login/failed', function(req, res, next) {
  res.send({error: "Facebook authentication failed"});
});

/* Add funds to user's balance */
router.post('/balance/add', function(req, res, next){
  if(req.isAuthenticated()){
    var amount = req.body.amount
    if(amount && amount!=""){
      User.findOne({email: req.user.email}, function(err, user){
        if(!err){
          user.balance += parseInt(amount)
          user.save(function(errSave,u){
            if(!errSave){
              res.send({success: true})
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
      res.send({error: "Fields required: amount (must be not empty)"})
    }
  }
  else{
    res.send({error: "User not authenticated"})
  }
})

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
          if(user.balance >= invested){
            user.balance -= invested
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
          }else{
            res.send({error: "Not enough funds"})
          }
          
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

/* Close action from user's portfolio */
router.get('/portfolio/close/:action_id', function(req, res, next) {
  if(req.isAuthenticated()){
    var action_id = req.params.action_id
    
    if(action_id){
      User.findOne({email: req.user.email}, function(err, user){
        if(!err){
          console.log(action_id)
          console.log(user.portfolio)
          var action = user.portfolio.find(x => String(x._id) == String(action_id))
          if(action){
            var currency_id = action.currency_id
            request({
              timeout: 3000,
              url: "http://167.99.193.161/cryptocurrency/"+currency_id,
              json: true
            }, function (error, response, body) {
              if (!error && response.statusCode === 200) {
                var current_value = body.price
                var open_value = action.open_value
                var invested = action.invested
                var units = invested / open_value
                var method = action.method
                
                var difference, profit_loss
                if(method=="buy"){
                  difference = current_value*units - open_value*units;
                  profit_loss = open_value*units - difference;
                }
                else if(method=="sell"){
                  difference = current_value*units - open_value*units;
                  profit_loss = open_value*units + difference;
                }
                else{
                  return res.send({error: "Database inconsistency: Action's method isn't 'buy' or 'sell'"})
                }

                var history_entry = {
                  currency_id: currency_id,
                  open_date: action.open_date,
                  open_value: open_value,
                  invested: invested,
                  units: units,
                  close_date: new Date(),
                  close_value: current_value,
                  profit_loss: profit_loss
                }

                user.portfolio = user.portfolio.filter(x => String(x._id) != String(action_id))
                user.history.push(history_entry)
                user.balance += profit_loss
                user.save(function(errSave, u){
                  if(!errSave){
                    res.send({success: "Action closed successfully"})
                  }
                  else{
                    res.send({error: "Error occurred in communication with database (save user)"})
                  }
                })
              }
              else{
                res.send({error: "Error retrieving data from cryptocurrencies service"})
              }
            })
          }
          else{
            res.send({error: "There's no action in this user's portfolio with the provided ID"})
          }
          
        }
        else{
          res.send({error: "Error occurred in communication with database (get user)"})
        }
      })
    }
    else{
      res.send({error: "Fields required: action_id"})
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


/* GET user's history */
router.get('/history', function(req, res, next) {
  if(req.isAuthenticated()){
    res.send(req.user.history)
  }
  else{
    res.send({error: "User not authenticated"})
  }
});



module.exports = router;
