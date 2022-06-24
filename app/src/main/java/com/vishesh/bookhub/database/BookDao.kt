package com.vishesh.bookhub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun  deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBook(): List<BookEntity>

//    @Query(SELECT * FROM book WHERE book_id = )
//    fun getBookById(bookid: String): BookEntity


}