package com.vishesh.bookhub.fragmentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.vishesh.bookhub.R
import com.vishesh.bookhub.adapter.DashboardRecyclerAdapter
import com.vishesh.bookhub.model.Book
import com.vishesh.bookhub.util.ConnectionManager
import org.json.JSONException

class DashboardFragmentation : Fragment() {
    // Variable Initialised
    private lateinit var recyclerDashboard: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>()

    // onCreateView started..:- it extract layout  from layout file and then sets it to the activity's kotlin file.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflater inflates a layout inside the fragmentation.
        val view = inflater.inflate(R.layout.fragment_dashboard_fragmentation, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        progressLayout = view.findViewById(R.id.progressBarLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE
        layoutManager = LinearLayoutManager(activity)
        // make a new request
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"
        // to check internet is connected or not
        if (ConnectionManager().checkConnectivity(requireContext())) {
            // GET request start
            val jsonRequest = object : JsonObjectRequest(
                Method.GET,
                url,
                null,
                Response.Listener {
                    // Here we will handle the response
                    try {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            Log.d("Main Activity", "API Data =  $data")
                            for (i in 0 until data.length()) {
                                val bookJsonObject = data.getJSONObject(i)
                                // data extract to API
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
                            }
                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Some error has occurred!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            "Some Error Occurred!! $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                Response.ErrorListener {
                    // Here we will handle the volley errors
                    Toast.makeText(
                        activity as Context,
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
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("App is not Connected..:(")
            dialog.setPositiveButton("Open Setting") { text, listner ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }

}