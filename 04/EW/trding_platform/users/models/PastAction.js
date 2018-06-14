var mongoose = require('mongoose');

var pastActionSchema = mongoose.Schema({
	currency_id: String,
	open_date: Date,
	open_value: Number,
	invested: Number,
	close_date: Date,
	close_value: Number,
	profit_loss: Number
});

module.exports = pastActionSchema;

