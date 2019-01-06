package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentAccountsBinding
import com.example.watering.assetlog.view.RecyclerViewAdapterAccounts
import com.example.watering.assetlog.viewmodel.ViewModelAccounts

class FragmentAccounts : Fragment() {
    private lateinit var binding: FragmentAccountsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_accounts, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelAccounts::class.java)
        initLayout()
        return binding.root
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

        binding.viewmodel?.run {
            listOfAccount = Transformations.map(allAccounts) { list -> list.map { it.number } } as MutableLiveData<List<String?>>

            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.indexOfAccount -> onIndexOfAccountChanged()
                    }
                }
            })
        }

        binding.recyclerviewFragmentAccounts.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FragmentAccounts.context)
        }
    }
    private fun itemClicked(position: Int) {

    }
    fun onIndexOfAccountChanged() {
        binding.viewmodel?.run {
            Transformations.switchMap(listOfAccount) { list ->
                Transformations.map(getAccountByNumber(list[indexOfAccount])) { account -> account.id }
            }.observe(this@FragmentAccounts, Observer { id -> id?.let {
                id_account = id
                getLogs(id_account).observe(this@FragmentAccounts, Observer { logs -> logs?.let {
                    binding.recyclerviewFragmentAccounts.run {
                        adapter = RecyclerViewAdapterAccounts(logs) { position -> itemClicked(position) }
                    }
                } })
            } })
        }
    }
}