package com.example.utshop

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.utshop.databinding.ActivityInicioBinding
import com.example.utshop.ui.SP

class Inicio : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInicioBinding
    private lateinit var SP:SP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("dato", "On create")

        SP=SP(this)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //seccion para redirigir a fragments en caso de ser necesario--------------------------------------------------------------------------------
        if (intent.getBooleanExtra("navigateToProductFragment", false)) {
            val navController = findNavController(R.id.nav_host_fragment_content_inicio)
            navController.navigate(R.id.nav_slideshow)
        }
        if (intent.getBooleanExtra("navigateToAccountFragment", false)){
            val navController = findNavController(R.id.nav_host_fragment_content_inicio)
            navController.navigate(R.id.nav_account)
        }
        //------------------------------------------------------------------------------------------------------------------------------------------------

        setSupportActionBar(binding.appBarInicio.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_inicio)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_account, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            menuItem -> when (menuItem.itemId){
                R.id.closeSession -> logout()
            else -> {
                // Navegar a la opción del menú seleccionada
                val navController = findNavController(R.id.nav_host_fragment_content_inicio)
                navController.navigate(menuItem.itemId)
                drawerLayout.closeDrawers() // Cierra el cajón de navegación después de la navegación
                true // Devuelve true para indicar que el evento ha sido consumido
            }
        }
        }
        // informacion del usuario en el menu de navegacion---------------------------------------------------------------------------------------
        val headerView = navView.getHeaderView(0)
        val Username: TextView = headerView.findViewById(R.id.username_header)
        val email: TextView = headerView.findViewById(R.id.email_header)
        Username.text = SP.username
        email.text = SP.email
        //------------------------------------------------------------------------------------------------------------------------------------------
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.inicio, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_inicio)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout():Boolean{
        SP.clearSession()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }
}



