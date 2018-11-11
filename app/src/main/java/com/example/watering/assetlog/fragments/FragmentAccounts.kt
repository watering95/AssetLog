package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.watering.assetlog.R
import com.example.watering.assetlog.view.PagerAdapterAccounts

class FragmentAccounts : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_accounts, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

        val viewPager = mView.findViewById<ViewPager>(R.id.viewpager_fragment_accounts)
        viewPager.adapter = PagerAdapterAccounts(childFragmentManager)
    }
}