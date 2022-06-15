package com.vishesh.bookhub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vishesh.bookhub.R
import com.vishesh.bookhub.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RegLogIn.setOnClickListener {
            val intent = Intent(this@Register, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.singUp.setOnClickListener {
            Toast.makeText(this@Register, "Account made successfully..!", Toast.LENGTH_LONG).show()
        }
    }
}