package com.example.utshop.ui.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.utshop.R

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Ejemplo de cómo asignar los datos a las vistas
        findViewById<TextView>(R.id.detail_product_name).text = intent.getStringExtra("EXTRA_PRODUCT_NAME")
        findViewById<TextView>(R.id.detail_product_price).text = intent.getStringExtra("EXTRA_PRODUCT_PRICE")
        findViewById<TextView>(R.id.detail_product_description).text = intent.getStringExtra("EXTRA_PRODUCT_DESCRIPTION")
        findViewById<TextView>(R.id.detail_seller_name).text = intent.getStringExtra("EXTRA_SELLER_NAME")
        findViewById<TextView>(R.id.detail_seller_phone).text = intent.getStringExtra("EXTRA_PHONE_NUMBER")

        // Para la imagen, necesitarías un mecanismo para pasar la referencia de la imagen y cargarla aquí.
        // Por simplicidad, he usado app:srcCompat="@drawable/ic_launcher_background" como placeholder.
    }
}
