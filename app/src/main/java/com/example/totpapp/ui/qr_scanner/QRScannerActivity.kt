package com.example.totpapp.ui.qr_scanner

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.totpapp.MainActivity
import com.example.totpapp.R

class QRScannerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_qr_scanner)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent = Intent(this@QRScannerActivity, MainActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}