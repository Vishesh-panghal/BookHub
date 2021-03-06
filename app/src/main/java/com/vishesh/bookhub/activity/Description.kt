package com.vishesh.bookhub.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.vishesh.bookhub.R
import com.vishesh.bookhub.database.BookDatabase
import com.vishesh.bookhub.database.BookEntity
import com.vishesh.bookhub.databinding.ActivityDescriptionBinding
import com.vishesh.bookhub.util.ConnectionManager
import org.json.JSONObject

class Description : AppCompatActivity() {
    // variable declare
    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var toolbar: Toolbar

    var bookId: String? = "100"

    // onCreate start
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.description_Toolbar)
        // to show progress bar
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBarLayout.visibility = View.VISIBLE

        // page title
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        // to store the book_id if it is not null from intent(DashboaedRecyclewrAdapter)
        if (intent != null) {
            Log.d("bookId", "intent = ${bookId.toString()}")
            bookId = intent.getStringExtra("book_id")
//            Log.d("bookId","Book_Id = $bookId")
        } else {
            finish()
            Toast.makeText(
                this@Description,
                "Some unexpected Error occurred!! in bookId",
                Toast.LENGTH_LONG
            ).show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@Description,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }
        // make a new request
        val queue = Volley.newRequestQueue(this@Description)
        val url = "http://13.235.250.119/v1/book/get_book/"

        // to sent data in POST request
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        // to check internet is connected or not
        if (ConnectionManager().checkConnectivity(this@Description)) {
            // Post request start
            val jsonRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {

                    //try 'n' catch start
                    try {

                        // to store the success key for response listner
                        val success = it.getBoolean("success")
                        if (success) {
                            // data retrieve to API
                            val bookJsonObject = it.getJSONObject("book_data")
                            binding.progressBarLayout.visibility = View.GONE

                            val bookImageUrl = bookJsonObject.getString("image")
                            Picasso.get().load(bookJsonObject.getString("image"))
                                .error(R.drawable.book_app_icon).into(binding.imgBookImage)
                            binding.descriptionBookName.text = bookJsonObject.getString("name")
                            binding.descriptionAuthor.text = bookJsonObject.getString("author")
                            binding.descriptionBookRating.text = bookJsonObject.getString("rating")
                            binding.descriptionBookCost.text = bookJsonObject.getString("price")
                            binding.Description.text = bookJsonObject.getString("description")

                            val bookEntity = BookEntity(
                                bookId?.toInt() as Int,
                                binding.descriptionBookName.text.toString(),
                                binding.descriptionAuthor.text.toString(),
                                binding.descriptionBookCost.text.toString(),
                                binding.descriptionBookRating.text.toString(),
                                binding.Description.text.toString(),
                                bookImageUrl
                            )
                            val chckFav = DBAsynTask(applicationContext, bookEntity, 1).execute()
                            val isFav = chckFav.get()
                            if (isFav) {
                                binding.descBtn.text = "Remove from Favourites"
                                val favColor = ContextCompat.getColor(
                                    applicationContext,
                                    R.color.fav_color
                                )
                                binding.descBtn.setBackgroundColor(favColor)
                            } else {
                                binding.descBtn.text = "Add to Favourites"
                                val noFavColor =
                                    ContextCompat.getColor(
                                        applicationContext,
                                        R.color.purple_700
                                    )
                                binding.descBtn.setBackgroundColor(noFavColor)
                            }
                            binding.descBtn.setOnClickListener {
                                if (!DBAsynTask(
                                        applicationContext,
                                        bookEntity,
                                        1
                                    ).execute().get()
                                ) {
                                    val async =
                                        DBAsynTask(applicationContext, bookEntity, 2).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@Description,
                                            "Book added to favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        binding.descBtn.text =
                                            getString(R.string.Remove_from_favourites)
                                        val favColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.purple_700
                                        )
                                        binding.descBtn.setBackgroundColor(favColor)
                                    } else {
                                        Toast.makeText(
                                            this@Description,
                                            "Some error occurred!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val async =
                                        DBAsynTask(applicationContext, bookEntity, 3).execute()
                                    val result = async.get()

                                    if (result) {
                                        Toast.makeText(
                                            this@Description,
                                            "Book removed from favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        binding.descBtn.text = getString(R.string.Add_to_favourites)
                                        val noFavColor =
                                            ContextCompat.getColor(
                                                applicationContext,
                                                R.color.purple_700
                                            )
                                        binding.descBtn.setBackgroundColor(noFavColor)
                                    } else {
                                        Toast.makeText(
                                            this@Description,
                                            "Some error occurred!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                        } else {
                            // error in retrieve data
                            Toast.makeText(
                                this@Description,
                                "Some error has occurred!! 'n' try else $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@Description,
                            "Some error has occurred!! in catch $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                    Response.ErrorListener {
                        // error with Volley library
                        Toast.makeText(
                            this@Description,
                            "Volley Error occurred!! $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    // headers (content-type)'n'(token)
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "db339e8abbedeb"
                        return headers
                    }
                }
            // it mandatory to add this line to make an request
            queue.add(jsonRequest)
        } else {
            // If internet is not connected
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

    // We use worker thread to perform read/write operations
    class DBAsynTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
            Mode 1 -> Check DB if the book is favourite or not
            Mode 2 -> Save the book into DB as favourite
            Mode 3 -> Remove the favourite book
            * */
        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    // Check DB if the book is favourites or not
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null
                }
                2 -> {
                    // Add to favourites
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    // Remove from favourites
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }

    }
}