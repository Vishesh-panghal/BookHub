package com.vishesh.bookhub.fragmentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.vishesh.bookhub.R
import com.vishesh.bookhub.adapter.DashboardRecyclerAdapter
import com.vishesh.bookhub.model.Book
import com.vishesh.bookhub.util.ConnectionManager
import com.android.volley.toolbox.JsonObjectRequest as JsonObjectRequest1

class DashboardFragmentation : Fragment() {
    // Variable Initialised
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var btnCheckInternet: Button
    val booklist =
        arrayListOf(
            "P.S I love you",
            "The Great Gatsby",
            "Madam Bovary",
            "War and Peace",
            "Lolita",
            "Middlemarch",
            "The Adventure of Huckleberry Finn",
            "Mobi-Dick",
            "The Lord Of the Rings"
        )
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>(
        Book(
            "ilu",
            "P.S. I love You",
            "Cecelia Aharon",
            "Rs. 299",
            "4.2",
            "R.drawable.ps_ily"
        ),
        Book(
            "The_Great_Gatsby",
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "Rs. 399",
            "4.5",
            "R.drawable.great_gatsby"
        ),
        Book(
            "Anna_Karenina",
            "Anna Karenina",
            "Leo Tolstoy",
            "Rs. 199",
            "5.0",
            "R.drawable.anna_kare"
        ),
        Book(
            "Madame_Bovary",
            "Madame Bovary",
            "Gustave Flaubert",
            "Rs. 500",
            "4.0",
            "R.drawable.madame"
        ),
        Book(
            "War_and_Peace",
            "War and Peace",
            "Leo Tolstoy",
            "Rs. 249",
            "3.8",
            "R.drawable.war_and_peace"
        ),
        Book(
            "Lolita",
            "Lolita",
            "Vladimir Nabokov",
            "Rs. 349",
            "5.0",
            "R.drawable.lolita"
        ),
        Book(
            "Middlemarch",
            "Middlemarch",
            "George Eliot",
            "Rs. 599",
            "4.2",
            "R.drawable.middlemarch"
        ),
        Book(
            "The_Adventures_of_Huckleberry_Finn",
            "The Adventures of Huckleberry Finn",
            "Mark Twain",
            "Rs. 699",
            "3.9",
            "R.drawable.adventures_finn"
        ),
        Book(
            "Moby-Dick",
            "Moby-Dick",
            "Herman Melville",
            "Rs. 499",
            "3.1",
            "R.drawable.moby_dick"
        ),
        Book(
            "The_Lord_of_the_Rings",
            "The Lord of the Rings",
            "J.R.R Tolkien",
            "Rs. 749",
            "5.0",
             "R.drawable.lord_of_rings"
        )
    )

    // onCreateView started..:- it extract layout  from layout file and then sets it to the activity's kotlin file.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater inflates a layout inside the fragmentation.
        val view = inflater.inflate(R.layout.fragment_dashboard_fragmentation, container, false)
        // Button to check iS Internet connected Or Not!
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        btnCheckInternet = view.findViewById(R.id.btnIsconnected)
        btnCheckInternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                // To create a DialogBox
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("App is Connected..:)")
                dialog.setPositiveButton("Ok") { text, listner -> //Do nothing
                }
                dialog.setNegativeButton("Cancel") { text, listner -> //Do nothing
                }
                dialog.create()
                dialog.show()
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("App is not Connected..:(")
                dialog.setPositiveButton("Ok") { text, listner -> //Do nothing
                }
                dialog.setNegativeButton("Cancel") { text, listner -> //Do nothing
                }
                dialog.create()
                dialog.show()
            }
        }
        layoutManager = LinearLayoutManager(activity)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/fetch_books"
        val jsonObjectRequest = object : JsonObjectRequest1(
            Request.Method.GET,
            url,
            null,
            Response.Listener() {
                // Here we will handle the response
                val success = it.getBoolean("Success")
                if (success) {
                    val data = it.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val bookJsonObject = data.getJSONObject(i)
                        val bookObject = Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image"),
                        )
                        bookInfoList.add(bookObject)
                        recyclerAdapter = DashboardRecyclerAdapter(
                            activity as Context,
                            bookInfoList
                        )// as word is used to typecasting
                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager
                        // to draw a line between objects.
                        recyclerDashboard.addItemDecoration(
                            DividerItemDecoration(
                                recyclerDashboard.context,
                                (layoutManager as LinearLayoutManager).orientation
                            )
                        )
                    }
                } else {
                    Toast.makeText(
                        activity as Context,
                        "Some error has occurred!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            Response.ErrorListener() {
                // Here we will handle the errors
                Log.d("my app", "Something went wrong!")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "db339e8abbedeb"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

        return view
    }

}