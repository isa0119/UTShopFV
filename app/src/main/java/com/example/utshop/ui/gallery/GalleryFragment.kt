package com.example.utshop.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.utshop.Inicio
import com.example.utshop.databinding.FragmentGalleryBinding
// Importa las actividades de cada edificio aquí

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonBuilding1.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 1)
            startActivity(intent)
        }

        binding.imageButtonBuilding2.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 2)
            startActivity(intent)
        }

        binding.imageButtonBuilding3.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 3)
            startActivity(intent)
        }

        binding.imageButtonBuilding4.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 4)
            startActivity(intent)
        }

        binding.imageButtonBuilding5.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 5)
            startActivity(intent)
        }
        binding.imageButtonBuilding6.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 6)
            startActivity(intent)
        }
        binding.imageButtonBuilding7.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 7)
            startActivity(intent)
        }
        binding.imageButtonBuilding8.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 8)
            startActivity(intent)
        }
        binding.imageButtonBuilding9.setOnClickListener {
            val intent = Intent(context, BuildingActivity::class.java)
            intent.putExtra("Building", 9)
            startActivity(intent)
        }


        // Listeners para los edificios
        // Esto los mandara al otra interfaz UwU
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
