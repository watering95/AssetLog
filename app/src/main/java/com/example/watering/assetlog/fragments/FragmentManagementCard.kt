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
import com.example.watering.assetlog.entities.Card
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementCard
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementCard : Fragment() {
    private lateinit var mView: View
    private lateinit var mViewModel: AppViewModel
    private var mFragmentManager: FragmentManager? = null
    private lateinit var mTransaction: FragmentTransaction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_card, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        activity.supportActionBar?.setTitle(R.string.title_management_card)
        mFragmentManager = fragmentManager

        mViewModel = activity.mViewModel

        setHasOptionsMenu(false)

        mViewModel.allCards.observe(this, Observer { cards -> cards?.let {
            mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_card).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(mView.context)
                adapter = RecyclerViewAdapterManagementCard(it) { position -> itemClicked(it[position]) }
            }
        } })

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_card)
        floating.setOnClickListener { replaceFragement(FragmentEditCard().initInstance(Card())) }
    }
    private fun itemClicked(item: Card) {
        replaceFragement(FragmentEditCard().initInstance(item))
    }
    private fun replaceFragement(fragment:Fragment) {
        mFragmentManager?.let {
            mTransaction = it.beginTransaction()
            mTransaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}