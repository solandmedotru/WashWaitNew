package ru.solandme.washwait.ui.settings

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.*
import ru.solandme.washwait.R
import ru.solandme.washwait.ui.ChooseCityActivity


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    companion object {
        const val REQUEST_CODE = 11
        const val KEY_CITY_NAME = "cityName"
    }

    private lateinit var cityPref: Preference
    private lateinit var preferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_city_key)))
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)))
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_limit_key)))
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_forecast_providers_key)));

        cityPref = findPreference(resources.getString(R.string.pref_city_key))
        cityPref.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference != null) {
            if (preference.key == resources.getString(R.string.pref_city_key)) {
                val intent = Intent(context, ChooseCityActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val cityName = data.getStringExtra(KEY_CITY_NAME)

                val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = preferences.edit()
                editor.putString(resources.getString(R.string.pref_city_key), cityName)
                editor.apply()

                cityPref.summary = cityName
                Log.e("CityFromIntent", cityName)
            }
        }
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        preference.onPreferenceChangeListener = this

        if (preference is ListPreference) {
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getString(preference.key, ""))
        } else if (preference is SwitchPreference) {
            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getBoolean(preference.key, false))
        }
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val stringValue = newValue.toString()

        if(preference != null){
            if (preference is ListPreference) {
                val prefIndex = preference.findIndexOfValue(stringValue)
                if (prefIndex >= 0) {
                    preference.setSummary(preference.entries[prefIndex])
                }
            } else if (preference is SwitchPreference) {
                val prefValue = preference.isEnabled
                preference.setEnabled(prefValue)
            } else {
                preference.summary = stringValue
            }
        }
        return true
    }
}
