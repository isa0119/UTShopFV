package com.example.utshop.ui.slideshow
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.utshop.databinding.FragmentSlideshowBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utshop.Inicio
import com.example.utshop.R
import com.example.utshop.ui.SP
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference
    private lateinit var SP : SP
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val view = binding.root
        SP = SP(requireContext())
        database = Firebase.database.reference

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        val sanitizedEmail = SP.email.toString().replace(".", "_").replace("@", "_")
        productViewModel.loadProducts(SP.building.toString(),sanitizedEmail)

        // Observar los cambios en los productos y actualizar la interfaz de usuario
        productViewModel.products.observe(viewLifecycleOwner) { productList ->
            val adapter = ProductAdapter(productList,
                onEditClicked = { product -> val intent = Intent(activity, EditProductActivity::class.java)
                    // Aquí puedes pasar datos extras si es necesario, por ejemplo:
                    intent.putExtra("productName", product.name)
                    intent.putExtra("productPrice", product.price)
                    intent.putExtra("productDescription", product.description)
                    intent.putExtra("productAvailability", product.isAvailable)
                    intent.putExtra("ProductBuilding", product.building)
                    startActivity(intent) },
                onDeleteClicked = { product -> DeleteProduct(sanitizedEmail, product.name, product.building) })
            binding.productsRecyclerView.adapter = adapter
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        binding.fabAddProduct.setOnClickListener {
            // Navegar a la nueva actividad o fragmento para agregar producto
            val intent = Intent(activity, NewProductActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductAdapter(getElements(),
            onEditClicked = { product ->
                val intent = Intent(activity, EditProductActivity::class.java)
                // Aquí puedes pasar datos extras si es necesario, por ejemplo:
                intent.putExtra("productName", product.name)
                intent.putExtra("productPrice", product.price)
                intent.putExtra("productDescription", product.description)
                intent.putExtra("productAvailability", product.isAvailable)
                startActivity(intent)
            },
            onDeleteClicked = { product ->
                // Implementa la acción de eliminar aquí
            })
        binding.productsRecyclerView.adapter = adapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun DeleteProduct(email:String, name:String, building:String){
        val reference = database.child("Products").child(building).child(email).child(name)
        reference.removeValue().addOnSuccessListener {
            val intent = Intent(requireContext(), Inicio::class.java)
            intent.putExtra("navigateToProductFragment", true)
            startActivity(intent)
        }.addOnFailureListener { exception ->
            val errorMessage = exception.message ?: "Error desconocido al registrar información del producto"
            showAlert(errorMessage) }
    }
    /*private fun getElements():List<Product>{
        var List = listOf<Product>()
        val ListProvider = mutableListOf<Product>()
        database.child("Products").child(SP.email.toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { productSnapshot ->
                    val productName = productSnapshot.key ?: ""
                    val available = productSnapshot.child("availability").getValue(Boolean::class.java) ?: false
                    val price = productSnapshot.child("price").getValue(Double::class.java) ?: 0.0
                    val description = productSnapshot.child("description").getValue(String::class.java) ?: ""
                    val product = Product(R.drawable.colegio, productName, price, available, description)
                    ListProvider.add(product)
                }
                List = ListProvider.toList()
            }

            override fun onCancelled(error: DatabaseError) {
                val errorMessage = error.message ?: "Error desconocido al registrar información del producto"
                showAlert(errorMessage)
            }
        })
        return List
    }*/
    private fun showAlert(message: String) {
        // Mostrar una alerta con el mensaje de error
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}


