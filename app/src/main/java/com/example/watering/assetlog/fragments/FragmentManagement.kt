package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.watering.assetlog.R

class FragmentManagement : Fragment() {
    private lateinit var mView: View
    lateinit var mChildFragmentManager: FragmentManager
    private lateinit var mTransaction: FragmentTransaction
    private val mFragmentManagementMain = FragmentManagementMain()
    val mFragmentManagementGroup = FragmentManagementGroup()
    val mFragmentManagementAccount = FragmentManagementAccount()
    val mFragmentManagementCategoryMain = FragmentManagementCategoryMain()
    val mFragmentManagementCategorySub = FragmentManagementCategorySub()
    val mFragmentManagementDB = FragmentManagementDB()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management, container, false)
        mChildFragmentManager = childFragmentManager
        initLayout()
        return mView
    }
    private fun initLayout() {
        mTransaction = mChildFragmentManager.beginTransaction()
        mTransaction.replace(R.id.frame_management, mFragmentManagementMain).commit()
    }
}