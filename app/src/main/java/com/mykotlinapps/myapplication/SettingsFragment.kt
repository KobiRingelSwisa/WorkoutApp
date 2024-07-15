package com.mykotlinapps.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mykotlinapps.myapplication.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }
}
