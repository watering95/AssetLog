package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.CategoryMain
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementCategoryMain
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementCategoryMain : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: AppViewModel
    private var mFragmentManager: FragmentManager? = null
    private lateinit var mTransaction: FragmentTransaction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_category_main, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        mFragmentManager = fragmentManager

        setHasOptionsMenu(false)

        mViewModel = activity.mViewModel
        mViewModel.allCategoryMains.observe(this, Observer { categoryMains -> categoryMains?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_category_main).run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterManagementCategoryMain(it) { position -> itemClicked(it[position]) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_category_main)
        floating.setOnClickListener { replaceFragement(FragmentEditCategoryMain().initInstance(CategoryMain())) }
    }
    private fun itemClicked(item: CategoryMain) {
        replaceFragement(FragmentEditCategoryMain().initInstance(item))
    }
    private fun replaceFragement(fragment:Fragment) {
        mFragmentManager?.let {
            mTransaction = it.beginTransaction()
            mTransaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}