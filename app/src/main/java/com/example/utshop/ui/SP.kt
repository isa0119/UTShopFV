package com.example.utshop.ui

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SP(context: Context) {
    private val SP : SharedPreferences = context.getSharedPreferences("controldesesiones", Context.MODE_PRIVATE)
    private val editor:  SharedPreferences.Editor = SP.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_CONTACT = "contact"
        private const val KEY_BUILD = "building"
        private const val KEY_USER_ID = "userid"
        private const val KEY_MONTH = "mesInt"
        private const val KEY_DAY = "diaInt"
        private const val KEY_YEAR = "añoInt"
        private const val KEY_MONTH_NAME ="mesString"
        private const val KEY_ENABLE_FILTER = "filterenable"
        private const val KEY_FILTER_TYPE = "FilterType"
        private const val KEY_ID_REGISTER_SHOW_REGISTERS ="registroID"
    }

    var username: String?
        get() = SP.getString(KEY_USER, "no hay usuario almacenado")
        set(value) = editor.putString(KEY_USER, value).apply()
    var email: String?
        get() = SP.getString(KEY_EMAIL, "no hay email almacenado")
        set(value) = editor.putString(KEY_EMAIL, value).apply()
    var contact: String?
        get() = SP.getString(KEY_CONTACT, "no hay contacto almacenado")
        set(value) = editor.putString(KEY_CONTACT, value).apply()
    var building: String?
        get() = SP.getString(KEY_BUILD, "no hay edificio almacenado")
        set(value) = editor.putString(KEY_BUILD, value).apply()
    var isLoggedIn: Boolean
        get() = SP.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = editor.putBoolean(KEY_IS_LOGGED_IN, value).apply()
    var userid: Int
        get() = SP.getInt(KEY_USER_ID, -1) // -1 es un valor predeterminado en caso de que no se haya guardado ningún ID
        set(value) = editor.putInt(KEY_USER_ID, value).apply()
    var mesInt: Int
        get() = SP.getInt(KEY_MONTH, month())
        set(value) = editor.putInt(KEY_MONTH, value).apply()
    var mesString: String
        get() = SP.getString(KEY_MONTH_NAME, monthname())?: monthname()
        set(value) = editor.putString(KEY_MONTH_NAME, value).apply()
    var filterenable: Boolean
        get() = SP.getBoolean(KEY_ENABLE_FILTER, false)
        set(value) = editor.putBoolean(KEY_ENABLE_FILTER, value).apply()
    var FilterType: String?
        get() = SP.getString(KEY_FILTER_TYPE, null)
        set(value) = editor.putString(KEY_FILTER_TYPE, value).apply()
    var diaInt: Int
        get() = SP.getInt(KEY_DAY, day())
        set(value) = editor.putInt(KEY_DAY, value).apply()
    var añoInt: Int
        get() = SP.getInt(KEY_YEAR, year())
        set(value) = editor.putInt(KEY_YEAR, value).apply()
    var registroID: Int
        get() = SP.getInt(KEY_ID_REGISTER_SHOW_REGISTERS, -1)
        set(value) = editor.putInt(KEY_ID_REGISTER_SHOW_REGISTERS, value).apply()


    fun clearSession() {
        editor.clear().apply()
    }
    fun month():Int{
        val fecha = Calendar.getInstance()
        return fecha.get(Calendar.MONTH) + 1
    }
    fun day():Int{
        val fecha = Calendar.getInstance()
        return fecha.get(Calendar.DAY_OF_WEEK)
    }
    fun year():Int{
        val fecha = Calendar.getInstance()
        return fecha.get(Calendar.YEAR)
    }

    fun monthname():String{
        val fecha = Calendar.getInstance()
        return SimpleDateFormat("MMMM", Locale.getDefault()).format(fecha.time)
    }

}