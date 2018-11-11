package com.example.watering.assetlog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.watering.assetlog.fragments.*
import com.example.watering.assetlog.model.GoogleDrive
import com.example.watering.assetlog.viewmodel.AppViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.drive.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val mFragmentManager = this.supportFragmentManager!!
    private var mTransaction = mFragmentManager.beginTransaction()
    private val mFragmentHome = FragmentHome()
    private val mFragmentBook = FragmentBook()
    private val mFragmentAccounts = FragmentAccounts()
    private val mFragmentManagement = FragmentManagement()
    val mGoogleDrive = GoogleDrive(this)
    lateinit var mViewModel: AppViewModel

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        mTransaction = mFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_home -> {
                with(mTransaction) {
                    replace(R.id.frame_main, mFragmentHome)
                    commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_book -> {
                with(mTransaction) {
                    replace(R.id.frame_main, mFragmentBook)
                    commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_accounts -> {
                with(mTransaction) {
                    replace(R.id.frame_main, mFragmentAccounts)
                    commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_management -> {
                with(mTransaction) {
                    replace(R.id.frame_main, mFragmentManagement)
                    commit()
                }
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

        mTransaction.add(R.id.frame_main, mFragmentHome).addToBackStack(null).commit()

        mViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GoogleDrive.REQUEST_CODE_SIGN_IN_UP, GoogleDrive.REQUEST_CODE_SIGN_IN_DOWN -> {
                when (resultCode) {
                    RESULT_OK -> {
                        mGoogleDrive.driveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                        mGoogleDrive.driveResourceClient = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                        when (requestCode) {
                            GoogleDrive.REQUEST_CODE_SIGN_IN_UP -> mGoogleDrive.saveFileToDrive()
                            else -> mGoogleDrive.downloadFileFromDrive()
                        }
                    }
                }
            }
            GoogleDrive.REQUEST_CODE_CREATOR -> when (resultCode) {
                RESULT_OK -> Toast.makeText(this,R.string.toast_db_backup_ok,Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this,R.string.toast_db_backup_error,Toast.LENGTH_SHORT).show()
            }
            GoogleDrive.REQUEST_CODE_OPENER -> when (resultCode) {
                RESULT_OK -> {
                    mGoogleDrive.currentDriveId = data?.getParcelableExtra(OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID)!!
                    mGoogleDrive.loadCurrentFile()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
