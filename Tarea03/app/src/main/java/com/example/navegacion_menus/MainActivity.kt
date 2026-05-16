package com.example.navegacion_menus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. INICIALIZACIÓN DE VISTAS
        topAppBar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        navigationView.itemIconTintList = null

        // 2. BOTÓN PRINCIPAL DE NAVEGACIÓN
        val btnNavigate = findViewById<Button>(R.id.btnNavigate)
        btnNavigate.setOnClickListener {
            Log.d("Tarea3_Mhaisi", "Navegación: El usuario se movió a SecondActivity")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        // 3. LÓGICA DEL NAVIGATION DRAWER (Menú Lateral)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.close()

            val title = menuItem.title.toString()
            Log.d("Tarea3_Mhaisi", "Drawer: Usuario seleccionó '$title'")

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Inicio: Mhaisi Coffee", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_orders -> {
                    Toast.makeText(this, "Consultando tus pedidos anteriores...", Toast.LENGTH_SHORT).show()
                }

                // CORREGIDO: Abre SecondActivity mandando la señal de cargar Favoritos
                R.id.nav_favorites -> {
                    Toast.makeText(this, "Abriendo tus favoritos... ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, FavoritosActivity::class.java) // <--- Cambiado aquí
                    startActivity(intent)
                }

                R.id.nav_coupons -> {
                    Toast.makeText(this, "¡Revisando cupones disponibles!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    Log.d("Tarea3_Mhaisi", "Acción: Cierre de sesión detectado")
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Opción: $title", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        // 4. LÓGICA DEL TOP APP BAR
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    Toast.makeText(this, "Editar perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.notifications -> {
                    Toast.makeText(this, "No hay notificaciones nuevas", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more01 -> {
                    Toast.makeText(this, "Reportar un problema", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more02 -> {
                    Toast.makeText(this, "Más info sobre la app", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more03 -> {
                    Toast.makeText(this, "Ajustes de la aplicación", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}