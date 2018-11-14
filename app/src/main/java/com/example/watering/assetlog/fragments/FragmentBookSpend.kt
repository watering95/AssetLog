package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Spend
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentBookSpend : Fragment() {
    private lateinit var mView: View
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_book_spend, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {

        setHasOptionsMenu(false)

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_book_spend)
        floating.setOnClickListener { replaceFragement(FragmentEditSpend().initInstance(Spend())) }
    }
    private fun replaceFragement(fragment:Fragment) {
        mFragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}