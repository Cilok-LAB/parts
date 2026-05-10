package org.lineageos.settings.charge;

import android.os.Bundle;
import android.widget.Switch;

import androidx.preference.PreferenceFragment;

import com.android.settingslib.widget.MainSwitchPreference;
import com.android.settingslib.widget.OnMainSwitchChangeListener;

import org.lineageos.settings.R;

public class ChargeSettingsFragment extends PreferenceFragment
        implements OnMainSwitchChangeListener {

    private static final String KEY_BYPASS_CHARGE = "bypass_charge";
    private MainSwitchPreference mBypassChargePreference;
    private ChargeUtils mChargeUtils;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.charge_settings);

        mChargeUtils = new ChargeUtils(getActivity());
        mBypassChargePreference = findPreference(KEY_BYPASS_CHARGE);

        if (mBypassChargePreference != null) {
            if (mChargeUtils.isBypassChargeSupported()) {
                mBypassChargePreference.setChecked(mChargeUtils.isBypassChargeEnabled());
                mBypassChargePreference.addOnSwitchChangeListener(this);
            } else {
                mBypassChargePreference.setEnabled(false);
                mBypassChargePreference.setSummary(R.string.charge_bypass_unavailable);
            }
        }
    }

    @Override
    public void onSwitchChanged(Switch switchView, boolean isChecked) {
        mBypassChargePreference.setChecked(isChecked);
        mChargeUtils.enableBypassCharge(isChecked);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBypassChargePreference != null && mChargeUtils.isBypassChargeSupported()) {
            mBypassChargePreference.setChecked(mChargeUtils.isBypassChargeEnabled());
        }
    }
}
