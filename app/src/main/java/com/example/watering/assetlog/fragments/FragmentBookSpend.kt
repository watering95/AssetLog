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
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.view.RecyclerViewAdapterBookSpend
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentBookSpend : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: ViewModelApp
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_book_spend, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        activity.supportActionBar?.setTitle(R.string.spend)

        mViewModel = activity.mViewModel
        val mModel = activity.mModel

        setHasOptionsMenu(false)

        mViewModel.getSpends(mModel.getToday()).observe(this, Observer { spends -> spends?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_book_spend).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterBookSpend(it) { position -> itemClicked(it[position]) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_book_spend)
        floating.setOnClickListener { mViewModel.replaceFragement(mFragmentManager, FragmentEditSpend().initInstance(Spend())) }
    }
    private fun itemClicked(item: Spend) {
        mViewModel.replaceFragement(mFragmentManager, FragmentEditSpend().initInstance(item))
    }
}