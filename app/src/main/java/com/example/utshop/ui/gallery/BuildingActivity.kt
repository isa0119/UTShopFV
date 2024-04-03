package com.example.utshop.ui.gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utshop.R
import com.example.utshop.databinding.ActivityBuilding1Binding
import com.example.utshop.databinding.ActivityBuildingBinding
import com.example.utshop.ui.ProductViewActivity
import com.example.utshop.ui.home.HomeProductAdapter
import com.example.utshop.ui.home.HomeViewModel

class BuildingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuildingBinding
    private lateinit var HomeproductViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Building= intent.getIntExtra("Building", 0)
        HomeproductViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        HomeproductViewModel.loadBuildingProducts(Building.toString())

        HomeproductViewModel.products.observe(this) { productList ->
            val adapter = HomeProductAdapter(productList,
                onContainerClicked = { product -> val intent = Intent(this, ProductViewActivity::class.java)
                    // Aquí puedes pasar datos extras si es necesario, por ejemplo:
                    intent.putExtra("productName", product.name)
                    intent.putExtra("productPrice", product.price)
                    intent.putExtra("productDescription", product.description)
                    intent.putExtra("productAvailability", product.isAvailable)
                    intent.putExtra("ProductBuilding", product.building)
                    intent.putExtra("ProductOwner", product.email)
                    startActivity(intent) })
            binding.productsRecyclerView.adapter = adapter
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
}