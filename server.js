let express = require("express")
app = express()
port = process.env.PORT || 3000;

// GET method route
app.get('/', function (req, res) {
    res.send('GET request to the homepage')
})

  // POST method route
app.post('/register', function (req, res) {
    console.log(req)
    res.send('Trying to add user')
})

app.listen(port)
console.log("showMe listening on "+ port)