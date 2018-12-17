package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Account
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementAccount
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementAccount : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: ViewModelApp
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_account, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        activity.supportActionBar?.setTitle(R.string.title_management_account)

        mViewModel = activity.mViewModel

        setHasOptionsMenu(false)

        mViewModel.allAccounts.observe(this, Observer { accounts -> accounts?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_account).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterManagementAccount(it) { position -> itemClicked(it[position]) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_account)
        floating.setOnClickListener { mViewModel.replaceFragment(mFragmentManager, FragmentEditAccount().initInstance(Account())) }
    }
    private fun itemClicked(item: Account) {
        mViewModel.replaceFragment(mFragmentManager, FragmentEditAccount().initInstance(item))
    }
}