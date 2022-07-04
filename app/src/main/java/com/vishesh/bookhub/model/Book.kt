package com.vishesh.bookhub.model

import android.widget.ImageView

data class Book(
    val bookId: String,
    val bookName: String,
    val bookAuthor: String,
    val bookRating: String,
    val bookCost: String,
    val bookImage: String
)