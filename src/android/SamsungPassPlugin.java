package com.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;
import com.samsung.android.sdk.pass.SpassInvalidStateException;

public class SamsungPassPlugin extends CordovaPlugin {

    private Spass mSpass;
    private SpassFingerprint mSpassFingerprint;
    private boolean isFeatureEnabled = false;
    private static final String TAG = "SamsungPassPlugin";

    @Override
    public void initialize(final CordovaInterface cordova, CordovaWebView webView) {

        mSpass = new Spass();

        try {
            mSpass.initialize(this.cordova.getActivity().getApplicationContext());
            Log.d(TAG, "Spass was Initialized");

            isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

            if (isFeatureEnabled) {
                mSpassFingerprint = new SpassFingerprint(this.cordova.getActivity().getApplicationContext());
                Log.d(TAG, "mSpassFingerprint was Initialized");
            } else {
                Log.d(TAG, "Fingerprint Service is not supported in the device.");
            }
        } catch (SsdkUnsupportedException e) {
            Log.d(TAG, "Spass could not initialize" + e);
        }

    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        Log.d(TAG, "Plugin Method Called: " + action);

        if (action.equals("checkSamsungPassSupport")) {
            this.checkSamsungPassSupport(args, callbackContext);
        } else if (action.equals("checkForRegisteredFingers")) {
            this.checkForRegisteredFingers(args, callbackContext);
        } else if (action.equals("startIdentifyWithDialog")) {
            this.startIdentifyWithDialog(args, callbackContext);
        }
        else {
            return false;
        }

        return true;
    }

    private void checkSamsungPassSupport(JSONArray args, CallbackContext callbackContext) {
        Log.d(TAG, "checkSamsungPassSupport");

        if (isFeatureEnabled) {
            callbackContext.success();
        } else {
            callbackContext.error("Error");
        }
    }

    private void checkForRegisteredFingers(JSONArray args, CallbackContext callbackContext) {
        Log.d(TAG, "checkForRegisteredFingers");

        boolean mHasRegisteredFinger = mSpassFingerprint.hasRegisteredFinger();

        if (mHasRegisteredFinger) {
            callbackContext.success();
        } else {
            callbackContext.error("Error");
        }
    }

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
}
