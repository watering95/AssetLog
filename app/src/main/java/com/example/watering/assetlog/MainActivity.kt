package com.example.watering.assetlog

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.watering.assetlog.fragments.FragmentAccounts
import com.example.watering.assetlog.fragments.FragmentBook
import com.example.watering.assetlog.fragments.FragmentHome
import com.example.watering.assetlog.fragments.FragmentManagement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fragmentManager = supportFragmentManager
    var transaction = fragmentManager.beginTransaction()
    val fragmentHome = FragmentHome()
    val fragmentBook = FragmentBook()
    val fragmentAccounts = FragmentAccounts()
    val fragmentManagement = FragmentManagement()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        transaction = fragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.main_fragment, fragmentHome)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_book -> {
                transaction.replace(R.id.main_fragment, fragmentBook)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_accounts -> {
                transaction.replace(R.id.main_fragment, fragmentAccounts)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_management -> {
                transaction.replace(R.id.main_fragment, fragmentManagement)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.app_name)

        transaction.add(R.id.main_fragment, fragmentHome).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
