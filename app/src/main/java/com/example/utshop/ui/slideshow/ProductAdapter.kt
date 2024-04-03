package com.example.utshop.ui.slideshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.utshop.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Product(
    val building: String,
    val imageResource: Int,
    val name: String,
    val price: Double,
    val isAvailable: Boolean,
    val description: String
)



class ProductAdapter(private val productList: List<Product>,
                     private val onEditClicked: (Product) -> Unit,
                     private val onDeleteClicked: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
        val availabilitySwitch: Switch = itemView.findViewById(R.id.productAvailability)
        val descriptionTextView: TextView = itemView.findViewById(R.id.productDescription)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price.toString()
        holder.availabilitySwitch.isChecked = currentItem.isAvailable
        holder.descriptionTextView.text = currentItem.description
        holder.editButton.setOnClickListener { onEditClicked(currentItem) }
        holder.deleteButton.setOnClickListener { onDeleteClicked(currentItem) }

    }

    override fun getItemCount() = productList.size
}
