var mongoose = require('mongoose');
var Action = require('./Action')
var PastAction = require('./PastAction')
var Currency = require('./Currency')

var userSchema = mongoose.Schema({
	id:String,
	email:String,
	name:String,
	watchlist: [String],
	portfolio: [Action],
	history: [PastAction]
});

var User = mongoose.model('User', userSchema);
module.exports = User;


