package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementMain
import com.example.watering.assetlog.viewmodel.ViewModelApp

class FragmentManagement : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: ViewModelApp
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    private val mFragmentManagementGroup = FragmentManagementGroup()
    private val mFragmentManagementAccount = FragmentManagementAccount()
    private val mFragmentManagementCategoryMain = FragmentManagementCategoryMain()
    private val mFragmentManagementCategorySub = FragmentManagementCategorySub()
    private val mFragmentManagementCard = FragmentManagementCard()
    private val mFragmentManagementDB = FragmentManagementDB()

    val lists = arrayListOf("Group","Account","CategoryMain","CategorySub","Card","DB")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

        mViewModel = (activity as MainActivity).mViewModel

        mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_main).run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mView.context)
            adapter = RecyclerViewAdapterManagementMain(lists) { position -> itemClicked(position) }
        }
    }
    private fun itemClicked(position: Int) {
        mViewModel.run {
            when (position) {
                0 -> replaceFragement(mFragmentManager, mFragmentManagementGroup)
                1 -> replaceFragement(mFragmentManager, mFragmentManagementAccount)
                2 -> replaceFragement(mFragmentManager, mFragmentManagementCategoryMain)
                3 -> replaceFragement(mFragmentManager, mFragmentManagementCategorySub)
                4 -> replaceFragement(mFragmentManager, mFragmentManagementCard)
                5 -> replaceFragement(mFragmentManager, mFragmentManagementDB)
                else -> {  }
            }
        }
    }
}