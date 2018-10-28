package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R

class FragmentManagementMain : Fragment() {
    private lateinit var mView: View
    private lateinit var mFragmentManagement: FragmentManagement
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mTransaction: FragmentTransaction

    val lists = arrayListOf("group","account","category","card","DB")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_main, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        mFragmentManagement = parentFragment as FragmentManagement
        mFragmentManager = mFragmentManagement.mChildFragmentManager
        val viewManager = LinearLayoutManager(mView.context)
        val viewAdapter = RecyclerViewAdapterManagementMain(lists) { position : Int -> itemClicked(position) }
        mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_main).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    private fun itemClicked(position: Int) {
        mTransaction = mFragmentManager.beginTransaction()
        when(position) {
            0 -> { mTransaction.replace(R.id.frame_management, mFragmentManagement.mFragmentManagementMain).commit() }
            1 -> {}
            2 -> {}
            3 -> {}
            4 -> { mTransaction.replace(R.id.frame_management, mFragmentManagement.mFragmentManagementDB).commit() }
        }
    }
}