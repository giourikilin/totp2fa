package com.example.totpapp.ui.enable_backups
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class KeyStoreManager(context: Context) {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "my_secure_prefs_filename",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveValue(tag:String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(tag, value)
        editor.apply()
    }

    fun getValue(tag:String): String? {
        return sharedPreferences.getString(tag, null)
    }
}
