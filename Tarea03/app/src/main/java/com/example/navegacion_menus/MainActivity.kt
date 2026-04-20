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

        // IMPORTANTE: Para que los iconos del Drawer se vean con sus colores reales
        navigationView.itemIconTintList = null

        // 2. BOTÓN DE NAVEGACIÓN A OTRA ACTIVIDAD
        val btnNavigate = findViewById<Button>(R.id.btnNavigate)
        btnNavigate.setOnClickListener {
            Log.d("Tarea3_Mhaisi", "Navegación: El usuario se movió a SecondActivity")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // 3. CONFIGURACIÓN DEL BOTÓN DE MENÚ (HAMBURGUESA)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        // 4. LÓGICA DEL NAVIGATION DRAWER (Menú Lateral)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Marcar visualmente y cerrar
            menuItem.isChecked = true
            drawerLayout.close()

            val title = menuItem.title.toString()

            Log.d("Tarea3_Mhaisi", "Drawer: Usuario seleccionó '$title'")

            // TOASTS PERSONALIZADOS SEGÚN EL ID
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Inicio: Mhaisi Coffee", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_orders -> {
                    Toast.makeText(this, "Consultando tus pedidos anteriores...", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_favorites -> {
                    Toast.makeText(this, "Abriendo tus productos favoritos", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_coupons -> {
                    Toast.makeText(this, "¡Revisando cupones disponibles!", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    Log.d("Tarea3_Mhaisi", "Acción: Cierre de sesión detectado")
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                }
                // Caso general para el resto de opciones (Sucursales, FAQ, etc.)
                else -> {
                    Toast.makeText(this, "Opción: $title", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        // 5. LÓGICA DEL TOP APP BAR (Iconos y 3 Puntos)
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    Log.d("Tarea3_Mhaisi", "TopBar: Clic en Editar Perfil")
                    Toast.makeText(this, "Editar perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.notifications -> {
                    Log.d("Tarea3_Mhaisi", "TopBar: Clic en Notificaciones")
                    Toast.makeText(this, "No hay notificaciones nuevas", Toast.LENGTH_SHORT).show()
                    true
                }
                // Opciones del Menú Desplegable (3 puntos)
                R.id.more01 -> {
                    Log.d("Tarea3_Mhaisi", "Overflow: Reportar problema")
                    Toast.makeText(this, "Reportar un problema", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more02 -> {
                    Log.d("Tarea3_Mhaisi", "Overflow: Más información")
                    Toast.makeText(this, "Más info sobre la app", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more03 -> {
                    Log.d("Tarea3_Mhaisi", "Overflow: Configuración")
                    Toast.makeText(this, "Ajustes de la aplicación", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}