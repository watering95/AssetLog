package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Card
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementCard : Fragment() {
    private lateinit var mView: View
    private lateinit var gestureDetector: GestureDetector
    lateinit var lists: List<Card>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_card, container, false)
        gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        })
        initLayout()
        return mView
    }
    private fun initLayout() {
        val viewManager = LinearLayoutManager(mView.context)
        val viewAdapter = RecyclerViewAdapterManagementCard(lists)
        mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_card).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_card)
        floating.setOnClickListener {  }
    }
}