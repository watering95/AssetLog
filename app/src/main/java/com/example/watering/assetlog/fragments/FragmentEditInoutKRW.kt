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
import com.example.watering.assetlog.databinding.FragmentEditInoutKrwBinding
import com.example.watering.assetlog.viewmodel.ViewModelEditInoutKRW

class FragmentEditInoutKRW : Fragment() {
    private lateinit var binding: FragmentEditInoutKrwBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private var id_account:Int? = 0
    private var date:String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_inout_krw, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditInoutKRW::class.java)

        initLayout()

        return binding.root
    }

    fun initInstance(id_account:Int?, date:String?):FragmentEditInoutKRW {
        this.id_account = id_account
        this.date = date
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_inout_krw)

        binding.viewmodel?.run {
            date = this@FragmentEditInoutKRW.date

            modifyIOKRW(id_account, date).observe(this@FragmentEditInoutKRW, Observer { io -> io?.let {
                income = io.income
                evaluation = io.evaluation_krw
                deposit = io.input
                spend = io.spend_card!! + io.spend_cash!!
                withdraw = io.output
                this.io = io
                modifyDairyKRW(id_account, date).observe(this@FragmentEditInoutKRW, Observer { dairy -> dairy?.let {
                    principal = dairy.principal_krw
                }})
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
            modifyDairyKRW(id_account, date).observeOnce(Observer { dairy -> dairy?.let {
                if(dairy.id == null) insert(dairy) else update(dairy)
                mFragmentManager.popBackStack()
            }})
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