package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend
import java.util.*

class FragmentEditSpend : Fragment() {
    private lateinit var binding: FragmentEditSpendBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private lateinit var spend: Spend

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_spend, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditSpend::class.java)

        initLayout()

        return binding.root
    }

    fun initInstance(item: Spend):FragmentEditSpend {
        spend = item
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.indexOfSub -> onIndexOfSubChanged()
                        BR.indexOfPay2 -> onIndexOfPay2Changed()
                    }
                }
            })

            listOfMain = Transformations.map(getCatMainsByKind("spend")) { list -> list.map { it.name } } as MutableLiveData<List<String?>>

            spend = this@FragmentEditSpend.spend

            oldCode = this@FragmentEditSpend.spend.code!!
            when(oldCode[0]) {
                '1' -> {
                    indexOfPay1 = 0
                    listOfPay2.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                        getAccountByCode(oldCode).observe(this@FragmentEditSpend, Observer { account -> account?.let {
                            indexOfPay2 = list.indexOf(account.number)
                        } })
                    } })
                }
                '2' -> {
                    indexOfPay1 = 1
                    listOfPay2.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                        getCardByCode(oldCode).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            indexOfPay2 = list.indexOf(card.number)
                        } })
                    } })
                }
            }

            getCatMainBySub(this@FragmentEditSpend.spend.category).observe(this@FragmentEditSpend, Observer { main -> main?.let {
                Transformations.map(listOfMain) { list -> indexOfMain = list.indexOf(main.name) }
                listOfSub = Transformations.map(getCatSubsByMain(main.id)) { list ->
                    list.map { it.name }.apply {
                        getCatSub(this@FragmentEditSpend.spend.category).observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                            indexOfSub = indexOf(sub.name)
                        } })
                    }
                } as MutableLiveData<List<String?>>
            } })
        }

        binding.editDateFragmentEditSpend.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.viewmodel?.run {
                        val dialog = DialogDate().newInstance(spend.date, object:DialogDate.Complete {
                            override fun onComplete(date: String?) {
                                val select = ModelCalendar.strToCalendar(date)
                                when {
                                    Calendar.getInstance().before(select) -> Toast.makeText(activity, R.string.toast_date_error, Toast.LENGTH_SHORT).show()
                                    else -> {
                                        spend.date = ModelCalendar.calendarToStr(select)
                                        notifyPropertyChanged(BR.spend)
                                    }
                                }
                            }
                        })
                        dialog.show(fragmentManager, "dialog")
                    }
                }
            }
            return@setOnTouchListener false
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
            R.id.menu_edit_delete -> binding.viewmodel?.run { delete(spend) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }

    fun onIndexOfSubChanged() {
        binding.viewmodel?.run {
            Transformations.switchMap(listOfMain) { listOfMain ->
                Transformations.switchMap(listOfSub) { listOfSub -> getCatSub(listOfSub[indexOfSub], listOfMain[indexOfMain]) }
            }.observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                spend.category = sub.id
            } })
        }
    }
    fun onIndexOfPay2Changed() {
        binding.viewmodel?.run {
            when(newCode[0]) {
                '1' -> {
                    Transformations.switchMap(listOfPay2) { list -> getAccountByNumber(list[indexOfPay2])
                    }.observe(this@FragmentEditSpend, Observer { account -> account?.let {
                        id_account = account.id
                    } })
                }
                '2' -> {
                    Transformations.switchMap(listOfPay2) { list -> getCardByNumber(list[indexOfPay2])
                    }.observe(this@FragmentEditSpend, Observer { card -> card?.let {
                        id_card = card.id
                    } })
                }
            }
        }
    }

    fun save() {
        binding.viewmodel?.run {

        }
    }
}