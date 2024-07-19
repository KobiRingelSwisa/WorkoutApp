package com.mykotlinapps.bodybuilder

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.BodyStats
import com.mykotlinapps.bodybuilder.ui.ItemsViewModel

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel: ItemsViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        updatePreferences()

        viewModel.latestBodyStats.observe(this) { bodyStats ->
            bodyStats?.let {
                findPreference<EditTextPreference>("weight")?.text = it.weight.toString()
                findPreference<Preference>("weight")?.summary = it.weight.toString()
                findPreference<EditTextPreference>("bodyFat")?.text = it.bodyFat.toString()
                findPreference<Preference>("bodyFat")?.summary = it.bodyFat.toString()
                findPreference<EditTextPreference>("waistSize")?.text = it.waistSize.toString()
                findPreference<Preference>("waistSize")?.summary = it.waistSize.toString()
            }
        }
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

    private fun saveBodyStats() {
        val weight = sharedPreferences.getString("weight", "0")?.toFloatOrNull() ?: 0f
        val bodyFat = sharedPreferences.getString("bodyFat", "0")?.toFloatOrNull() ?: 0f
        val waistSize = sharedPreferences.getString("waistSize", "0")?.toFloatOrNull() ?: 0f

        val bodyStats = BodyStats(
            weight = weight,
            bodyFat = bodyFat,
            waistSize = waistSize
        )
        viewModel.insertBodyStats(bodyStats)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


}
