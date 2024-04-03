package com.example.utshop.ui.slideshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.utshop.Inicio
import com.example.utshop.MainActivity
import com.example.utshop.R
import com.example.utshop.ui.SP
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewProductActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var SP : SP
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        SP = SP(this)
        database = Firebase.database.reference

        val name:EditText = findViewById(R.id.editTextProductName)
        val price: EditText = findViewById(R.id.editTextProductPrice)
        val availability = true
        val description: EditText = findViewById(R.id.editTextProductDescription)
        val btnsbmt: Button = findViewById(R.id.buttonSaveProduct)

        btnsbmt.setOnClickListener {
            val validate = validation(name.text.toString(), price.text.toString().toDouble(), description.text.toString())
            if (validate == "confirm"){
                addProduct(SP.email.toString(), name.text.toString(), price.text.toString().toDouble(), description.text.toString(), availability)
            }else{
                showAlert(validate)
            }
        }
    }
    private fun validation(name:String, price: Double, description: String):String{
        if (name.isNotEmpty() || description.isNotEmpty()){
            if (price > 0){
                return "confirm"
            }else{
                return "precio debe ser mayor que 0"
            }
        }else{
            return "porfavor, rellene todos los campos"
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
    private fun addProduct(email: String, name: String, price:Double, description: String, availability: Boolean){
        val sanitizedEmail = email.replace(".", "_").replace("@", "_")
        val datos = mapOf(
            "price" to price,
            "description" to description,
            "availability" to availability,
            "building" to SP.building
        )
        database.child("Products").child(SP.building.toString()).child(sanitizedEmail).child(name).setValue(datos).addOnSuccessListener {
            val intent = Intent(this, Inicio::class.java)
            intent.putExtra("navigateToProductFragment", true)
            startActivity(intent)
            finish()
        }.addOnFailureListener { exception ->
            val errorMessage = exception.message ?: "Error desconocido al registrar información del producto"
            showAlert(errorMessage)
        }
    }
}