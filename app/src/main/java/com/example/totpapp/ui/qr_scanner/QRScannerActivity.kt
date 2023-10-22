package com.example.totpapp.ui.qr_scanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.totpapp.R
import com.example.totpapp.databinding.ActivityQrScannerBinding
import com.google.zxing.integration.android.IntentIntegrator

class QRScannerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        if (ContextCompat.checkSelfPermission(this@QRScannerActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@QRScannerActivity, arrayOf(android.Manifest.permission.CAMERA), 123)
        }

        var scanner = IntentIntegrator(this@QRScannerActivity)
        scanner.setPrompt("Scan qr code")
        scanner.setOrientationLocked(true);
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}