package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementCategorySub
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementCategorySub : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_category_sub, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity

        setHasOptionsMenu(false)

        mViewModel = activity.mViewModel

        mViewModel.allCategorySubs.observe(this, Observer { categorySubs -> categorySubs?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_category_sub).run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterManagementCategorySub(it) { position -> itemClicked(position) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_category_sub)
        floating.setOnClickListener {  }
    }
    private fun itemClicked(position: Int) {

    }
}