package com.example.utshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.utshop.R
import com.example.utshop.UserInf
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProductViewActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)
        database = Firebase.database.reference

        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productDescription = intent.getStringExtra("productDescription")
        val productAvailability = intent.getStringExtra("productAvailability")
        val email = intent.getStringExtra("ProductOwner")
        val productBuilding = intent.getStringExtra("ProductBuilding")

        val name:TextView = findViewById(R.id.editTextProductName)
        name.setText(productName)
        val price:TextView = findViewById(R.id.editTextProductPrice)
        price.setText(productPrice.toString())
        val availability:TextView = findViewById(R.id.switchProductAvailability)
        availability.setText(productAvailability)
        val description:TextView = findViewById(R.id.editTextProductDescription)
        description.setText(productDescription)
        val building:TextView = findViewById(R.id.BuildingField)
        building.setText(productBuilding)
        val seller:TextView = findViewById(R.id.sellerfield)
        val contact:TextView = findViewById(R.id.contactfield)
        getSeller(seller, contact, email.toString())
    }
    private fun getSeller(user:TextView, contact:TextView, email:String){
        database.child("Users").child(email).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userInf = snapshot.getValue(UserInf::class.java)
                if (userInf != null) {
                    val contacto = userInf.contacto
                    val username = userInf.user
                    user.setText(username)
                    contact.setText(contacto)
                }
            } else {
                Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Error al obtener informacion de user", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "fallo en la consulta firebase", Toast.LENGTH_SHORT).show()
         }
    }
}