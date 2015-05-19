var SamsungPass = function() {};

SamsungPass.prototype.GenericSuccessCallback = function(response) {
    console.log('Success');
    console.log(response);
};

SamsungPass.prototype.GenericFailureCallback = function(response) {
    console.log('Failure');
    console.log(response);
};

var SamsungPass = new SamsungPass();
module.exports = SamsungPass;
