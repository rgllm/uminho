var mongoose = require('mongoose');

var pastActionSchema = mongoose.Schema({
	currencyId: String,
	open_date: Date,
	open_value: Number,
	invested: Number,
	close_date: Date,
	close_value: Number
});

module.exports = pastActionSchema;

