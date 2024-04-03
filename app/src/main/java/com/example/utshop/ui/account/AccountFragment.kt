package com.example.utshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.utshop.Inicio
import com.example.utshop.UserInf
import com.example.utshop.databinding.ActivityAccountFragmentBinding
import com.example.utshop.ui.SP
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AccountFragment : Fragment() {

    private var _binding: ActivityAccountFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : DatabaseReference
    private lateinit var SP : SP

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityAccountFragmentBinding.inflate(inflater, container, false)
        database = Firebase.database.reference
        SP = SP(requireContext())
        val email:String = SP.email.toString()
        val sanitizedEmail:String = email.replace(".", "_").replace("@", "_")
        val root: View = binding.root
        database.child("Users").child(sanitizedEmail).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userInf = snapshot.getValue(UserInf::class.java)
                if (userInf != null) {
                    val edificio = userInf.edificio
                    val contacto = userInf.contacto
                    val user = userInf.user
                    binding.editTextName.text = Editable.Factory.getInstance().newEditable(user)
                    binding.editTextPhone.text = Editable.Factory.getInstance().newEditable(contacto)
                    binding.editTextBuilding.text = Editable.Factory.getInstance().newEditable(edificio)
                    binding.editTextEmail.isEnabled = false
                    binding.editTextEmail.setText(email)
                    /*val message = "Usuario: $user\nContacto: $contacto\nEdificio: $edificio\nEmail: $email"
                    showAlert(message)*/
                }
            } else {
                Toast.makeText(requireContext(), sanitizedEmail, Toast.LENGTH_SHORT).show()
                showAlert(email)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "fallo en la consulta firebase", Toast.LENGTH_SHORT).show()
        }
        binding.buttonSave.setOnClickListener {
            val user = binding.editTextName.text.toString()
            val contact = binding.editTextPhone.text.toString()
            val building = binding.editTextBuilding.text.toString()
            val email = binding.editTextEmail.text.toString()
            Update(user, contact, building, email)
        }
        return root
    }

    private fun showAlert(message: String) {
        // Mostrar una alerta con el mensaje de error
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val user = binding.editTextName.text.toString()
        val contact = binding.editTextPhone.text.toString()
        val building = binding.editTextBuilding.text.toString()
        val email = binding.editTextEmail.text.toString()
        binding.buttonSave.setOnClickListener {
            Update(user, contact, building, email)
        }*/
    }

    private fun Update(user:String, contact:String, building:String, email:String){
        val sanitizedEmail = email.replace(".", "_").replace("@", "_")
        val datos = mapOf(
            "contacto" to contact,
            "edificio" to building,
            "user" to user
        )
        database.child("Users").child(sanitizedEmail).setValue(datos).addOnSuccessListener {
            val intent = Intent(requireContext(), Inicio::class.java)
            intent.putExtra("navigateToAccountFragment", true)
            startActivity(intent)
        }.addOnFailureListener { exception ->
            val errorMessage = exception.message ?: "Error desconocido al registrar información del producto"
            showAlert(errorMessage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
