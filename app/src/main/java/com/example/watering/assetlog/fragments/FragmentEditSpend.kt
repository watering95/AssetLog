package com.example.watering.assetlog.fragments

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
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.entities.SpendCard
import com.example.watering.assetlog.entities.SpendCash
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.model.Processing
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import java.util.*

class FragmentEditSpend : Fragment() {
    private lateinit var binding: FragmentEditSpendBinding
    private lateinit var processing: Processing
    private lateinit var spend: Spend
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_spend, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditSpend::class.java)
        processing = Processing(binding.viewmodel, mFragmentManager)

        initLayout()

        return binding.root
    }

    fun initInstance(item: Spend):FragmentEditSpend {
        spend = item
        return this
    }

    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_spend)
        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.indexOfSub -> onChangedIndexOfSub()
                        BR.indexOfPay2 -> onChangedIndexOfPay2()
                    }
                }
            })

            listOfMain = Transformations.map(getCatMainsByKind("spend")) { list -> list.map { it.name } } as MutableLiveData<List<String?>>

            spend = this@FragmentEditSpend.spend
            this@FragmentEditSpend.spend.code?.let { oldCode = it }
            if(spend.id == null) {
                spend = spend.apply { this.date = ModelCalendar.getToday() }
                notifyPropertyChanged(BR.spend)
            }
            when(oldCode[0]) {
                '1' -> {
                    indexOfPay1 = 0
                    listOfPay2.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                        getAccountByCode(oldCode).observe(this@FragmentEditSpend, Observer { account -> account?.let {
                            indexOfPay2 = list.indexOf(account.number)
                            idAccount = account.id
                        } })
                    } })
                }
                '2' -> {
                    indexOfPay1 = 1
                    listOfPay2.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                        getCardByCode(oldCode).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            indexOfPay2 = list.indexOf(card.number)
                            idCard = card.id
                            idAccount = card.account
                        } })
                    } })
                }
            }

            getCatMainBySub(this@FragmentEditSpend.spend.category).observe(this@FragmentEditSpend, Observer { main -> main?.let {
                Transformations.map(listOfMain) { list -> list.indexOf(main.name) }.observe(this@FragmentEditSpend, Observer { index -> index?.let {
                    indexOfMain = index
                } })
                listOfSub = Transformations.map(getCatSubsByMain(main.id)) { list ->
                    list.map { it.name }.apply {
                        getCatSub(this@FragmentEditSpend.spend.category).observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                            indexOfSub = indexOf(sub.name)
                        } })
                    }
                } as MutableLiveData<List<String?>>
            } })
        }

        binding.buttonDateFragmentEditSpend.setOnClickListener {
            binding.viewmodel?.run {
                val dialog = DialogDate().newInstance(spend.date, object:DialogDate.Complete {
                    override fun onComplete(date: String?) {
                        val select = ModelCalendar.strToCalendar(date)
                        when {
                            Calendar.getInstance().before(select) -> Toast.makeText(activity, R.string.toast_date_error, Toast.LENGTH_SHORT).show()
                            else -> {
                                spend = spend.apply { this.date = ModelCalendar.calendarToStr(select) }
                                notifyPropertyChanged(BR.spend)
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
                var jobDelete = Job()

                when(oldCode[0]) {
                    '1' -> getSpendCash(oldCode).observe(this@FragmentEditSpend, Observer { cash -> cash?.let {
                        jobDelete = delete(cash)
                    }})
                    '2' -> getSpendCard(oldCode).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                        jobDelete = delete(card)
                    }})
                }

                runBlocking {
                    delete(spend).cancelAndJoin()
                    jobDelete.cancelAndJoin()
                    processing.ioKRW(idAccount, spend.date)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun onChangedIndexOfSub() {
        binding.viewmodel?.run {
            Transformations.switchMap(listOfMain) { listOfMain ->
                Transformations.switchMap(listOfSub) { listOfSub ->
                    if(indexOfSub < 0 || indexOfMain < 0) null
                    else getCatSub(listOfSub[indexOfSub], listOfMain[indexOfMain])
                }
            }.observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                spend.category = sub.id
            } })
        }
    }
    fun onChangedIndexOfPay2() {
        binding.viewmodel?.run {
            when(newCode[0]) {
                '1' -> {
                    Transformations.switchMap(listOfPay2) { list -> getAccountByNumber(list[indexOfPay2])
                    }.observe(this@FragmentEditSpend, Observer { account -> account?.let {
                        idAccount = account.id
                    } })
                }
                '2' -> {
                    Transformations.switchMap(listOfPay2) { list -> getCardByNumber(list[indexOfPay2])
                    }.observe(this@FragmentEditSpend, Observer { card -> card?.let {
                        idCard = card.id
                        idAccount = card.account
                    } })
                }
            }
        }
    }

    private fun save() {
        binding.viewmodel?.run {
            Transformations.map(getLastSpendCode(spend.date)) { code ->
                val index = code?.substring(10,12)?.toInt() ?: -1
                newCode = newCode.replaceRange(10, 12, String.format("%02d", index + 1))
                newCode
            }.observeOnce(Observer { code -> code?.let {
                spend.code = code

                var jobDelete = Job()
                val jobSpend1 = if(spend.id == null) insert(spend)
                else {
                    when(oldCode[0]) {
                        '1' -> getSpendCash(oldCode).observe(this@FragmentEditSpend, Observer { cash -> cash?.let {
                            jobDelete = delete(cash)
                        } })
                        '2' -> getSpendCard(oldCode).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            jobDelete = delete(card)
                        } })
                    }
                    update(spend)
                }

                val jobSpend2 = when(code[0]) {
                    '1' -> {
                        val spendCash = SpendCash().apply {
                            this.code = code
                            this.account = idAccount
                        }
                        insert(spendCash)
                    }
                    '2' -> {
                        val spendCard = SpendCard().apply {
                            this.code = code
                            this.card = idCard
                        }
                        insert(spendCard)
                    }
                    else -> insert(null)
                }

                runBlocking {
                    jobSpend1.cancelAndJoin()
                    jobSpend2.cancelAndJoin()
                    jobDelete.cancelAndJoin()
                    processing.ioKRW(idAccount, spend.date)
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