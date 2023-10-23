package com.example.totpapp.ui.enable_backups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.totpapp.R
import com.example.totpapp.databinding.FragmentEnableBackupsBinding
import android.util.Log
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.security.SecureRandom


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
            //get password
            var pwd=editTextPassword.text.toString()
            Log.d("PWD og", pwd)

            var hashResult=generateKey(pwd)

            // TODO (login functionality): verify a password against an encoded string representation

            // Write password and hashResult in Android KeyStore

        }

       return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val random = SecureRandom()
    private fun generateSalt(): ByteArray {
        // TODO: make sure salt is as described on paper
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    private fun generateKey(pwd: String): Argon2KtResult {
        val charset = Charsets.UTF_8
        val pwdByteArray = pwd.toByteArray(charset)
        Log.d("PWD ByteArray", pwdByteArray.contentToString())

        //generate salt
        val saltByteArray = generateSalt()
        Log.d("saltByteArray", saltByteArray.contentToString())

        // initialize Argon2Kt and load the native library
        val argon2Kt = Argon2Kt()

        // hash password
        val hashResult : Argon2KtResult = argon2Kt.hash(
            mode = Argon2Mode.ARGON2_I,
            password = pwdByteArray,
            salt = saltByteArray,
            tCostInIterations = 5,
            mCostInKibibyte = 65536
        )

        Log.d("Raw hash", hashResult.rawHashAsHexadecimal())
        Log.d("Encoded string",hashResult.encodedOutputAsString())

        return hashResult
    }
}