package com.example.watering.assetlog.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.watering.assetlog.fragments.FragmentAccountsLog

class PagerAdapterAccounts(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragmentAccountsLog = FragmentAccountsLog()

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> { fragmentAccountsLog }
            else -> { fragmentAccountsLog }
        }
    }

    override fun getCount(): Int {
        return 1
    }
}