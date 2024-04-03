package com.example.utshop.ui.gallery

import ProductAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utshop.R
import com.example.utshop.databinding.ActivityBuilding1Binding

class Building1Activity : AppCompatActivity() {
    // Definición de Product como una clase interna de Building1Activity
    data class Product(
        val imageResource: Int,
        val name: String,
        val price: String,
        val description: String, // Agregar descripción
        val sellerName: String, // Agregar nombre del vendedor
        val phoneNumber: String // Agregar número telefónico
    )

    private lateinit var binding: ActivityBuilding1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuilding1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val productList = listOf(
            Product(R.drawable.colegio, "Producto 1", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Textodddddddddd","Pedro","9934567332"),
            Product(R.drawable.colegio, "Producto 3", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 4", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.colegio, "Producto 3", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 4", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.colegio, "Producto 3", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 4", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.colegio, "Producto 1", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.colegio, "Producto 3", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 4", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.colegio, "Producto 3", "$10.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 4", "$20.00", "Texto","Juan","9934567332"),
            Product(R.drawable.hamburguesa, "Producto 2", "$20.00", "Texto","Juan","9934567332"),
            // Añade más productos aquí
        )

        val adapter = ProductAdapter(this, productList)
        binding.productsRecyclerView.adapter = adapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
