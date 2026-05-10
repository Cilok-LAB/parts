package org.lineageos.settings.charge;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.utils.FileUtils;

public class ChargeUtils {

    private static final String TAG = "ChargeUtils";
    public static final String BYPASS_CHARGE_NODE = "/sys/class/power_supply/battery/charging_enabled";
    private static final String PREF_BYPASS_CHARGE = "bypass_charge";

    private SharedPreferences mSharedPrefs;

    public ChargeUtils(Context context) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isBypassChargeEnabled() {
        try {
            String value = FileUtils.readOneLine(BYPASS_CHARGE_NODE);
            return value != null && value.trim().equals("0");
        } catch (Exception e) {
            Log.e(TAG, "Failed to read bypass charge status", e);
            return false;
        }
    }

    public void enableBypassCharge(boolean enable) {
        try {
            FileUtils.writeLine(BYPASS_CHARGE_NODE, enable ? "0" : "1");
            mSharedPrefs.edit().putBoolean(PREF_BYPASS_CHARGE, enable).apply();
        } catch (Exception e) {
            Log.e(TAG, "Failed to write bypass charge status", e);
        }
    }

    public boolean isBypassChargeSupported() {
        try {
            FileUtils.readOneLine(BYPASS_CHARGE_NODE);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Node " + BYPASS_CHARGE_NODE + " not accessible", e);
            return false;
        }
    }
}
