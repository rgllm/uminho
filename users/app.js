var createError = require('http-errors');
var express = require('express');
var path = require('path');
var mongoose = require('mongoose')
var User = require('./models/User')
var session = require('express-session');
var bodyParser = require('body-parser')

//Conexão à BD
mongoose.connect('mongodb://trding:trding2018@ds141720.mlab.com:41720/trding_users')
mongoose.Promise = global.Promise



var indexRouter = require('./routes/index');
var testsRouter = require('./routes/tests');

var app = express();
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Credentials", "true");
  res.header("Access-Control-Allow-Headers", "Origin,Content-Type, Authorization, x-id, Content-Length, X-Requested-With");
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
  next();
});



// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));

app.use(bodyParser.json())

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
