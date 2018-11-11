package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditCategorymainBinding
import com.example.watering.assetlog.entities.CategoryMain
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.example.watering.assetlog.viewmodel.EditCategoryMainViewModel

class FragmentEditCategoryMain : Fragment() {
    private lateinit var item: CategoryMain
    private lateinit var mViewModel: AppViewModel
    private lateinit var binding:FragmentEditCategorymainBinding
    private var mFragmentManager: FragmentManager? = null
    private lateinit var mList:List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_categorymain, container, false)
        initLayout()
        return binding.root
    }
    fun initInstance(item: CategoryMain):FragmentEditCategoryMain {
        this.item = item
        return this
    }
    private fun initLayout() {
        mFragmentManager = fragmentManager

        val activity = activity as MainActivity
        mViewModel = activity.mViewModel

        mList = resources.getStringArray(R.array.category).toList()

        binding.viewmodel = EditCategoryMainViewModel(this.item, mList.indexOf(this.item.kind))

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
                binding.viewmodel?.let { viewModel ->
                    viewModel.categoryMain?.let {
                        viewModel.selected?.let { selected -> it.kind = mList[selected] }
                        when {
                            this.item.id == null -> mViewModel.insert(it)
                            else -> mViewModel.update(it)
                        }
                    }
                }
            }
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        mFragmentManager?.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}