package com.example.totpapp.ui.qr_scanner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.totpapp.databinding.FragmentQrScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class QRScannerFragment : Fragment() {

    private var _binding: FragmentQrScannerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQrScannerBinding.inflate(inflater, container, false)
        scanFromFragment()
        return binding.root
    }



    private val fragmentLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled from fragment", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                context,
                "Scanned from fragment: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
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



}