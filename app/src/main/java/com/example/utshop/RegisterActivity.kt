package com.example.utshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.utshop.ui.SP
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    var items = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    private lateinit var sp : SP
    private lateinit var db : DBMain
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Log.i("dato","On create")
        sp = SP(this)
        db = DBMain(this)
        database = Firebase.database.reference

        var autoCompletetext:AutoCompleteTextView = findViewById(R.id.autocompletetext)
        var arrayadapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.list_item, items)
        val emailfield: EditText = findViewById(R.id.emailField)
        val user:EditText = findViewById(R.id.nameEditText)
        val contact:EditText = findViewById(R.id.contactoField)
        val passfield: EditText = findViewById(R.id.passwordEditText)
        val confpassfield: EditText = findViewById(R.id.passwordConfirmEditText)
        val sbmt: Button = findViewById(R.id.registerSubmitButton)
        var building:String = ""

        autoCompletetext.setAdapter(arrayadapter)
        autoCompletetext.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            building = item
            Toast.makeText(this, building, Toast.LENGTH_SHORT).show()
        }
        sbmt.setOnClickListener {
            var validate = Validations(emailfield, passfield, confpassfield, user, contact, building)
            if(validate == "confirm"){
                val idreg = db.newuser(user.text.toString(), passfield.text.toString(), emailfield.text.toString(), contact.text.toString(), building)
                newuser(user.text.toString(), emailfield.text.toString(), passfield.text.toString(), contact.text.toString(), building)
                if(idreg != -1L){
                    Toast.makeText(this, "registro exitoso", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "error al registrar", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, validate, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun Validations(email: EditText, pass: EditText, confpass: EditText, user: EditText, contact:EditText, building:String): String {
        val username = user.text.toString().trim()
        val password = pass.text.toString().trim()
        val confirmPassword = confpass.text.toString().trim()
        val email = email.text.toString().trim()
        val contact = contact.text.toString().trim()

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || contact.isEmpty() || building == "") {
            return "Por favor, rellena todos los campos."
        } else if (password != confirmPassword) {
            return "Las contraseñas no coinciden."
        } else {
            /*val exists = db.ifuserexist(username)
            if (exists) {
                return "Usuario no válido."
            } else {*/
                return "confirm"
        }
    }
    private fun newuser(user:String, email: String, pass: String, contacto: String, Edificio :String) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                ifexistFdb(email) { exists ->
                    if (exists) {
                        showAlert("correo electronico ya en uso, introduce uno diferente")
                    } else {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val datos = mapOf(
                                    "user" to user,
                                    "contacto" to contacto,
                                    "edificio" to Edificio
                                )
                                val sanitizedEmail = email.replace(".", "_").replace("@", "_")
                                database.child("Users").child(sanitizedEmail).setValue(datos).addOnSuccessListener {
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener {
                                    val errorMessage = task.exception?.message ?: "Error desconocido al registrar informacion de usuario"
                                    showAlert(errorMessage)
                                }
                            } else {
                                val errorMessage = task.exception?.message ?: "Error desconocido al registrar el usuario"
                                showAlert(errorMessage)
                            }
                        }
                    }
                }
            } else {
                showAlert("El formato del correo electrónico es inválido")
            }
        } else {
            showAlert("Por favor, rellena todos los campos")
        }
    }

    private fun showAlert(message: String) {
        // Mostrar una alerta con el mensaje de error
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun ifexistFdb(email: String, callback: (Boolean) -> Unit) {
        val sanitizedEmail = email.replace(".", "_").replace("@", "_")
        database.child("users").child(sanitizedEmail).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val exists = snapshot.exists()
                callback(exists)
            }
            override fun onCancelled(error: DatabaseError) {
                showAlert("error, enviado al onCancelled")
                callback(false)
            }
        })
    }


    /*private fun newuser(email:String, pass:String){
        if (email.isNotEmpty() && pass.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
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
    }*/
    /*fun firebasenewuser(user: EditText, pass: EditText){
        var user = user.text.toString()
        var pass = pass.text.toString()
        database.child("users").child(user).setValue(pass)
    }
    override fun onStart() {
        super.onStart()
        Log.i("cicloVida", "on start")
    }

    override fun onResume() {
        super.onResume()
        Log.i("cicloVida","On resume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("dato","On pause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("dato","On stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("dato","On destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("dato","On restart")
    }*/
}



