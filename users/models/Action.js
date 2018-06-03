var mongoose = require('mongoose');

var actionSchema = mongoose.Schema({
	currency_id: String,
	open_date: Date,
	open_value: Number,
	invested: Number,
	method: String
});

module.exports = actionSchema;

