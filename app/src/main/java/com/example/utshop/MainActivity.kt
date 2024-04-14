package com.example.utshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.utshop.ui.SP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var db : DBMain
    private lateinit var database: DatabaseReference
    private lateinit var SP: SP
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenSplash.setKeepOnScreenCondition{false}
        SP=SP(this)
        db = DBMain(this)
        database = Firebase.database.reference
        Log.i("cicloVida","ingresa On create")
        if (SP.isLoggedIn){
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish()
        }
        // Botón de registro ya existente
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón de inicio de sesión para navegar a InicioActivity
        val loginButton = findViewById<Button>(R.id.loginButton)
        val email: EditText = findViewById(R.id.emailEditText)
        val pass: EditText = findViewById(R.id.passwordEditText)
        loginButton.setOnClickListener {
            login(email.text.toString(), pass.text.toString())
    }
    /*fun validatelogin(user:String, pass:String):Boolean{
        if(user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "porfavor llene todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }else{
            val ifexist = db.loginctrl(user, pass)
            if (ifexist){
                return true
            }else{
                Toast.makeText(this, "no se encontro el usuario", Toast.LENGTH_SHORT).show()
                return false
            }
        }
    }
    fun firebaseifexist(user: String, pass: String, callback: (Boolean) -> Unit) {
        val userRef = database.child("users").child(user)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var userExists = false // Variable para almacenar el resultado

                if (dataSnapshot.exists()) {
                    val passwordFromDB = dataSnapshot.getValue(String::class.java)
                    if (passwordFromDB == pass) {
                        userExists = true
                    }
                }

                // Llamar al callback con el resultado
                callback(userExists)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(false)
            }
        })
    }
    override fun onStart() {
        super.onStart()
        Log.i("cicloVida", "on start")
        Toast.makeText(this, "on start login", Toast.LENGTH_LONG).show()

    }

    override fun onResume() {
        super.onResume()
        Log.i("cicloVida","On resume")
        Toast.makeText(this, "on resume login", Toast.LENGTH_LONG).show()

    }

    override fun onPause() {
        super.onPause()
        Log.i("cicloVida","On pause")
        Toast.makeText(this, "on pause login", Toast.LENGTH_LONG).show()

    }

    override fun onStop() {
        super.onStop()
        Log.i("cicloVida","On stop")
        Toast.makeText(this, "on stop login", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("cicloVida","On destroy")
        Toast.makeText(this, "on destroy login", Toast.LENGTH_LONG).show()

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("cicloVida","On restart")
        Toast.makeText(this, "on restart login", Toast.LENGTH_LONG).show()

    }*/
}
    private fun login(email:String, pass:String){
        if (email.isNotEmpty() && pass.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    SessionCtrl(email, pass)
                    SP.email = email
                    val intent = Intent(this, Inicio::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    showAlert()
                }
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("error al autenticar")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun SessionCtrl(email: String, pass: String){
        SP.isLoggedIn = true
        var userdata = db.getUserId(email,pass)
        if (userdata.moveToFirst()) {
            SP.userid = userdata.getInt(userdata.getColumnIndex("iduser"))
            SP.username = userdata.getString(userdata.getColumnIndex("user"))
            SP.contact = userdata.getString(userdata.getColumnIndex("contacto"))
            SP.building = userdata.getString(userdata.getColumnIndex("edificio"))
        } else {
            AlertLocalError()
        }
        userdata.close()
    }
    private fun AlertLocalError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("error")
        builder.setMessage("no hay datos almacenados en la base de datos local")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}





