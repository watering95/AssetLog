package com.example.watering.assetlog.model

import android.content.Context
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.watering.assetlog.R
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
    private val dbFileName = "AssetLog.db"
    private val tag = "AssetLog"
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

    fun deleteDBFile() = try {
        context.getDatabasePath(dbFileName).delete()
        Toast.makeText(context, R.string.toast_db_del_ok, Toast.LENGTH_SHORT).show()
    } catch (e: FileNotFoundException) {
        Toast.makeText(context, R.string.toast_db_del_error, Toast.LENGTH_SHORT).show()
    }
    fun saveFileToDrive(date:String) {
        val context = this.context as AppCompatActivity
        val fileInputStream: FileInputStream
        val fileName = context.getDatabasePath(dbFileName)

        try {
            fileInputStream = FileInputStream(fileName)
        } catch (e: FileNotFoundException) {
            Toast.makeText(context, R.string.toast_db_open_error, Toast.LENGTH_SHORT).show()
            return
        }

        driveResourceClient.createContents().continueWithTask { createFileIntentSender(it.result, fileInputStream, date) }
            .addOnFailureListener { Log.w(tag,"Failed to create new contents.", it) }
    }
    private fun createFileIntentSender(driveContents: DriveContents?, file: FileInputStream, date: String): Task<Void> {
        val context = this.context as AppCompatActivity
        val outputStream = driveContents?.outputStream

        val readBuffer = ByteArray(1024)

        try {
            while(file.read(readBuffer,0,readBuffer.size) != -1) {
                outputStream?.write(readBuffer)
            }
        } catch (e: IOException) {
            Log.w(tag, "Unable to write file contents.", e)
        }

        val filename = "AssetLog_$date.db"
        val metadataChangeSet = MetadataChangeSet.Builder().setMimeType("application/x-sqlite3")
            .setTitle(filename).build()
        val createFileActivityOptions = CreateFileActivityOptions.Builder()
            .setInitialMetadata(metadataChangeSet).setInitialDriveContents(driveContents).build()
        return driveClient.newCreateFileActivityIntentSender(createFileActivityOptions)
            .continueWith {
                context.startIntentSenderForResult(it.result,
                    REQUEST_CODE_CREATOR, null, 0,0,0)
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
                context.startIntentSenderForResult(it,
                    REQUEST_CODE_OPENER,null,0,0,0)
            } catch(e: IntentSender.SendIntentException) {
                Log.w(tag, "Unable to send intent", e)
            }
        }.addOnFailureListener { Log.e(tag, "Unable to create OpenFileActivityIntent", it) }
    }
    fun loadCurrentFile() {
        val file = currentDriveId.asDriveFile()

        driveResourceClient.getMetadata(file).continueWithTask {
            if (it.isSuccessful) driveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY)
            else Tasks.forException(it.exception!!)
        }.addOnSuccessListener {
            driveContents = it
            refreshDBFromCurrentFile()
        }.addOnFailureListener { Log.e(tag, "Unable to retrieve file metadata and contents", it) }
    }
    private fun refreshDBFromCurrentFile() {
        currentDriveId.let {
            try {
                val context = this.context as AppCompatActivity
                val file = context.getDatabasePath(dbFileName)
                if(!file.exists()) {
                    file.parentFile.mkdir()
                    file.createNewFile()
                }
                val dbFileOutputStream = FileOutputStream(file)
                val inputStream = driveContents.inputStream
                val writeBuffer = ByteArray(1024)

                try {
                    while (inputStream.read(writeBuffer, 0, writeBuffer.size) != -1) {
                        dbFileOutputStream.write(writeBuffer)
                    }
                } catch (e_io: IOException) {
                    Toast.makeText(context, R.string.toast_db_restore_error, Toast.LENGTH_SHORT).show()
                    return
                }
            } catch (e: FileNotFoundException) {
                Toast.makeText(context, R.string.toast_db_no_exist_error, Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(context, R.string.toast_db_restore_ok, Toast.LENGTH_SHORT).show()
        }
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