package com.example.utshop.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utshop.databinding.FragmentHomeBinding
import com.example.utshop.ui.ProductViewActivity
import com.example.utshop.ui.slideshow.EditProductActivity
import com.example.utshop.ui.slideshow.ProductAdapter
import com.example.utshop.ui.slideshow.ProductViewModel
import com.google.firebase.database.DatabaseReference

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var HomeproductViewModel: HomeViewModel
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        HomeproductViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        HomeproductViewModel.loadProducts()

        HomeproductViewModel.products.observe(viewLifecycleOwner) { productList ->
            val adapter = HomeProductAdapter(productList,
                onContainerClicked = { product -> val intent = Intent(activity, ProductViewActivity::class.java)
                    // Aquí puedes pasar datos extras si es necesario, por ejemplo:
                    intent.putExtra("productName", product.name)
                    intent.putExtra("productPrice", product.price)
                    intent.putExtra("productDescription", product.description)
                    intent.putExtra("productAvailability", product.isAvailable)
                    intent.putExtra("ProductBuilding", product.building)
                    intent.putExtra("ProductOwner", product.email)
                    startActivity(intent) })
            binding.productsRecyclerView.adapter = adapter
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}