package com.example.watering.assetlog.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.watering.assetlog.MainActivity
import com.example.watering.assetlog.R
import com.example.watering.assetlog.databinding.FragmentEditCategorysubBinding
import com.example.watering.assetlog.entities.CategorySub
import com.example.watering.assetlog.viewmodel.ViewModelApp
import com.example.watering.assetlog.viewmodel.ViewModelEditCategorySub

class FragmentEditCategorySub : Fragment() {
    private lateinit var item: CategorySub
    private lateinit var mViewModel: ViewModelApp
    private lateinit var binding:FragmentEditCategorysubBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_edit_categorysub, container, false)
        initLayout()
        return binding.root
    }
    fun initInstance(item: CategorySub):FragmentEditCategorySub {
        this.item = item
        return this
    }
    private fun initLayout() {
        val activity = activity as MainActivity
        mViewModel = activity.mViewModel

        setHasOptionsMenu(true)

        mViewModel.allCatMains.observe(this, Observer { list -> list?.let {
            list.map { it.name }.let { list ->
                when {
                    this.item.id != null -> mViewModel.getCatMain(this.item.categoryMain).observe(this, Observer { main -> main?.let {
                        binding.viewmodel = ViewModelEditCategorySub(this.item, list.indexOf(main.name))
                    } })
                    else -> binding.viewmodel = ViewModelEditCategorySub(this.item, 0)
                }
                binding.adapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item,list)
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
                binding.viewmodel?.run {
                    mViewModel.allCatMains.observe(this@FragmentEditCategorySub, Observer { list -> list?.let {
                        categorySub?.apply { selected?.let { categoryMain = list[it].id } }.let { sub ->
                            when {
                                this@FragmentEditCategorySub.item.id == null -> mViewModel.insert(sub)
                                else -> mViewModel.update(sub)
                            }
                        }
                    } })
                }
            }
            R.id.menu_edit_delete -> { mViewModel.delete(this.item) }
        }
        fragmentManager?.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}