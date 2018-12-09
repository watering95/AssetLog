package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.example.watering.assetlog.BR
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend

class FragmentEditSpend : Fragment() {
    private lateinit var item: Spend
    private lateinit var binding: FragmentEditSpendBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_spend, container, false)
        binding.setLifecycleOwner(this)
        binding.viewmodel = ViewModelProviders.of(this).get(ViewModelEditSpend::class.java)

        initLayout()

        return binding.root
    }
    fun initInstance(item: Spend):FragmentEditSpend {
        this.item = item
        return this
    }

    private fun initLayout() {
        binding.viewmodel?.run {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    when(propertyId) {
                        BR.spend -> onSpendChanged()
                        BR.indexOfMain -> OnIndexOfMainChanged()
                        BR.listOfSub -> onListOfSubChanged()
                        BR.listOfPay2 -> onListOfPay2Changed()
                        BR.indexOfPay1 -> onIndexOfPay1Changed()
                    }
                }
            })
            spend = item
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
            R.id.menu_edit_save -> {

            }
            R.id.menu_edit_delete -> binding.viewmodel?.delete(this.item)
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }

    fun onSpendChanged() {
        binding.viewmodel?.run {
            listOfMain.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                getCatMainBySub(id_sub).observe(this@FragmentEditSpend, Observer { main -> main?.let {
                    indexOfMain = list.indexOf(main.name)
                } })
            } })
            when(code[0]) {
                '1' -> indexOfPay1 = 0
                '2' -> indexOfPay1 = 1
            }
            notifyPropertyChanged(BR.indexOfMain)
            notifyPropertyChanged(BR.indexOfPay1)
        }
    }

    fun OnIndexOfMainChanged() {
        binding.viewmodel?.run {
            listOfSub = Transformations.switchMap(listOfMain) { listOfMain ->
                Transformations.map(getCatSubsByMain(listOfMain[indexOfMain])) { listOfSub -> listOfSub.map { it.name } }
            } as MutableLiveData<List<String?>>
            notifyPropertyChanged(BR.listOfSub)
        }
    }

    fun onListOfSubChanged() {
        binding.viewmodel?.run {
            listOfSub.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                getCatSub(id_sub).observe(this@FragmentEditSpend, Observer { sub -> sub?.let {
                    indexOfSub = list.indexOf(sub.name)
                } })
            } })
            notifyPropertyChanged(BR.indexOfSub)
        }
    }

    fun onIndexOfPay1Changed() {
        binding.viewmodel?.run {
            when(indexOfPay1) {
                0 -> listOfPay2 = Transformations.map(allAccounts) { list -> list.map { it.number }} as MutableLiveData<List<String?>>
                1 -> listOfPay2 = Transformations.map(allCards) { list -> list.map { it.number } } as MutableLiveData<List<String?>>
            }
            notifyPropertyChanged(BR.listOfPay2)
        }
    }

    fun onListOfPay2Changed() {
        binding.viewmodel?.run {
            listOfPay2.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                when(indexOfPay1) {
                    0 -> {
                        getAccountByCode(code).observe(this@FragmentEditSpend, Observer { account -> account?.let {
                            indexOfPay2 = list.indexOf(account.number)
                        } })
                    }
                    1 -> {
                        getCardByCode(code).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            indexOfPay2 = list.indexOf(card.number)
                        } })
                    }
                }
            } })
            notifyPropertyChanged(BR.indexOfPay2)
        }
    }
}