package com.example.watering.assetlog

import android.content.Context
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.drive.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class GoogleDrive(val context: Context) {
    private val dbFileName = "AssetLog"
    private val TAG = "AssetLog"
    lateinit var driveResourceClient: DriveResourceClient
    lateinit var driveClient: DriveClient
    lateinit var currentDriveId: DriveId
    private lateinit var driveContents: DriveContents
    companion object {
        const val REQUEST_CODE_SIGN_IN_UP = 0
        const val REQUEST_CODE_SIGN_IN_DOWN = 1
        const val REQUEST_CODE_CREATOR = 2
        const val REQUEST_CODE_OPENER = 3
    }

    fun deleteDBFile(context: Context) {
        try {
            context.getDatabasePath(dbFileName).delete()
            Toast.makeText(context, R.string.toast_db_del_ok, Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(context, R.string.toast_db_del_error, Toast.LENGTH_SHORT).show()
        }
    }
    fun saveFileToDrive() {
        val context = this.context as AppCompatActivity
        val fileInputStream: FileInputStream
        val directory = context.filesDir.toString()
        val fileName = directory.substring(0,directory.length-5) + dbFileName + ".db"

        try {
            fileInputStream = FileInputStream(fileName)
        } catch (e: FileNotFoundException) {
            Toast.makeText(context, R.string.toast_db_open_error, Toast.LENGTH_SHORT).show()
            return
        }

        driveResourceClient.createContents().continueWithTask { createFileIntentSender(it.result, fileInputStream) }
            .addOnFailureListener { Log.w(TAG,"Failed to create new contents.", it) }
    }
    private fun createFileIntentSender(driveContents: DriveContents?, file: FileInputStream): Task<Void> {
        val context = this.context as AppCompatActivity
        val outputStream = driveContents?.outputStream

        val readBuffer = ByteArray(1024)

        try {
            while(file.read(readBuffer,0,readBuffer.size) != -1) {
                outputStream?.write(readBuffer)
            }
        } catch (e: IOException) {
            Log.w(TAG, "Unable to write file contents.", e)
        }

        val filename = "InvestRecord_" + getToday() + ".db"
        val metadataChangeSet = MetadataChangeSet.Builder().setMimeType("application/x-sqlite3")
            .setTitle(filename).build()
        val createFileActivityOptions = CreateFileActivityOptions.Builder()
            .setInitialMetadata(metadataChangeSet).setInitialDriveContents(driveContents).build()
        return driveClient.newCreateFileActivityIntentSender(createFileActivityOptions)
            .continueWith {
                context.startIntentSenderForResult(it.result, REQUEST_CODE_CREATOR, null, 0,0,0)
                null
            }
    }
    fun downloadFileFromDrive() {
        val context = this.context as AppCompatActivity
        val mimeList = ArrayList<String>()
        mimeList.clear()
        mimeList.add("application/x-sqlite3")

        val openFileActivityOptions = OpenFileActivityOptions.Builder().setMimeType(mimeList).build()
        driveClient.newOpenFileActivityIntentSender(openFileActivityOptions).addOnSuccessListener {
            try {
                context.startIntentSenderForResult(it, REQUEST_CODE_CREATOR,null,0,0,0)
            } catch(e: IntentSender.SendIntentException) {
                Log.w(TAG, "Unable to send intent", e)
            }
        }.addOnFailureListener { Log.e(TAG, "Unable to create OpenFileActivityIntent", it) }
    }
    fun loadCurrentFile() {
        val file = currentDriveId.asDriveFile()

        driveResourceClient.getMetadata(file).continueWithTask {
            if (it.isSuccessful) driveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY)
            else Tasks.forException(it.exception!!)
        }.addOnSuccessListener {
            driveContents = it
            refreshDBFromCurrentFile()
        }.addOnFailureListener { Log.e(TAG, "Unable to retrieve file metadata and contennts", it) }
    }
    private fun refreshDBFromCurrentFile() {
        currentDriveId.let {
            try {
                val context = this.context as AppCompatActivity
                val directory = context.filesDir.toString()
                val fileName = directory.substring(0, directory.length - 5) + dbFileName + ".db"
                val dbFileOutputStream = FileOutputStream(fileName)
                val inputStream = driveContents.inputStream
                val writeBuffer = ByteArray(1024)

                try {
                    while (inputStream.read(writeBuffer, 0, writeBuffer.size) != -1) {
                        dbFileOutputStream.write(writeBuffer)
                    }
                } catch (e_io: IOException) {
                    Toast.makeText(context, R.string.toast_db_restore_error, Toast.LENGTH_SHORT).show()
                }
            } catch (e: FileNotFoundException) {
                Toast.makeText(context, R.string.toast_db_no_exist_error, Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(context, R.string.toast_db_restore_ok, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getToday(): String {
        val today = Calendar.getInstance()
        return String.format(
            Locale.getDefault(), "%04d-%02d-%02d", today.get(Calendar.YEAR),today.get(Calendar.MONTH)+1,today.get(
                Calendar.DATE))
    }

    fun signIn(code:Int) {
        val context = this.context as AppCompatActivity
        val mGoogleSignInClient = buildGoogleSignInClient()
        context.startActivityForResult(mGoogleSignInClient.signInIntent, code)
    }
    private fun buildGoogleSignInClient(): GoogleSignInClient {
        val context = this.context as AppCompatActivity
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(Drive.SCOPE_FILE).build()
        return GoogleSignIn.getClient(context, signInOptions)
    }
}