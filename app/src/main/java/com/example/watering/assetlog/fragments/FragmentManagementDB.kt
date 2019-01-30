package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watering.assetlog.model.GoogleDrive
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentManagementDbBinding
import com.example.watering.assetlog.view.RecyclerViewAdapterFileManagementDB
import com.example.watering.assetlog.view.RecyclerViewAdapterManagementDB
import com.example.watering.assetlog.viewmodel.ViewModelManagementDB

class FragmentManagementDB : Fragment() {
    private lateinit var binding: FragmentManagementDbBinding
    private lateinit var mMainActivity: MainActivity
    private val dbFileName = "AssetLog.db"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_management_db, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelManagementDB::class.java)
        initLayout()
        return binding.root
    }
    private fun initLayout() {
        mMainActivity = activity as MainActivity
        mMainActivity.supportActionBar?.setTitle(R.string.title_management_db)

        setHasOptionsMenu(false)

        binding.viewmodel?.run {
            binding.recyclerviewFragmentManagementDb.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@FragmentManagementDB.context)
                adapter = RecyclerViewAdapterManagementDB(arrayListOf("파일백업","파일복원", "파일삭제")) { position -> itemClicked(position) }
            }
            binding.recyclerviewFileFragmentManagementDb.run {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@FragmentManagementDB.context)
            }

            getList()
        }
    }

    private fun itemClicked(position: Int) {
        when(position) {
            0 -> {
                mMainActivity.mViewModel.close()
                mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_UP)
            }
            1 -> {
                mMainActivity.mGoogleDrive.signIn(GoogleDrive.REQUEST_CODE_SIGN_IN_DOWN)
                getList()
            }
            2 -> {
                mMainActivity.mGoogleDrive.deleteDBFile()
                getList()
            }
        }
    }

    fun getList() {
        binding.viewmodel?.run {
            listOfFile = mMainActivity.getDatabasePath(dbFileName).parentFile.list().filterNotNull()
            binding.recyclerviewFileFragmentManagementDb.run {
                adapter = RecyclerViewAdapterFileManagementDB(listOfFile)
            }
        }
    }
}