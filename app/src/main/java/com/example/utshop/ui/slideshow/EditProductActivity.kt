package com.example.utshop.ui.slideshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import com.example.utshop.Inicio
import com.example.utshop.R
import com.example.utshop.ui.SP
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProductActivity : AppCompatActivity() {
    private lateinit var SP : SP
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)
        SP = SP(this)
        database = Firebase.database.reference

        // Obteniendo los datos pasados a esta actividad
        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productDescription = intent.getStringExtra("productDescription")
        val productAvailability = intent.getBooleanExtra("productAvailability", false)
        val productImageResource = intent.getIntExtra("productImageResource", 0)
        val productBuilding = intent.getStringExtra("ProductBuilding")

        // Asignando valores a los componentes del formulario
        val name = findViewById<EditText>(R.id.editTextProductName)
        name.isEnabled = false
        name.setText(productName)
        val price = findViewById<EditText>(R.id.editTextProductPrice)
        price.setText(productPrice.toString())
        val description = findViewById<EditText>(R.id.editTextProductDescription)
        description.setText(productDescription)
        val availability = findViewById<Switch>(R.id.switchProductAvailability)
        availability.isChecked = productAvailability
        val building = findViewById<EditText>(R.id.BuildingField)
        building.setText(productBuilding)

        val btnsbmt = findViewById<Button>(R.id.buttonSaveProduct)
        btnsbmt.setOnClickListener {
            update(name.text.toString(), price.text.toString().toDouble(), description.text.toString(), availability.isChecked, building.text.toString())
        }
    }
    private fun update(name:String, price:Double, description:String, availability:Boolean, Building:String){
        val sanitizedEmail = SP.email.toString().replace(".", "_").replace("@", "_")
        val datos = mapOf(
            "price" to price,
            "description" to description,
            "availability" to availability,
            "building" to Building
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
    private fun showAlert(message: String) {
        // Mostrar una alerta con el mensaje de error
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
