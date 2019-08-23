
var exec = require('cordova/exec');

var PLUGIN_NAME = 'NetonCordovaInAppUpdate';

var InAppUpdate = {
  updateImmediate: function(onSuccess, onFail) {
    exec(onSuccess, onFail, PLUGIN_NAME, 'updateImmediate', []);
  },
  updateFlexible: function(onSuccess, onFail) {
    exec(onSuccess, onFail, PLUGIN_NAME, 'updateFlexible', []);
  }
};

module.exports = InAppUpdate;
