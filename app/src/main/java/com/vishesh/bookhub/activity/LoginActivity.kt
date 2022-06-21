package com.vishesh.bookhub.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.vishesh.bookhub.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var valmobno = "1"
    private var valpass = "1"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LogIn.setOnClickListener {
            val mobileNumber = binding.mobo.text.toString()
            val password = binding.pass.text.toString()
            if ((mobileNumber == valmobno) && (password == valpass)) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_LONG).show()
            }
        }
        binding.register.setOnClickListener {
            val intent = Intent(this@LoginActivity, Register::class.java)
            startActivity(intent)
            finish()
        }
        binding.forgotPass.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }
    }
}