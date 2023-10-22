package com.example.totpapp.ui.qr_scanner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.totpapp.R
import com.example.totpapp.databinding.ActivityQrScannerBinding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class QRScannerActivity: AppCompatActivity() {

    private lateinit var binding: ActivityQrScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)

        if (ContextCompat.checkSelfPermission(this@QRScannerActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@QRScannerActivity, arrayOf(android.Manifest.permission.CAMERA), 123)
        }

        var scanner = IntentIntegrator(this@QRScannerActivity)
        scanner.setPrompt("Scan qr code")
        scanner.setOrientationLocked(true);
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                findViewById<TextView>(R.id.qr_data).setText(R.string.result_not_found)
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                findViewById<TextView>(R.id.qr_data).setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}