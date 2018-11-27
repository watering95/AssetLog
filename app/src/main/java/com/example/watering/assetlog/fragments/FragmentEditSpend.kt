package com.example.watering.assetlog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.model.ModelCalendar
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend
import java.util.*

class FragmentEditSpend : Fragment() {
    private lateinit var item: Spend
    private lateinit var mViewModel: ViewModelApp
    private lateinit var binding: FragmentEditSpendBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
    private var listOfAdapterMain = listOf<String?>()
    private var listOfAdapterApproval2 = listOf<String?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_spend, container, false)
        initLayout()
        return binding.root
    }
    fun initInstance(item: Spend):FragmentEditSpend {
        this.item = item
        return this
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        val activity = activity as MainActivity
        mViewModel = activity.mViewModel
        binding.viewmodel = ViewModelEditSpend(item,0,0,0,0)

        mViewModel.getCatMainsByKind("spend").observe(this, Observer { list -> list?.run {
            listOfAdapterMain = list.map { it.name }
            binding.adapterMain = ArrayAdapter(activity,android.R.layout.simple_spinner_item,listOfAdapterMain)
        } })

        binding.viewmodel?.run {
            spend?.let {
                val id_sub = it.category
                mViewModel.getCatSub(id_sub).observe(this@FragmentEditSpend, Observer { sub ->
                    sub?.categoryMain.let { id_main ->
                        mViewModel.getCatMain(id_main).observe(this@FragmentEditSpend,
                            Observer { main -> main?.let { idx_cat_main = listOfAdapterMain.indexOf(main.name) }
                        })
                        mViewModel.getCatSubsByMain(id_main).observe(this@FragmentEditSpend, Observer { list ->
                            list?.map { it.name }?.let { listOfAdapterSub ->
                                binding.adapterSub = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfAdapterSub)
                                idx_cat_sub = listOfAdapterSub.indexOf(sub.name)
                            }
                        })
                    }
                })
                it.code
            }?.let {code ->
                val type = code.substring(0,1).toInt()

                when(type) {
                    1 -> {
                        idx_approval1 = 0
                        // account list로 spinner 등록
                        mViewModel.allAccounts.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                            listOfAdapterApproval2 = list.map { it.number }
                            binding.adapterApproval2 = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfAdapterApproval2)
                        } })
                        // account 선택
                        mViewModel.getAccountByCode(code).observe(this@FragmentEditSpend, Observer { account -> account?.let {
                            idx_approval2 = listOfAdapterApproval2.indexOf(account.number)
                        } })
                    }
                    2 -> {
                        idx_approval1 = 1
                        // card list로 spinner 등록
                        mViewModel.allCards.observe(this@FragmentEditSpend, Observer { list -> list?.let {
                            listOfAdapterApproval2 = list.map { it.number }
                            binding.adapterApproval2 = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfAdapterApproval2)
                        } })
                        // 카드 선택
                        mViewModel.getCardByCode(code).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            idx_approval2 = listOfAdapterApproval2.indexOf(card.number)
                        } })
                    }
                    else -> {}
                }
            }
        }

        binding.editDateFragmentEditSpend.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_UP -> {
                    val dialog = DialogDate().newInstance(binding.viewmodel?.spend?.date, object:DialogDate.Complete {
                        override fun onComplete(date: String?) {
                            val select = ModelCalendar.strToCalendar(date)
                            when {
                                Calendar.getInstance().before(select) -> Toast.makeText(activity, R.string.toast_date_error, Toast.LENGTH_SHORT).show()
                                else -> {
                                    binding.viewmodel = binding.viewmodel?.apply {
                                        spend?.let { it.date = ModelCalendar.calendarToStr(select) }
                                    }
                                }
                            }
                        }
                    })
                    dialog.show(fragmentManager, "dialog")
                }
                else -> {}
            }
            true
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
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}