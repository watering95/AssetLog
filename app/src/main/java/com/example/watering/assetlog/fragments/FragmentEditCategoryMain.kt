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
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.example.watering.assetlog.viewmodel.ViewModelEditCategoryMain

class FragmentEditCategoryMain : Fragment() {
    private lateinit var item: CategoryMain
    private lateinit var mViewModel: ViewModelApp
    private lateinit var binding:FragmentEditCategorymainBinding
    private val mFragmentManager by lazy { fragmentManager as FragmentManager }
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
        val activity = activity as MainActivity
        mViewModel = activity.mViewModel

        mList = resources.getStringArray(R.array.category).toList()

        binding.viewmodel = ViewModelEditCategoryMain(this.item, mList.indexOf(this.item.kind))

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
                binding.viewmodel?.run {
                    categoryMain?.apply { selected?.let { kind = mList[it] } }.let { main ->
                        when {
                            this@FragmentEditCategoryMain.item.id == null -> mViewModel.insert(main)
                            else -> mViewModel.update(main)
                        }
                    }
                }
            }
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        mFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}