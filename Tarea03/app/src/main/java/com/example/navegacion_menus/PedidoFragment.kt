package com.example.navegacion_menus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.widget.ImageView
import com.google.android.material.button.MaterialButton

class PedidoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pedido, container, false)

        val btnCheckout = root.findViewById<MaterialButton>(R.id.btn_checkout)
        val btnCancel = root.findViewById<MaterialButton>(R.id.btn_cancel_order)

        btnCheckout?.setOnClickListener {
            Log.d("Tarea3_Mhaisi", "Orden: El usuario presionó 'Realizar pedido'")
            Toast.makeText(context, "¡Pedido enviado a Mhaisi Coffee!", Toast.LENGTH_LONG).show()
        }

        btnCancel?.setOnClickListener {
            Log.d("Tarea3_Mhaisi", "Orden: El usuario canceló el pedido")
            CarritoGlobal.limpiarCarrito()
            cargarOrden(root)
            Toast.makeText(context, "Pedido cancelado", Toast.LENGTH_SHORT).show()
        }

        cargarOrden(root)
        return root
    }

    private fun cargarOrden(root: View) {
        val container = root.findViewById<LinearLayout>(R.id.container_items_orden)
        val tvEmpty = root.findViewById<TextView>(R.id.tv_empty_cart)
        val tvTotal = root.findViewById<TextView>(R.id.tv_total_pedido) // Referencia al Total
        val btnCheckout = root.findViewById<View>(R.id.btn_checkout)
        val btnCancel = root.findViewById<View>(R.id.btn_cancel_order)

        container?.removeAllViews()

        // Variable para acumular la suma de todos los productos
        var sumaTotal = 0.0

        val productosSeleccionados = CarritoGlobal.obtenerProductosSeleccionados()

        if (productosSeleccionados.isEmpty()) {
            tvEmpty?.visibility = View.VISIBLE
            container?.visibility = View.GONE
            btnCheckout?.visibility = View.GONE
            btnCancel?.visibility = View.GONE
            tvTotal?.text = "$0.00" // Resetear total
        } else {
            tvEmpty?.visibility = View.GONE
            container?.visibility = View.VISIBLE
            btnCheckout?.visibility = View.VISIBLE
            btnCancel?.visibility = View.VISIBLE

            for (prod in productosSeleccionados) {
                val itemView = layoutInflater.inflate(R.layout.item_orden_card, container, false)

                // --- 1. REFERENCIAS A LOS COMPONENTES ---
                val imgProducto = itemView.findViewById<ImageView>(R.id.img_item_orden)
                val tvEspec = itemView.findViewById<TextView>(R.id.tv_especificaciones_orden)
                val btnPerso = itemView.findViewById<MaterialButton>(R.id.btn_customize_item)

                // --- 2. ASIGNACIÓN DE IMÁGENES POR NOMBRE REAL ---
                when (prod.nombre) {
                    "Latte Clásico"      -> imgProducto.setImageResource(R.drawable.latte)
                    "Espresso"           -> imgProducto.setImageResource(R.drawable.espresso)
                    "Chocolate caliente" -> imgProducto.setImageResource(R.drawable.chocolate)
                    "Capuccino"          -> imgProducto.setImageResource(R.drawable.capuccino)
                    "Té Frío"            -> imgProducto.setImageResource(R.drawable.tefrio)
                    "Limonada"           -> imgProducto.setImageResource(R.drawable.limonada)
                    "Smoothie Asha"      -> imgProducto.setImageResource(R.drawable.smoothie)
                    "Matcha"             -> imgProducto.setImageResource(R.drawable.matcha)
                    "Baguette Pizza"     -> imgProducto.setImageResource(R.drawable.baguette)
                    "Ensalada César"     -> imgProducto.setImageResource(R.drawable.ensalada)
                    "Sandwich Pavo"      -> imgProducto.setImageResource(R.drawable.sandwich)
                    "Bagel Guacamole"    -> imgProducto.setImageResource(R.drawable.bagel)
                    "Dona Caramelo"      -> imgProducto.setImageResource(R.drawable.dona)
                    "Tarta de Moras"     -> imgProducto.setImageResource(R.drawable.tarta)
                    "Pastel Zanahoria"   -> imgProducto.setImageResource(R.drawable.pastel)
                    "Cheesecake"         -> imgProducto.setImageResource(R.drawable.cheesecake)
                    "Grano Oaxaca"       -> imgProducto.setImageResource(R.drawable.grano)
                    "Molido Michoacán"   -> imgProducto.setImageResource(R.drawable.molido)
                    "Termo Rosa"         -> imgProducto.setImageResource(R.drawable.termo_rosa)
                    "Termo Aniversario"  -> imgProducto.setImageResource(R.drawable.aniversario)
                    "Tote Bag Mhaisi"    -> imgProducto.setImageResource(R.drawable.tote)
                    "Galletas Avena"     -> imgProducto.setImageResource(R.drawable.galletas)
                    "Mix Energético"     -> imgProducto.setImageResource(R.drawable.mix)
                    else -> imgProducto.setImageResource(R.drawable.img)
                }

                // --- 3. DATOS DE TEXTO ---
                val subtotal = prod.precio * prod.cantidad
                sumaTotal += subtotal // Acumulamos el precio en la suma total

                itemView.findViewById<TextView>(R.id.tv_nombre_item_orden).text = prod.nombre
                itemView.findViewById<TextView>(R.id.tv_cantidad_item_orden).text = "Cantidad: ${prod.cantidad}"
                itemView.findViewById<TextView>(R.id.tv_precio_item_orden).text = "$$subtotal"

                // --- 4. LÓGICA DE ESPECIFICACIONES ---
                when (prod.categoria) {
                    "bebida" -> {
                        tvEspec.visibility = View.VISIBLE
                        btnPerso.visibility = View.VISIBLE
                        tvEspec.text = "• Tamaño Grande\n• Con azúcar\n• Con crema batida\n• Leche Entera"
                        btnPerso?.setOnClickListener {
                            Log.d("Tarea3_Mhaisi", "Orden: Personalizando bebida -> ${prod.nombre}")
                        }
                    }
                    "comida" -> {
                        tvEspec.visibility = View.VISIBLE
                        btnPerso.visibility = View.VISIBLE
                        tvEspec.text = "• Caliente\n• Para llevar\n• Con todo"
                        btnPerso?.setOnClickListener {
                            Log.d("Tarea3_Mhaisi", "Orden: Personalizando alimento -> ${prod.nombre}")
                        }
                    }
                    "extra" -> {
                        tvEspec.visibility = View.GONE
                        btnPerso.visibility = View.GONE
                    }
                }

                container?.addView(itemView)
            }

            // --- 5. ACTUALIZAR EL TOTAL AL FINAL DEL CICLO ---
            tvTotal?.text = "$${String.format("%.2f", sumaTotal)}"
            Log.d("Tarea3_Mhaisi", "Orden: El total del pedido es $$sumaTotal")
        }
    }
}