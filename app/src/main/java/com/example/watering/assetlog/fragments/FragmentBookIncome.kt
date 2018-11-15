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
import com.example.watering.assetlog.entities.Income
import com.example.watering.assetlog.view.RecyclerViewAdapterBookIncome
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentBookIncome : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: ViewModelApp
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_book_income, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        activity.supportActionBar?.setTitle(R.string.income)

        mViewModel = activity.mViewModel
        val mModel = activity.mModel

        setHasOptionsMenu(false)

        mViewModel.getIncomes(mModel.getToday()).observe(this, Observer { incomes -> incomes?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_book_income).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterBookIncome(it) { position -> itemClicked(it[position]) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_book_income)
        floating.setOnClickListener { mViewModel.replaceFragement(mFragmentManager, FragmentEditIncome().initInstance(Income())) }
    }
    private fun itemClicked(item: Income) {
        mViewModel.replaceFragement(mFragmentManager, FragmentEditIncome().initInstance(item))
    }
}