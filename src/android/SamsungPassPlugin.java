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
        } catch (SsdkUnsupportedException e) {
            Log.d(TAG, "Spass could not initialize" + e);
        }

        isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);
        
        if (isFeatureEnabled) {
            mSpassFingerprint = new SpassFingerprint(this.cordova.getActivity().getApplicationContext());
            Log.d(TAG, "mSpassFingerprint was Initialized");
        } else {
            Log.d(TAG, "Fingerprint Service is not supported in the device.");
        }
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        Log.d(TAG, "Plugin Method Called: " + action);

        if (action.equals("checkSamsungPassSupport")) {
            this.checkSamsungPassSupport(args, callbackContext);
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

}
