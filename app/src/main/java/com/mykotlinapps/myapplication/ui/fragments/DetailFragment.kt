package com.mykotlinapps.myapplication.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mykotlinapps.myapplication.base.BaseFragment
import com.mykotlinapps.myapplication.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }
}
