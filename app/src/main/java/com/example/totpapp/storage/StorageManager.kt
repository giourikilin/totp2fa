package com.example.totpapp.storage

import android.content.Context
import android.util.Log
import com.example.totpapp.R
import org.json.JSONObject
import java.io.File

class StorageManager(private val context: Context) {
    private val filename: String = R.string.storage_file.toString()
    private var file: File

    init {
        context.filesDir.createNewFile()
        val file = File(context.filesDir, filename)
        if (!file.isFile ){
            this.file = file
            this.file.createNewFile()
        } else {
            this.file = file
        }
    }

    fun saveToFile(data: JSONObject) : Boolean{
        context.openFileOutput(this.filename, Context.MODE_PRIVATE).use{
            it.write(data.toString().toByteArray())
        }
        // TODO: add try/catch, check whether it already exists to replace
        // TODO: add encryption
        return true
    }

    fun readFromFile() : String {
        val contents = context.openFileInput(this.filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        Log.e("CONTENT", contents)
        // TODO: add decryption
        return contents
    }
}