var mongoose = require('mongoose');
var Action = require('./Action')
var PastAction = require('./PastAction')
var Currency = require('./Currency')

var userSchema = mongoose.Schema({
	email:String,
	name:String,
	watchlist: [String],
	portfolio: [Action],
	history: [PastAction],
	balance: Number
});

var User = mongoose.model('User', userSchema);
module.exports = User;


