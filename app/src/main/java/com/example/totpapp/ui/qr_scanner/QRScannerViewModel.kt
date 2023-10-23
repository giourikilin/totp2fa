package com.example.totpapp.ui.qr_scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QRScannerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is QR fragment"
    }
    val text: LiveData<String> = _text
}