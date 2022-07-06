package com.vishesh.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vishesh.bookhub.R
import com.vishesh.bookhub.database.BookEntity

// To make a bridge between UI content and data of favourite Fragmentation (which is shown)
class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>) :
    RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    // To hold the view in a layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourites_recycler_adapter, parent, false)
        return FavouriteViewHolder(view)
    }

    // it is responsible to recycle and reusable of view holders.
    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.book_app_icon).into(holder.imgBookImage)
    }

    // It stores total number to items.
    override fun getItemCount(): Int {
        return bookList.size
    }
    // variable initialized
    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtBookName: TextView = view.findViewById(R.id.txtFavBookTitle)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtFavBookAuthor)
        val txtBookPrice: TextView = view.findViewById(R.id.txtFavBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtFavBookRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgFavBookImage)
        val content_11: LinearLayout = view.findViewById(R.id.llFavContent)
    }
}