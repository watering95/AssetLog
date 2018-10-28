package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.R
import com.example.watering.assetlog.entities.Account
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentManagementAccount : Fragment() {
    private lateinit var mView: View
    lateinit var gestureDetector: GestureDetector
    lateinit var lists: List<Account>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_account, container, false)
        gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        })
        initLayout()
        return mView
    }
    private fun initLayout() {
        val viewManager = LinearLayoutManager(mView.context)
        val viewAdapter = RecyclerViewAdapterManagementAccount(lists)
        val recyclerView = mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_account).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                rv.findChildViewUnder(e.x, e.y)?.let {
                    if(gestureDetector.onTouchEvent(e)) {

                    }
                }
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        val floating = mView.findViewById<FloatingActionButton>(R.id.floating_fragment_management_account)
        floating.setOnClickListener {  }
    }
}