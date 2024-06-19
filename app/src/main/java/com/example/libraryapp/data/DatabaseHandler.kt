package com.example.libraryapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.libraryapp.ui.Book

val DATABASE_NAME = "books"
val TABLE_NAME = "favourites"
val COL_ID = "id"
val COL_TITLE = "title"
val COL_AUTHORS = "authors"

class DatabaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(150), " +
                COL_AUTHORS + " VARCHAR(150));"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(book: Books) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, book.title)
        cv.put(COL_AUTHORS, book.authors)
        var result = db.insert(TABLE_NAME, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFavoriteBooks(): List<Book> {
        val bookList = ArrayList<Book>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val titleIndex = result.getColumnIndex(COL_TITLE)
                val authorsIndex = result.getColumnIndex(COL_AUTHORS)

                if (titleIndex != -1 && authorsIndex != -1) {
                    val book = Book(
                        result.getString(titleIndex),
                        result.getString(authorsIndex)
                    )
                    bookList.add(book)
                }
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return bookList
    }


}