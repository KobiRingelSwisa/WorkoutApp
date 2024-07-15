package com.mykotlinapps.myapplication.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mykotlinapps.myapplication.base.BaseFragment
import com.mykotlinapps.myapplication.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(inflater, container, false)
    }
}
