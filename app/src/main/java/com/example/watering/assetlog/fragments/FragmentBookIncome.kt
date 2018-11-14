package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Income
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentBookIncome : Fragment() {
    private lateinit var mView: View
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_book_income, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {

        setHasOptionsMenu(false)

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_book_income)
        floating.setOnClickListener { replaceFragement(FragmentEditIncome().initInstance(Income())) }
    }
    private fun replaceFragement(fragment:Fragment) {
        mFragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}