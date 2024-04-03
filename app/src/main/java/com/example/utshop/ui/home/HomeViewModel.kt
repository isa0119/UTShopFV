package com.example.utshop.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.utshop.R
import com.example.utshop.ui.slideshow.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {
    private val _products = MutableLiveData<List<HomeProduct>>()
    val products: LiveData<List<HomeProduct>> = _products

    fun loadProducts() {
        val productList = mutableListOf<HomeProduct>()
        val database = FirebaseDatabase.getInstance().reference
        val edificioRef = database.child("Products")

        edificioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (edificioSnapshot in snapshot.children) {
                    for (userSnapshot in edificioSnapshot.children) {
                        val user = userSnapshot.key ?: ""
                        for (productSnapshot in userSnapshot.children) {
                            val productName = productSnapshot.key ?: ""
                            val available = productSnapshot.child("availability").getValue(Boolean::class.java) ?: false
                            val price = productSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                            val description = productSnapshot.child("description").getValue(String::class.java) ?: ""
                            val building = productSnapshot.child("building").getValue(String::class.java) ?:""
                            var availability = "disponible"
                            if (!available){
                                availability = "agotado"
                            }
                            val product = HomeProduct(building,R.drawable.colegio, productName, price, availability, description, user)
                            productList.add(product)
                        }
                    }
                }
                _products.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer productos: ${error.message}")
            }
        })
    }
    fun loadBuildingProducts(building:String){
        val productList = mutableListOf<HomeProduct>()
        val database = FirebaseDatabase.getInstance().reference
        val edificioRef = database.child("Products").child(building)

        edificioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.key ?: ""
                        for (productSnapshot in userSnapshot.children) {
                            val productName = productSnapshot.key ?: ""
                            val available = productSnapshot.child("availability").getValue(Boolean::class.java) ?: false
                            val price = productSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                            val description = productSnapshot.child("description").getValue(String::class.java) ?: ""
                            val building = productSnapshot.child("building").getValue(String::class.java) ?:""
                            var availability = "disponible"
                            if (!available){
                                availability = "agotado"
                            }
                            val product = HomeProduct(building,R.drawable.colegio, productName, price, availability, description, user)
                            productList.add(product)
                        }
                    }
                _products.value = productList
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer productos: ${error.message}")
            }
        })
    }
}