package com.wonmirzo.asymmetric.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonmirzo.asymmetric.R
import com.wonmirzo.asymmetric.databinding.ActivityDecryptBinding
import com.wonmirzo.asymmetric.model.Key
import com.wonmirzo.asymmetric.model.User
import com.wonmirzo.asymmetric.utils.Asymmetric.Companion.decryptMessage

class DecryptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDecryptBinding
    private var key: Key? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDecryptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        key = intent.getSerializableExtra("EXTRA_KEY") as Key
        user = intent.getSerializableExtra("ENCRYPTED_USER") as User
        binding.apply {
            tvEmailResult.text = user!!.email
            tvPasswordResult.text = decryptMessage(user!!.password, key!!.privateKey)
        }
    }
}