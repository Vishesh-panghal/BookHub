package com.vishesh.bookhub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vishesh.bookhub.R
import com.vishesh.bookhub.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forgotLogIn.setOnClickListener {
            val intent = Intent(this@ForgotPassword, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.register.setOnClickListener {
            val intent = Intent(this@ForgotPassword, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}