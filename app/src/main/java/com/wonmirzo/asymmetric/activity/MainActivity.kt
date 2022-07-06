package com.wonmirzo.asymmetric.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import com.wonmirzo.asymmetric.R
import com.wonmirzo.asymmetric.databinding.ActivityMainBinding
import com.wonmirzo.asymmetric.model.Key
import com.wonmirzo.asymmetric.model.User
import com.wonmirzo.asymmetric.utils.Asymmetric
import com.wonmirzo.asymmetric.utils.Asymmetric.Companion.decryptMessage
import com.wonmirzo.asymmetric.utils.Asymmetric.Companion.encryptMessage
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnEncrypt.setOnClickListener {
                openEncryptActivity()
            }

            mainUI.setOnClickListener {
                hideKeyboard(etEmail)
                hideKeyboard(etPassword)
            }
        }
    }

    private fun openEncryptActivity() {
        val keyPairGenerator = Asymmetric()
        // Generate private and public key
        val privateKey: String =
            Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.encoded)
        val publicKey: String =
            Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.encoded)

        binding.apply {
            if (etEmail.text.toString().isEmpty() && etPassword.text.toString().isEmpty()) {
                Toast.makeText(this@MainActivity, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Intent(this@MainActivity, EncryptActivity::class.java).also { intent ->
                    val key = Key(privateKey, publicKey)
                    val password = encryptMessage(etPassword.text.toString(), publicKey)
                    val user = User(etEmail.text.toString(), password)

                    intent.putExtra("EXTRA_USER", user)
                    intent.putExtra("EXTRA_KEY", key)
                    startActivity(intent)
                }
            }
        }
    }

    private fun testAsymmetric() {
        val secretText = "Mushak"
        val keyPairGenerator = Asymmetric()

        // Generate private and public key
        val privateKey: String =
            Base64.getEncoder().encodeToString(keyPairGenerator.privateKey.encoded)
        val publicKey: String =
            Base64.getEncoder().encodeToString(keyPairGenerator.publicKey.encoded)
        Log.d("@@@", "Private Key: $privateKey")
        Log.d("@@@", "Public Key: $publicKey")
        // Encrypt secret key text using public key
        val encryptedValue = encryptMessage(secretText, publicKey)
        Log.d("@@@", "Encrypted Value: $encryptedValue")
        // Decrypt
        val decryptedText = decryptMessage(encryptedValue, privateKey)
        Log.d("@@@", "Decrypted output: $decryptedText")
    }

    private fun hideKeyboard(view: View) {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}