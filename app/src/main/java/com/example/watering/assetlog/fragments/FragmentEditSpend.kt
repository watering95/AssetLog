package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditSpendBinding
import com.example.watering.assetlog.entities.Spend
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.example.watering.assetlog.viewmodel.ViewModelEditSpend

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
    private fun initLayout() {
        val activity = activity as MainActivity
        mViewModel = activity.mViewModel
        binding.viewmodel = ViewModelEditSpend(item,0,0,0,0)

        mViewModel.getCatMainsByKind("spend").observe(this, Observer { list -> list?.let {
            listOfAdapterMain = list.map { it.name }
            binding.adapterMain = ArrayAdapter(activity,android.R.layout.simple_spinner_item,listOfAdapterMain)
        } })

        binding.viewmodel?.apply {
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
                                mViewModel.getCatSub(id_sub).observe(this@FragmentEditSpend,
                                    Observer { sub -> sub?.let { idx_cat_sub = listOfAdapterSub.indexOf(sub.name) }
                                })
                            }
                        })
                    }
                })
                it.code
            }?.let {
                val type = it.substring(0,1).toInt()
                when(type) {
                    1 -> { idx_approval2 = getIdxOfAccount(it) }
                    2 -> { mViewModel.getSpendCardByCode(it).observe(this@FragmentEditSpend, Observer {spendCard -> spendCard?.let {
                        mViewModel.getCard(spendCard.card).observe(this@FragmentEditSpend, Observer { card -> card?.let {
                            idx_approval2 = listOfAdapterApproval2.indexOf(card.number)
                        } })
                    } }) }
                    else -> {}
                }
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
            R.id.menu_edit_save -> {

            }
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }

    private fun getIdxOfAccount(spend_code: String?): Int {
        var idx = 0
        return idx
    }
}