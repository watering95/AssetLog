package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.GoogleDrive
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R

class FragmentManagementDB : Fragment() {
    private lateinit var mView: View
    private lateinit var mMainActivity: MainActivity
    val lists = arrayListOf("파일백업","파일복원", "파일삭제")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_db, container, false)
        mMainActivity = activity as MainActivity
        initLayout()
        return mView
    }
    private fun initLayout() {
        val viewManager = LinearLayoutManager(mView.context)
        val viewAdapter = RecyclerViewAdapterManagementDB(lists) { position: Int -> itemClicked(position) }
        mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_db).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    private fun itemClicked(position: Int) {
        when(position) {
            0 -> { mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_UP) }
            1 -> { mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_DOWN) }
            2 -> { mMainActivity.mGoogleDrive.deleteDBFile() }
        }
    }
}