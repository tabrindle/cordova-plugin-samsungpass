
cordova plugin add https://github.com/tabrindle/cordova-plugin-samsungpass.git

OR 
private void startIdentifyWithDialog(JSONArray args, CallbackContext callbackContext) {

        final CallbackContext callbackContextFinal = callbackContext; 
        
        SpassFingerprint.IdentifyListener listener = new SpassFingerprint.IdentifyListener() {
            @Override
            public void onFinished(int eventStatus) {
                Log.d(TAG, "identify finished : reason=" + eventStatus);

                if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                     Log.d(TAG, "onFinished() : Identify authentification Success");
                     callbackContextFinal.success();
                } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
                     callbackContextFinal.success();
                } else {
                     Log.d(TAG, "onFinished() : Authentification Fail for identify");
                     callbackContextFinal.error("Error");
                }
            }
            @Override
            public void onReady() {}
            @Override
            public void onStarted() {}
        };

        mSpassFingerprint.setDialogTitle("Authentificate yourself!", 0xff0000);

        mSpassFingerprint.startIdentifyWithDialog(this.cordova.getActivity().getApplicationContext(), listener, false);
    }

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

