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
import com.example.watering.assetlog.databinding.FragmentEditAccountBinding
import com.example.watering.assetlog.entities.Account
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.example.watering.assetlog.viewmodel.EditAccountViewModel

class FragmentEditAccount : Fragment() {
    private lateinit var item: Account
    private lateinit var mViewModel: AppViewModel
    private lateinit var binding:FragmentEditAccountBinding
    private var mFragmentManager: FragmentManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_account, container, false)
        initLayout()
        return binding.root
    }
    fun initInstance(item: Account):FragmentEditAccount {
        this.item = item
        return this
    }
    private fun initLayout() {
        mFragmentManager = fragmentManager

        val activity = activity as MainActivity
        mViewModel = activity.mViewModel

        setHasOptionsMenu(true)

        mViewModel.allGroups.observe(this, Observer { groups -> groups?.let {listGroup ->
            val listName = listGroup.map { it.name }
            when {
                this.item.id != null -> mViewModel.getGroup(this.item.group).observe(this, Observer { selectedGroup -> selectedGroup?.let {
                    binding.viewmodel = EditAccountViewModel(this.item, listName.indexOf(it.name))
                    binding.adapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item,listName)
                } })
                else -> {
                    binding.viewmodel = EditAccountViewModel(this.item, 0)
                    binding.adapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item,listName)
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
                binding.viewmodel?.let { viewModel ->
                    mViewModel.allGroups.observe(this, Observer { groups -> groups?.let { listGroup ->
                        viewModel.selectedGroup?.let { id ->
                            viewModel.account?.let { it.group = listGroup[id].id }
                        }
                    } })
                    when {
                        this.item.id == null -> mViewModel.insert(viewModel.account)
                        else -> mViewModel.update(viewModel.account)
                    }
                }
            }
            R.id.menu_edit_delete -> {
                mViewModel.delete(this.item)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}