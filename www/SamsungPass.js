var SamsungPass = function() {};

SamsungPass.prototype.GenericSuccessCallback = function(response) {
    console.log('Success');
    console.log(response);
};

SamsungPass.prototype.GenericFailureCallback = function(response) {
    console.log('Failure');
    console.log(response);
};

SamsungPass.prototype.checkSamsungPassSupport = function(SuccessCallback, FailureCallback) {
    console.log('checkSamsungPassSupport');

    if (SuccessCallback && FailureCallback) {
        cordova.exec(SuccessCallback, FailureCallback, 'SamsungPassPlugin', 'checkSamsungPassSupport', []);
    } else {
        cordova.exec(SamsungPass.GenericSuccessCallback, SamsungPass.GenericFailureCallback, 'SamsungPassPlugin', 'checkSamsungPassSupport', []);
    }
};

var SamsungPass = new SamsungPass();
module.exports = SamsungPass;
