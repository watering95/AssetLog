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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentHomeBinding
import com.example.watering.assetlog.entities.Home
import com.example.watering.assetlog.view.RecyclerViewAdapterHome
import com.example.watering.assetlog.viewmodel.ViewModelHome

class FragmentHome : Fragment() {
    private val mViewModel by lazy { (activity as MainActivity).mViewModel }
    private lateinit var binding: FragmentHomeBinding
    private val mFragmentManager by lazy { (activity as MainActivity).supportFragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelHome::class.java)
        initLayout()
        return binding.root
    }
    private fun initLayout() {
        setHasOptionsMenu(false)

        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.list -> onChangedList()
                    }
                }
            })
        }
    }

    private fun itemClicked(position: Int) {
        binding.viewmodel?.run {
        }
    }

    private fun onChangedRecyclerView(list: List<Home>) {
        binding.viewmodel?.run {
            binding.recyclerviewFragmentHome.run {
                if(adapter == null) {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@FragmentHome.context)
                    adapter = RecyclerViewAdapterHome(list) { position -> itemClicked(position) }
                }
                else adapter?.notifyDataSetChanged()
            }
        }
    }

    fun onChangedList() {
        binding.viewmodel?.run {
            var sumOfEvaluation = 0
            var sumOfPrincipal = 0

            list.observeOnce(Observer { list -> list?.let {
                var listOfHome: List<Home> = listOf()
                list.map {
                    if(it == list.last()) {
                        it.observeOnce(Observer { home ->
                            listOfHome = listOfHome + home
                            sumOfEvaluation += home.evaluationKRW!!
                            sumOfPrincipal += home.principalKRW!!
                            totalEvaluation = sumOfEvaluation
                            totalPrincipal = sumOfPrincipal
                            listOfHome = listOfHome.map {
                                it.total = totalEvaluation
                                it
                            }
                            onChangedRecyclerView(listOfHome)
                        })
                    } else {
                        it.observeOnce(Observer { home ->
                            listOfHome = listOfHome + home
                            sumOfEvaluation += home.evaluationKRW!!
                            sumOfPrincipal += home.principalKRW!!
                        })
                    }
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