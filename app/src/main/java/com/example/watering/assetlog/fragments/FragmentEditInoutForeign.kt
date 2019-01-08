package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditInoutForeignBinding
import com.example.watering.assetlog.databinding.FragmentEditInoutKrwBinding
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.entities.SpendCard
import com.example.watering.assetlog.entities.SpendCash
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.viewmodel.ViewModelEditInoutForeign
import com.example.watering.assetlog.viewmodel.ViewModelEditInoutKRW
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend
import java.util.*

class FragmentEditInoutForeign : Fragment() {
    private lateinit var binding: FragmentEditInoutForeignBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private lateinit var spend: Spend

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_inout_foreign, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditInoutForeign::class.java)

        initLayout()

        return binding.root
    }

    fun initInstance(item: Spend):FragmentEditInoutForeign {
        spend = item
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_inout_foreign)

        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {

                    }
                }
            })


        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.menu_edit,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_edit_save -> save()
            R.id.menu_edit_delete -> {}
        }

        return super.onOptionsItemSelected(item)
    }

    fun save() {

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