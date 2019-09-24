'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var UserSchema = new Schema({
    key: {
        type: String,
    },
    created: {
        type: Number,
        default: Date.now
    },
    type: {
        type: String,
    },
});

module.exports = mongoose.model('Users', UserSchema);