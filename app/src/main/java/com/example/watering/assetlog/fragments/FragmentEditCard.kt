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
import com.example.watering.assetlog.databinding.FragmentEditCardBinding
import com.example.watering.assetlog.entities.Card
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.example.watering.assetlog.viewmodel.EditCardViewModel

class FragmentEditCard : Fragment() {
    private lateinit var item: Card
    private lateinit var mViewModel: AppViewModel
    private lateinit var binding: FragmentEditCardBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_card, container, false)
        initLayout()
        return binding.root
    }
    fun initInstance(item: Card):FragmentEditCard {
        this.item = item
        return this
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        mViewModel = activity.mViewModel

        setHasOptionsMenu(true)

        mViewModel.allAccounts.observe(this, Observer { accounts -> accounts?.let {listAccount ->
            val listName = listAccount.map { it.number }
            when {
                this.item.id != null -> mViewModel.getAccount(this.item.account).observe(this, Observer { selectedAccount -> selectedAccount?.let {
                    binding.viewmodel = EditCardViewModel(this.item, listName.indexOf(it.number))
                    binding.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item,listName)
                } })
                else -> {
                    binding.viewmodel = EditCardViewModel(this.item, 0)
                    binding.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item,listName)
                }
            }
        } })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.menu_edit,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_edit_save -> {
                mViewModel.allAccounts.observe(this, Observer { accounts -> accounts?.let { list ->
                    binding.viewmodel?.let { viewModel ->
                        viewModel.card?.apply { viewModel.selected?.let { account = list[it].id } }.let {
                            when {
                                this.item.id == null -> mViewModel.insert(it)
                                else -> mViewModel.update(it)
                            }
                        }
                    }
                } })
            }
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}