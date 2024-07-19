package com.mykotlinapps.bodybuilder

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.mykotlinapps.bodybuilder.R

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        updatePreferences()
    }

    private fun updatePreferences() {
        val weight = sharedPreferences.getString("weight", "Not set")
        val bodyFat = sharedPreferences.getString("bodyFat", "Not set")
        val waistSize = sharedPreferences.getString("waistSize", "Not set")

        findPreference<Preference>("weight")?.summary = weight
        findPreference<Preference>("bodyFat")?.summary = bodyFat
        findPreference<Preference>("waistSize")?.summary = waistSize
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "weight" || key == "bodyFat" || key == "waistSize") {
            updatePreferences()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}
