package com.example.utshop.ui.slideshow
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.example.utshop.R


class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // Método para cargar los productos desde Firebase
    fun loadProducts(Building: String,email: String) {
        val productList = mutableListOf<Product>()
        val database = FirebaseDatabase.getInstance().reference
        val productsRef = database.child("Products").child(Building).child(email)

        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (productSnapshot in snapshot.children) {
                    val productName = productSnapshot.key ?: ""
                    val available = productSnapshot.child("availability").getValue(Boolean::class.java) ?: false
                    val price = productSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                    val description = productSnapshot.child("description").getValue(String::class.java) ?: ""
                    val product = Product(Building, R.drawable.colegio, productName, price, available, description)
                    productList.add(product)
                }
                _products.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer productos: ${error.message}")
            }
        })
    }
}
