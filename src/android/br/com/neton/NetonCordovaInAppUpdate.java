package br.com.neton;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.content.Context;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import android.app.Activity;
import android.content.IntentSender;


import android.util.Log;

import java.util.Date;

public class NetonCordovaInAppUpdate extends CordovaPlugin {
    private static final String TAG = "NetonCordovaInAppUpdate";
    private static final int MY_REQUEST_CODE = 1337;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "Initializing NetonCordovaInAppUpdate");
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (action.equals("updateImmediate")) {
                updateImmediate(callbackContext);
            } else {
                callbackContext.error("Sorry, updateFlexible not implemented yet.");
            }
        } else {
            callbackContext.error("Android SDK less than 5.0");
        }

        return true;
    }

    private void updateImmediate(CallbackContext callbackContext) {
        Log.d(TAG, "updateImmediate");
        Context context = this.cordova.getActivity().getApplicationContext();
        Activity that = this.cordova.getActivity();

        // Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d(TAG, "appUpdateInfo");
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.

                try {
                    Log.d(TAG, "appUpdateManager");
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            that,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    Log.d(TAG, "Catch");
                    e.printStackTrace();
                }
            } else {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE) {
                    Log.d(TAG, "UPDATE NOT AVAILABILITY");
                }

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UNKNOWN) {
                    Log.d(TAG, "UNKNOWN");
                }

                callbackContext.success("false");
                Log.d(TAG, "");
            }
        });
    }

}
