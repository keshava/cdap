/**
 * @fileoverview catch-all middleware for 404 routes.
 */

module.exports = {
  render404: function(req, res) {
    // rendering cdap page which will render 404 UI
    res.render('cdap', {nonceVal: `${res.locals.nonce}`});
  }
}
