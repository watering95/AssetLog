package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend

class FragmentEditSpend : Fragment() {
    private lateinit var item: Spend
    private lateinit var binding: FragmentEditSpendBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private val viewmodel by lazy { ViewModelProviders.of(this).get(ViewModelEditSpend::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_spend, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewmodel

        initLayout()

        return binding.root
    }
    fun initInstance(item: Spend):FragmentEditSpend {
        this.item = item
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.spend -> {
                            listOfMain.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                                getCatMainBySub(id_sub).observe(this@FragmentEditSpend, Observer {main -> main?.let {
                                    indexOfMain = list.indexOf(main.name)
                                } })
                            } })
                        }
                        BR.listOfSub -> {
                            listOfSub?.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                                getCatSub(id_sub).observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                                    indexOfSub = list.indexOf(sub.name)
                                } })
                            } })
                        }
                    }
                }
            })
        }

        binding.viewmodel?.run { spend = item }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.menu_edit,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_edit_save -> {

            }
            R.id.menu_edit_delete -> { binding.viewmodel?.delete(this.item) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}