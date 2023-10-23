package com.example.totpapp.ui.enable_backups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.totpapp.R
import com.example.totpapp.databinding.FragmentEnableBackupsBinding

class EnableBackupsFragment : Fragment() {

    private var _binding: FragmentEnableBackupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val enableBackupsViewModel =
                ViewModelProvider(this).get(EnableBackupsViewModel::class.java)

        _binding = FragmentEnableBackupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEnableBackups
        enableBackupsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        //this use returned initially
        val view =root

        // get username & password
        val editTextUsername: EditText = view.findViewById(R.id.editTextUsername)
        val editTextPassword:EditText=view.findViewById(R.id.editTextPassword)
        val buttonRegister: Button =view.findViewById(R.id.buttonRegister)

        //using button click listener
        buttonRegister.setOnClickListener {
            //getting username& password
            val pwd=editTextPassword.text.toString()
        }

       return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}