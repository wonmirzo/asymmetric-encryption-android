package com.wonmirzo.asymmetric.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonmirzo.asymmetric.databinding.ActivityEncryptBinding
import com.wonmirzo.asymmetric.model.Key
import com.wonmirzo.asymmetric.model.User

class EncryptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncryptBinding
    private var key: Key? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncryptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        key = intent.getSerializableExtra("EXTRA_KEY") as Key
        user = intent.getSerializableExtra("EXTRA_USER") as User

        binding.apply {
            tvEmailResult.text = user!!.email
            tvPasswordResult.text = user!!.password

            btnDecrypt.setOnClickListener {
                openDecryptActivity()
            }
        }
    }

    private fun openDecryptActivity() {
        Intent(this@EncryptActivity, DecryptActivity::class.java).also { intent ->
            intent.putExtra("EXTRA_KEY", key)
            intent.putExtra("ENCRYPTED_USER", user)
            startActivity(intent)
        }
    }
}