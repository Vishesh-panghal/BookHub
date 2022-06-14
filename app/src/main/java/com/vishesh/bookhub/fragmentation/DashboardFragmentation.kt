package com.vishesh.bookhub.fragmentation

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.vishesh.bookhub.R
import com.vishesh.bookhub.adapter.DashboardRecyclerAdapter
import com.vishesh.bookhub.model.Book
import com.vishesh.bookhub.util.ConnectionManager

class DashboardFragmentation : Fragment() {


    // Variable Initialised
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var btnCheckInternet: Button
    val Booklist =
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
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>(
        Book(
            "P.S. I love You",
            "Cecelia Ahern",
            "Rs. 299",
            "4.5", R.drawable.ps_ily
        ),
        Book(
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            "Rs. 399",
            "4.1", R.drawable.great_gatsby
        ),
        Book(
            "Anna Karenina",
            "Leo Tolstoy",
            "Rs. 199",
            "4.3", R.drawable.anna_kare
        ),
        Book(
            "Madame Bovary",
            "Gustave Flaubert",
            "Rs. 500",
            "4.0", R.drawable.madame
        ),
        Book(
            "War and Peace",
            "Leo Tolstoy",
            "Rs. 249",
            "4.8", R.drawable.war_and_peace
        ),
        Book(
            "Lolita",
            "Vladimir Nabokov",
            "Rs. 349",
            "3.9", R.drawable.lolita
        ),
        Book(
            "Middlemarch",
            "George Eliot",
            "Rs. 599", "4.2",
            R.drawable.middlemarch
        ),
        Book(
            "The Adventures of Huckleberry Finn",
            "Mark Twain",
            "Rs. 699",
            "4.5",
            R.drawable.adventures_finn
        ),
        Book(
            "Moby-Dick",
            "Herman Melville",
            "Rs. 499", "4.5",
            R.drawable.moby_dick
        ),
        Book(
            "The Lord of the Rings",
            "J.R.R Tolkien",
            "Rs. 749",
            "5.0", R.drawable.lord_of_rings
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
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/fetch_books"
        return view
    }

}