var createError = require('http-errors');
var express = require('express');
var path = require('path');
var mongoose = require('mongoose')
var User = require('./models/User')
var session = require('express-session');
// Autenticação FB 
var passport = require('passport'), 
	FacebookStrategy = require('passport-facebook').Strategy;

passport.serializeUser(function(user, done) {
  done(null, user._id);
});

passport.deserializeUser(function(id, done) {
  User.findById(id, function(err, user) {
    done(err, user);
  });
});

//Conexão à BD
mongoose.connect('mongodb://trding:trding2018@ds141720.mlab.com:41720/trding_users')
mongoose.Promise = global.Promise


passport.use(new FacebookStrategy({
    clientID: "141112822652545",
    clientSecret: "c77219de66fc4713f659be19a1e5a6c4",
    callbackURL: "http://206.189.27.195:80/auth/facebook/callback",
    profileFields:['id','displayName','emails']
  }, function(accessToken, refreshToken, profile, done) {
      var me = new User({
        email: profile.emails[0].value,
        name: profile.displayName
      });
  
      /* save if new */
      User.findOne({email:me.email}, function(err, u) {
        if(!u) {
          me.portfolio = []
          me.watchlist = []
          me.history = []
          me.balance = 0
          me.save(function(err, me) {
            if(err) return done(err);
            done(null,me);
          });
        } else {
          done(null, u);
        }
      });
    }
  ));


var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var testsRouter = require('./routes/tests');

var app = express();
app.use(session({
  secret: "trding2018",
  resave: true,
  saveUninitialized: true }));
app.use(passport.initialize());
app.use(passport.session());

app.get('/auth/facebook', passport.authenticate('facebook', {scope:"email"}));
app.get('/auth/facebook/callback', passport.authenticate('facebook', { successRedirect: '/login/success', failureRedirect: '/login/failed' }));


// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/tests', testsRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.send({error: 'error'});
});

module.exports = app;
