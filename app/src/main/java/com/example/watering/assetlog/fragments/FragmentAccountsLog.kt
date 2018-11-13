package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.watering.assetlog.R

class FragmentAccountsLog : Fragment() {
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_accounts_log, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {

        setHasOptionsMenu(false)
    }
}