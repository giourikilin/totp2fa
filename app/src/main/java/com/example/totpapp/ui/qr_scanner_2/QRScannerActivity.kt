package com.example.totpapp.ui.qr_scanner_2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.totpapp.R
import com.example.totpapp.databinding.ActivityQrScannerBinding
import com.example.totpapp.storage.StorageManager
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONObject


class QRScannerActivity: AppCompatActivity() {

    private lateinit var binding: ActivityQrScannerBinding
    private lateinit var storageManager: StorageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        this.storageManager = StorageManager(this@QRScannerActivity)

        // check for camera permissions / ask for them
        if (ContextCompat.checkSelfPermission(this@QRScannerActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@QRScannerActivity, arrayOf(android.Manifest.permission.CAMERA), 123)
        }

        // set up the scanner
        var scanner = IntentIntegrator(this@QRScannerActivity)
        scanner.setPrompt("Scan qr code")
        scanner.setOrientationLocked(true);

        // catch the scanner result
        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            // Handle the returned result
            if (result != null){
                handleResult(result.resultCode, result.data)
            } else {
                findViewById<TextView>(R.id.qr_data).setText(R.string.result_not_found)
            }
        }
        getContent.launch(scanner.createScanIntent())
    }

    /***
     * function to handle the result of the qr-scanner intent
     */
    private fun handleResult(resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult( resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                findViewById<TextView>(R.id.qr_data).setText(R.string.result_not_found)
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                if (!extractValues(result.contents)){
                    findViewById<TextView>(R.id.qr_data).setText(R.string.result_not_found)
                }
            }
        }
    }

    /***
     * extracts the secret, issuer and label from the uri encoded by the qrcode
     */
    private fun extractValues(url: String) : Boolean{
        try {
            val query = url.removePrefix("otpauth://totp/")
            val secret = ("secret=[A-Z, 0-9, a-z]*".toRegex()).find(query)!!
            val issuer = ("issuer=[A-Z, 0-9, a-z]*".toRegex()).find(query)!!
            val label = query.substringBefore("?")
            findViewById<TextView>(R.id.label_text).setText(label)
            findViewById<TextView>(R.id.issuer_text).setText(issuer.value)
            findViewById<TextView>(R.id.secret_text).setText(secret.value)

            val data = JSONObject()
            data.put("label", label)
            data.put("issuer", issuer.value.removePrefix("issuer="))
            data.put("secret", secret.value.removePrefix("secret="))
            this.storageManager.saveToFile(data)
        }catch (e: Exception){
            Toast.makeText(this, "This is not a valid code", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}