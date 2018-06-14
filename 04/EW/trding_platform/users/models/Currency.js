var mongoose = require('mongoose');

var currencySchema = mongoose.Schema({
	id: String,
	name: String,
	rank: String,
	price: String,
	marketCap: String,
	percentChange24h: String
});

module.exports = currencySchema;

