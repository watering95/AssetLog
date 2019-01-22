package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentHomeBinding
import com.example.watering.assetlog.entities.Home
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.view.RecyclerViewAdapterHome
import com.example.watering.assetlog.viewmodel.ViewModelHome

class FragmentHome : Fragment() {
    private val mViewModel by lazy { (activity as MainActivity).mViewModel }
    private lateinit var binding: FragmentHomeBinding
    private val mFragmentManager by lazy { (activity as MainActivity).supportFragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_home, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelHome::class.java)
        initLayout()
        return binding.root
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

        binding.viewmodel?.run {
            listOfGroup = Transformations.map(allGroups) { list -> listOf("전체") + list.map { it.name } }

            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.indexOfGroup -> onIndexOfGroupChanged()
                    }
                }
            })
        }

        binding.recyclerviewFragmentHome.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FragmentHome.context)
        }
    }

    private fun itemClicked(position: Int) {
        binding.viewmodel?.run {
        }
    }

    fun onIndexOfGroupChanged() {
        binding.viewmodel?.run {
            if(indexOfGroup == 0) allAccounts else {
                Transformations.switchMap(listOfGroup) { list ->
                    Transformations.switchMap(getGroupByName(list[indexOfGroup])) { group ->
                        Transformations.map(getAccountsByGroup(group.id)) { accounts -> accounts }
                    }
                }
            }.observe(this@FragmentHome, Observer { accounts -> accounts?.let {
                val list = accounts.map {
                    val home = Home()
                    modifyDairyTotal(it.id, ModelCalendar.getToday()).observeOnce(Observer { dairy -> dairy?.let {
                        home.evaluation_krw = dairy.evaluation_krw
                        home.principal_krw = dairy.principal_krw
                        home.rate = dairy.rate
                    } })
                    home.account = it.number
                    home
                }
                binding.recyclerviewFragmentHome.run {
                    adapter = RecyclerViewAdapterHome(list) { position -> itemClicked(position) }
                }
            } })
        }
    }

    private fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
        observeForever(object: Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}