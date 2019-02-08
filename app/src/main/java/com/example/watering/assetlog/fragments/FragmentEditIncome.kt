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
import com.example.watering.assetlog.databinding.FragmentEditIncomeBinding
import com.example.watering.assetlog.entities.Income
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.viewmodel.ViewModelEditIncome
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import java.util.*

class FragmentEditIncome : Fragment() {
    private lateinit var income: Income
    private lateinit var binding: FragmentEditIncomeBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_income, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditIncome::class.java)

        initLayout()
        return binding.root
    }
    fun initInstance(item: Income):FragmentEditIncome {
        income = item
        return this
    }

    private fun initLayout() {
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_income)

        binding.viewmodel?.run {
            listOfMain = Transformations.map(getCatMainsByKind("income")) { list -> list.map { it.name } } as MutableLiveData<List<String?>>
            listOfAccount = Transformations.map(allAccounts) { list -> list.map { it.number } } as MutableLiveData<List<String?>>

            income = this@FragmentEditIncome.income
            if(income.id == null) {
                income = income.apply { this.date = ModelCalendar.getToday() }
                notifyPropertyChanged(BR.income)
            }

            getCatMainBySub(this@FragmentEditIncome.income.category).observe(this@FragmentEditIncome, Observer { main -> main?.let {
                Transformations.map(listOfMain) { list -> list.indexOf(main.name) }.observe(this@FragmentEditIncome, Observer { index -> index?.let {
                    indexOfMain = index
                } })
                listOfSub = Transformations.map(getCatSubsByMain(main.id)) { list ->
                    list.map { it.name }.apply {
                        getCatSub(this@FragmentEditIncome.income.category).observe(this@FragmentEditIncome, Observer { sub -> sub?.let {
                            indexOfSub = indexOf(sub.name)
                            listOfAccount.observe(this@FragmentEditIncome, Observer { list -> list?.let {
                                getAccount(income.account).observe(this@FragmentEditIncome, Observer { account -> account?.let {
                                    indexOfAccount = list.indexOf(account.number)
                                    idAccount = account.id
                                } })
                            } })
                        } })
                    }
                } as MutableLiveData<List<String?>>
            } })

            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.indexOfSub -> onIndexOfSubChanged()
                        BR.indexOfAccount -> onIndexOfAccountChanged()
                    }
                }
            })
        }

        binding.buttonDateFragmentEditIncome.setOnClickListener {
            binding.viewmodel?.run {
                val dialog = DialogDate().newInstance(income.date, object:DialogDate.Complete {
                    override fun onComplete(date: String?) {
                        val select = ModelCalendar.strToCalendar(date)
                        when {
                            Calendar.getInstance().before(select) -> Toast.makeText(activity, R.string.toast_date_error, Toast.LENGTH_SHORT).show()
                            else -> {
                                income = income.apply { this.date = ModelCalendar.calendarToStr(select) }
                                notifyPropertyChanged(BR.income)
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
                    delete(income).cancelAndJoin()

                    loadingIOKRW(idAccount, income.date).observeOnce(Observer { io -> io?.let {
                        val jobIO = if(io.id == null) insert(io) else update(io)

                        runBlocking {
                            jobIO.cancelAndJoin()

                            loadingDairyKRW(idAccount, income.date).observeOnce(Observer { dairy -> dairy?.let {
                                val jobDairy = if(dairy.id == null) insert(dairy) else update(dairy)

                                runBlocking {
                                    jobDairy.cancelAndJoin()
                                    mFragmentManager.popBackStack()
                                }
                            } })
                        }
                    } })
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun onIndexOfSubChanged() {
        binding.viewmodel?.run {
            Transformations.switchMap(listOfMain) { listOfMain ->
                Transformations.switchMap(listOfSub) { listOfSub ->
                    if(indexOfSub < 0 || indexOfMain < 0) null
                    else getCatSub(listOfSub[indexOfSub], listOfMain[indexOfMain])
                }
            }.observe(this@FragmentEditIncome, Observer { sub -> sub?.let {
                income.category = sub.id
            } })
        }
    }
    fun onIndexOfAccountChanged() {
        binding.viewmodel?.run {
            Transformations.switchMap(listOfAccount) { list ->
                if(indexOfAccount < 0) null else getAccountByNumber(list[indexOfAccount])
            }.observe(this@FragmentEditIncome, Observer { account -> account?.let {
                idAccount = account.id
                income.account = account.id
            } })
        }
    }
    private fun save() {
        binding.viewmodel?.run {
            val jobIncome = if(income.id == null) insert(income) else update(income)

            runBlocking {
                jobIncome.cancelAndJoin()

                loadingIOKRW(idAccount, income.date).observeOnce(Observer { io -> io?.let {
                    val jobIO = if(io.id == null) insert(io) else update(io)

                    runBlocking {
                        jobIO.cancelAndJoin()

                        loadingDairyKRW(idAccount, income.date).observeOnce(Observer { dairy -> dairy?.let {
                            val jobDairyKRW = if(dairy.id == null) insert(dairy) else update(dairy)

                            runBlocking {
                                jobDairyKRW.cancelAndJoin()

                                getAfterOfDairyKRW(idAccount, income.date).observeOnce(Observer { list -> list?.let {
                                    list.forEach { date ->
                                        loadingDairyKRW(idAccount, date).observeOnce(Observer { d -> d?.let {
                                            runBlocking { update(d).cancelAndJoin() }
                                        }})
                                    }
                                }})
                                loadingDairyTotal(idAccount, income.date).observeOnce(Observer { dairy -> dairy?.let {
                                    val jobDairyTotal = if(dairy.id == null) insert(dairy) else update(dairy)

                                    runBlocking {
                                        jobDairyTotal.cancelAndJoin()

                                        getAfterOfDairyTotal(idAccount, income.date).observeOnce(Observer { list -> list?.let {
                                            list.forEach { date ->
                                                loadingDairyTotal(idAccount, date).observeOnce(Observer { d -> d?.let {
                                                    runBlocking { update(d).cancelAndJoin() }
                                                }})
                                            }
                                        }})
                                        mFragmentManager.popBackStack()
                                    }
                                } })
                            }
                        } })
                    }
                } })
            }
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