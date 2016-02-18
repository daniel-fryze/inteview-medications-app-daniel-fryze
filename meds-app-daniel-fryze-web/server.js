// GENERAL SET UP

var express        = require('express');
var app            = express();
var morgan         = require('morgan');
var bodyParser     = require('body-parser');
var methodOverride = require('method-override');

// CONFIGURATION

app.use(express.static(__dirname + '/app'));                    
app.use(morgan('dev'));
app.use(bodyParser.urlencoded({'extended':'true'}));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' }));
app.use(methodOverride());

// SERVER LISTENING START

app.listen(process.env.PORT || 5000);