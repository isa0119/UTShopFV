package com.example.utshop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utshop.R
import com.example.utshop.ui.slideshow.Product

data class HomeProduct(
    val building: String,
    val imageResource: Int,
    val name: String,
    val price: Double,
    val isAvailable: String,
    val description: String,
    val email:String
)

class HomeProductAdapter(private val productList: List<HomeProduct>, private val onContainerClicked: (HomeProduct) -> Unit) : RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val availability: TextView = itemView.findViewById(R.id.productAvailability)
        val clickableLinearLayout: LinearLayout = itemView.findViewById(R.id.productContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price.toString()
        holder.availability.text = currentItem.isAvailable
        holder.clickableLinearLayout.setOnClickListener { onContainerClicked(currentItem) }

    }

    override fun getItemCount() = productList.size
}