package com.example.totpapp.storage

import android.content.Context
import com.example.totpapp.R
import org.json.JSONObject
import java.io.File

class StorageManager(context: Context) {
    val filename: String = R.string.storage_file.toString()
    val context = context
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
        return true
    }

    fun readFromFile() : String {
        var contents = context.openFileInput(this.filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return contents
    }
}