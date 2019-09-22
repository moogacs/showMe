'use strict';
module.exports = function(app) {
    var showMeList = require('../controllers/showMeController');

  // showMeList Routes
app.route('/users')
    .get(showMeList.list_all_users)
    .post(showMeList.create_a_user);


app.route('/users/:userId')
    .get(showMeList.read_a_user)
    .put(showMeList.update_a_user)
    .delete(showMeList.delete_a_user);
};