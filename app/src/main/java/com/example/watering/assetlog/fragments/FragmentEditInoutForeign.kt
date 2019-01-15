package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditInoutForeignBinding
import com.example.watering.assetlog.viewmodel.ViewModelEditInoutForeign

class FragmentEditInoutForeign : Fragment() {
    private lateinit var binding: FragmentEditInoutForeignBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private var id_account:Int? = 0
    private var date:String? = ""
    private var currency:Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_inout_foreign, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditInoutForeign::class.java)

        initLayout()

        return binding.root
    }

    fun initInstance(id_account:Int?, date:String?):FragmentEditInoutForeign {
        this.id_account = id_account
        this.date = date
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_inout_foreign)

        binding.viewmodel?.run {
            date = this@FragmentEditInoutForeign.date

            modifyIOForeign(id_account, date, currency).observe(this@FragmentEditInoutForeign,  Observer { io -> io?.let {
                deposit = io.input
                withdraw = io.output
                deposit_krw = io.input_krw
                withdraw_krw = io.output_krw
                indexOfCurrency = io.currency
                evaluation_krw = io.evaluation_krw
                this.io = io
                modifyDairyForeign(id_account, date, currency).observe(this@FragmentEditInoutForeign, Observer { dairy -> dairy?.let {
                    principal = dairy.principal
                } })
            } })
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

    private fun save() {
        binding.viewmodel?.run {
            if(io.id == null) insert(io) else update(io)

            modifyDairyForeign(id_account, date, currency).observeOnce(Observer { dairy -> dairy?.let {
                if(dairy.id == null) insert(dairy) else update(dairy)
                mFragmentManager.popBackStack()
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