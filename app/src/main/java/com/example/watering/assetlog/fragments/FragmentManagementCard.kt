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
import com.example.watering.assetlog.entities.Card
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementCard
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementCard : Fragment() {
    private lateinit var mView: View
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_card, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        val viewModel = activity.mViewModel

        activity.supportActionBar?.setTitle(R.string.title_management_card)

        setHasOptionsMenu(false)

        viewModel.allCards.observe(this, Observer { cards -> cards?.let {
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
        mFragmentManager.run {
            val transaction = beginTransaction()
            transaction.replace(R.id.frame_main, fragment).addToBackStack(null).commit()
        }
    }
}