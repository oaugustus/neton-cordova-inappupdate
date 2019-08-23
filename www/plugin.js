
var exec = require('cordova/exec');

var PLUGIN_NAME = 'NetonCordovaInAppUpdate';

var InAppUpdate = {
  start: function(phrase, cb) {
    exec(cb, null, PLUGIN_NAME, 'updateImmediate', []);
  },
  stop: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'updateFlexible', []);
  }
};

module.exports = InAppUpdate;
