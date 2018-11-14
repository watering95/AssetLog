package com.example.watering.assetlog.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.watering.assetlog.fragments.FragmentBookIncome
import com.example.watering.assetlog.fragments.FragmentBookSpend

class PagerAdapterBook(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragmentBookSpend = FragmentBookSpend()
    private val fragmentBookIncome = FragmentBookIncome()

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> { fragmentBookSpend }
            1 -> { fragmentBookIncome }
            else -> { fragmentBookSpend }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}