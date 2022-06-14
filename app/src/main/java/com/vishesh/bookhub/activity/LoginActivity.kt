package com.vishesh.bookhub.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.vishesh.bookhub.R
import com.vishesh.bookhub.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var valmobno = "9306078820"
    var valpass = "champion"
    lateinit var shared_Preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        shared_Preferences = getSharedPreferences(getString(R.string.Book_hub_Preference), Context.MODE_PRIVATE)
        var isLoggedIn = shared_Preferences.getBoolean("isLoggedIn",false)
        setContentView(R.layout.activity_login)

        binding.register.setOnClickListener {
            Toast.makeText(
                this@LoginActivity,
                "Register page open",
                Toast.LENGTH_LONG
            ).show()
        }

    }
}