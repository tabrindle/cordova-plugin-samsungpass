
cordova plugin add https://github.com/tabrindle/cordova-plugin-samsungpass.git

OR 

npm install cordova-plugin-samsungpass

## Support
- Android
- Tested on Galaxy S6
- Should work on Galaxy S5

## Setup

  ```cordova create ~/Developer/SamsungPassTest com.test.samsungpass SamsungPassTest```
  
  ```cordova platform add android```
  
  ```cordova plugin add cordova plugin add https://github.com/tabrindle/cordova-plugin-samsungpass.git```

## Methods

SamsungPass.checkSamsungPassSupport();

SamsungPass.checkForRegisteredFingers();

SamsungPass.startIdentifyWithDialog();

## Example

Success and Failure callbacks are defaulted so if you like, you may pass an empty function call like this:

```
SamsungPass.checkSamsungPassSupport();
```

If you require more than just a log, then pass functions like this:

```
SamsungPass.checkForRegisteredFingers(function() {
  console.log('CheckForRegisteredFingersWithSamsungPass Success');
  //do something funny
}, function() {
  console.log('CheckForRegisteredFingersWithSamsungPass Failure');
  //do something sad
});
```

