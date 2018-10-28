package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.watering.assetlog.R

class FragmentBook : Fragment() {
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_book, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val viewPager = mView.findViewById(R.id.viewpager_fragment_book) as ViewPager
        viewPager.adapter = PagerAdapterBook(childFragmentManager)
    }
}