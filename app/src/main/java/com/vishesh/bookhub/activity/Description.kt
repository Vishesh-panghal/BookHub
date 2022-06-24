package com.vishesh.bookhub.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.vishesh.bookhub.R
import com.vishesh.bookhub.databinding.ActivityDescriptionBinding
import com.vishesh.bookhub.util.ConnectionManager
import org.json.JSONObject

class Description : AppCompatActivity() {
    private var bookId: String? = "100"
    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.description_Toolbar)
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBarLayout.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"
        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(this@Description, "Some unexpected Error occurred!!", Toast.LENGTH_LONG)
                .show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(this@Description, "Some unexpected Error occurred!!", Toast.LENGTH_LONG)
                .show()
        }
        val queue = Volley.newRequestQueue(this@Description)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        if (ConnectionManager().checkConnectivity(this@Description)) {
            val jsonRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                Response.Listener {
                    try {
                        val success = it.getBoolean("success")
                        if (success) {
                            val bookJsonObject = it.getJSONObject("book_data")
                            binding.progressBarLayout.visibility = View.GONE
                            Picasso.get().load(bookJsonObject.getString("image"))
                                .error(R.drawable.book_app_icon).into(binding.imgBookImage)
                            binding.descriptionBookName.text = bookJsonObject.getString("name")
                            binding.descriptionAuthor.text = bookJsonObject.getString("author")
                            binding.descriptionBookRating.text = bookJsonObject.getString("rating")
                            binding.descriptionBookCost.text = bookJsonObject.getString("price")
                            binding.Description.text = bookJsonObject.getString("description")
                        } else {
                            Toast.makeText(
                                this@Description as Context,
                                "Some error has occurred!! $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@Description as Context,
                            "Some error has occurred!! in catch $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        this@Description as Context,
                        "Volley Error occurred!! $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "db339e8abbedeb"
                    return super.getHeaders()
                }
            }
            queue.add(jsonRequest)
        }else {
            val dialog = AlertDialog.Builder(this@Description)
            dialog.setTitle("Error")
            dialog.setMessage("App is not Connected..:(")
            dialog.setPositiveButton("Open Setting") { text, listner ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                this@Description.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(this@Description)
            }
            dialog.create()
            dialog.show()
        }


    }
}