package com.vishesh.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vishesh.bookhub.R
import com.vishesh.bookhub.model.Book

// To make a bridge between UI content and data (which is shown)
class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() // link adapter class and Recycler view
{


    // To hold the view in a layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single_row, parent, false)
        return DashboardViewHolder(view)
    }

    // it is responsible to recycle and reusable of view holders.
    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookCost
        holder.txtBookRating.text = book.bookRating
        holder.txtBookImage.setImageResource(book.bookImage)
        holder.Content_11.setOnClickListener {
            Toast.makeText(context, " Clicked on ${holder.txtBookName.text}", Toast.LENGTH_SHORT)
                .show()
        }
    }
    // It stores total number to items.
    override fun getItemCount(): Int {
        return itemList.size
    }

    // Is is responsible to create initials view holders.
    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val txtBookImage: ImageView = view.findViewById(R.id.imgBookImage)
        val Content_11: LinearLayout = view.findViewById(R.id.content_11)
    }
}