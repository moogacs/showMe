let express = require("express")
app = express()
port = process.env.PORT || 3000;

mongoose = require('mongoose'),
Task = require('./api/models/showMeModel'), //created model loading here
bodyParser = require('body-parser');

// mongoose instance connection url connection
mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/showMeDB'); 

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
});


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


var routes = require('./api/routes/showMeRoutes'); //importing route
routes(app); //register the route

app.listen(port)
console.log("showMe listening on "+ port)