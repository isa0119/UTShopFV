import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utshop.R
import com.example.utshop.ui.gallery.Building1Activity.Product
import com.example.utshop.ui.gallery.ProductDetailActivity // Asegúrate de importar correctamente tu ProductDetailActivity

class ProductAdapter(private val context: Context, private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val nameTextView: TextView = itemView.findViewById(R.id.productName)
        val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Asegúrate de que estás inflando el layout correcto para tus items de producto
        // 'activity_product_adapter' es el layout de los items
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_adapter, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price

        // Manejo de clic en el item para abrir ProductDetailActivity con detalles
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", currentItem.name)
                putExtra("EXTRA_PRODUCT_PRICE", currentItem.price)
                putExtra("EXTRA_PRODUCT_DESCRIPTION", currentItem.description)
                putExtra("EXTRA_SELLER_NAME", currentItem.sellerName)
                putExtra("EXTRA_PHONE_NUMBER", currentItem.phoneNumber)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = productList.size
}
