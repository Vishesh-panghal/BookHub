package com.vishesh.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vishesh.bookhub.activity.Description

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val book_id: Int,
    @ColumnInfo (name = "book_name") val bookName:String,
    @ColumnInfo(name = "book_author") val bookAuthor:String,
    @ColumnInfo(name = "book_price") val bookPrice:String,
    @ColumnInfo(name = "book_rating") val bookRating:String,
    @ColumnInfo(name = "description") val description: String,
   @ColumnInfo(name = "book_image") val bookImage:String

)

