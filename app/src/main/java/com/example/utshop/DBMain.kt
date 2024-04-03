package com.example.utshop

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBMain (private val context: Context): SQLiteOpenHelper(context, DBNAME, null, DB_VERSION) {
    companion object{
        private const val DBNAME = "XRsize.db"
        private const val DB_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val users = ("CREATE TABLE usuarios("+
                "iduser INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "user TEXT, "+
                "pass TEXT, "+
                "email TEXT, "+
                "contacto TEXT, "+
                "edificio TEXT)")
        db?.execSQL(users)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val users = "DROP TABLE IF EXISTS usuarios"
        db?.execSQL(users)
        onCreate(db)
    }

    fun newuser(name: String, pass: String, email:String, contacto:String, edificio: String): Long{
        val valores = ContentValues().apply {
            put("user", name)
            put("pass", pass)
            put("email", email)
            put("contacto", contacto)
            put("edificio", edificio)
        }
        return writableDatabase.insert("usuarios", null, valores)
    }

    fun getUserId (email:String, pass:String):Cursor{
        val arr = arrayOf(email, pass)
        val query = readableDatabase.query("usuarios", null, "email = ? AND pass = ?", arr, null, null, null)
        return query
    }

    fun ifuserexist(user:String):Boolean{
        val arr = arrayOf(user)
        val query = readableDatabase.query("usuarios", null, "user = ?", arr, null, null, null)
        val user = query.count>0
        query.close()
        return user
    }

}