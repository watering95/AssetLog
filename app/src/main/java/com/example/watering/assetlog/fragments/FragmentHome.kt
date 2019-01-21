package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentHomeBinding
import com.example.watering.assetlog.viewmodel.ViewModelHome

class FragmentHome : Fragment() {
    private val mViewModel by lazy { (activity as MainActivity).mViewModel }
    private lateinit var binding: FragmentHomeBinding
    private val mFragmentManager by lazy { (activity as MainActivity).supportFragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_home, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelHome::class.java)
        initLayout()
        return binding.root
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

    }
}