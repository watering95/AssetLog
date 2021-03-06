package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditInoutForeignBinding
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.model.Processing
import com.example.watering.assetlog.viewmodel.ViewModelEditInoutForeign
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import java.util.*

class FragmentEditInoutForeign : Fragment() {
    private lateinit var binding: FragmentEditInoutForeignBinding
    private lateinit var processing: Processing
    private var idAccount:Int? = 0
    private var date:String? = ""
    private var currency:Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_inout_foreign, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditInoutForeign::class.java)
        processing = Processing(binding.viewmodel, fragmentManager)

        initLayout()

        return binding.root
    }

    fun initInstance(id_account:Int?, date:String?):FragmentEditInoutForeign {
        this.idAccount = id_account
        this.date = date
        return this
    }

    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_inout_foreign)

        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.date -> onChangedDate()
                    }
                }
            })

            date = this@FragmentEditInoutForeign.date
            notifyPropertyChanged(BR.date)
        }

        binding.buttonDateFragmentEditInoutForeign.setOnClickListener {
            binding.viewmodel?.run {
                val dialog = DialogDate().newInstance(date, object:DialogDate.Complete {
                    override fun onComplete(date: String?) {
                        val select = ModelCalendar.strToCalendar(date)
                        when {
                            Calendar.getInstance().before(select) -> Toast.makeText(activity, R.string.toast_date_error, Toast.LENGTH_SHORT).show()
                            else -> {
                                this@run.date = ModelCalendar.calendarToStr(select)
                                notifyPropertyChanged(BR.date)
                            }
                        }
                    }
                })
                dialog.show(fragmentManager, "dialog")
            }
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
            R.id.menu_edit_delete -> binding.viewmodel?.run {
                runBlocking {
                    delete(io).cancelAndJoin()
                    processing.dairyForeign(idAccount, io.date, io.currency)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        binding.viewmodel?.run {
            val jobIO = if(io.id == null) insert(io) else update(io)

            runBlocking {
                jobIO.cancelAndJoin()

                processing.dairyForeign(idAccount, io.date, io.currency)
            }
        }
    }

    private fun onChangedDate() {
        binding.viewmodel?.run {
            loadingIOForeign(idAccount, date, currency).observe(this@FragmentEditInoutForeign,  Observer { io -> io?.let {
                deposit = io.input
                withdraw = io.output
                depositKRW = io.inputKRW
                withdrawKRW = io.outputKRW
                indexOfCurrency = io.currency
                evaluationKRW = io.evaluationKRW
                this.io = io
                loadingDairyForeign(idAccount, date, currency).observe(this@FragmentEditInoutForeign, Observer { dairy -> dairy?.let {
                    principal = dairy.principal
                } })
            } })
        }
    }
}