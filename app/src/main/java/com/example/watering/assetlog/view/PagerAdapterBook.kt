package com.example.watering.assetlog.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.watering.assetlog.fragments.FragmentBookSpend

class PagerAdapterBook(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragmentBookSpend = FragmentBookSpend()

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> { fragmentBookSpend }
            else -> { fragmentBookSpend }
        }
    }

    override fun getCount(): Int {
        return 1
    }
}