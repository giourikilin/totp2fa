package com.example.totpapp.ui.enable_backups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnableBackupsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is enable backups Fragment"
    }
    val text: LiveData<String> = _text
}