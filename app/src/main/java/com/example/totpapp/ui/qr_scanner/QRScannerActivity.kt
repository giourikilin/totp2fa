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
import java.net.URL
import java.net.URLDecoder


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
                verifyFormat(result.contents)
                findViewById<TextView>(R.id.qr_data).setText(result.contents)

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun verifyFormat(url: String) : Boolean{
        try {
            val query = url.removePrefix("otpauth://totp/")
            val secret = ("secret=[A-Z, 0-9, a-z]*".toRegex()).find(query)!!
            val issuer = ("issuer=[A-Z, 0-9, a-z]*".toRegex()).find(query)!!
            //Toast.makeText(this, issuer.value + "", Toast.LENGTH_LONG).show()
            /*val reg = (issuer.value + ":.*?").toRegex()
            if (reg!= null){
                val accountName = (reg).find(query)!!
                Toast.makeText(this, "not null", Toast.LENGTH_LONG).show()
            }

            val accountName : MatchResult
            if (issuer != null){
                 accountName = ((issuer.value + "[:,%3A].?").toRegex()).find(query)!!
            } else {
                accountName = (".?").toRegex().find(query)!!
            }*/
            Toast.makeText(this, secret.value + " " + issuer.value, Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
        //Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
        return true
    }
}