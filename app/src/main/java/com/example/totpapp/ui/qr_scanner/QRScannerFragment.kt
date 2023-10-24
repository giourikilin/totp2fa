package com.example.totpapp.ui.qr_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.totpapp.R
import com.example.totpapp.databinding.FragmentQrScannerBinding
import com.example.totpapp.storage.StorageManager
import com.example.totpapp.ui.home.HTOP
import com.example.totpapp.ui.home.HomeViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.json.JSONObject


class QRScannerFragment : Fragment() {

    private var _binding: FragmentQrScannerBinding? = null
    private lateinit var storageManager: StorageManager
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQrScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storageManager = StorageManager(requireContext())
        scanFromFragment()
    }


    private val fragmentLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled from fragment", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(context, "Scanned from fragment: " + result.contents, Toast.LENGTH_LONG).show()
            handleResult(result.contents)
        }

    }

    private fun scanFromFragment() {
        val options = ScanOptions()
        options.setBeepEnabled(false)
        options.setOrientationLocked(true)
        fragmentLauncher.launch(options)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleResult(result: String?) {
        if (result == null) {
            binding.labelText.text = getString(R.string.result_not_found)
            Toast.makeText(context, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
        } else {
            // If QRCode contains data.
            if (!extractValues(result)){
                binding.labelText.text = getString(R.string.result_not_found)
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
            binding.labelText.text = label
            binding.issuerText.text = issuer.value
            binding.secretText.text = secret.value

            val data = JSONObject()
            data.put("label", label)
            data.put("issuer", issuer.value.removePrefix("issuer="))
            data.put("secret", secret.value.removePrefix("secret="))
//            storageManager.saveToFile(data)
            createEntry(label,issuer.value.removePrefix("issuer="), secret.value.removePrefix("secret="))
        }catch (e: Exception){
            Toast.makeText(context, "This is not a valid code", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun createEntry(l: String, i: String, s: String) {
        val arraylist = ArrayList<String>()
        arraylist.add(l)
        arraylist.add(i)
        arraylist.add(s)
        val bundle = Bundle()
        bundle.putStringArrayList("fields", arraylist)
        findNavController().navigate(R.id.nav_home, bundle)
    }



}