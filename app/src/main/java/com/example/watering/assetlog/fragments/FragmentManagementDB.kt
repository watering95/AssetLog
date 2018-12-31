package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watering.assetlog.model.GoogleDrive
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementDB

class FragmentManagementDB : Fragment() {
    private lateinit var mView: View
    private lateinit var mMainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_management_db, container, false)
        initLayout()
        return mView
    }
    private fun initLayout() {
        mMainActivity = activity as MainActivity
        mMainActivity.supportActionBar?.setTitle(R.string.title_management_db)

        setHasOptionsMenu(false)

        mView.findViewById<RecyclerView>(R.id.recyclerview_fragment_management_db).run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mView.context)
            adapter = RecyclerViewAdapterManagementDB(arrayListOf("파일백업","파일복원", "파일삭제")) { position -> itemClicked(position) }
        }
    }
    private fun itemClicked(position: Int) {
        when(position) {
            0 -> {
                mMainActivity.mViewModel.close()
                mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_UP)
            }
            1 -> { mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_DOWN) }
            2 -> { mMainActivity.mGoogleDrive.deleteDBFile() }
        }
    }
}