package com.example.navegacion_menus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue // <--- IMPORTANTE PARA EL SP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class PedidoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pedido, container, false)

        val btnCheckout = root.findViewById<MaterialButton>(R.id.btn_checkout)
        val btnCancel = root.findViewById<MaterialButton>(R.id.btn_cancel_order)

        // CORREGIDO: Al presionar "Realizar pedido", ahora abre el diálogo de confirmación
        btnCheckout?.setOnClickListener {
            Log.d("Tarea3_Mhaisi", "Orden: El usuario presionó 'Realizar pedido'")
            mostrarDialogoConfirmacion()
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

    override fun onResume() {
        super.onResume()
        view?.let { cargarOrden(it) }
    }

    private fun cargarOrden(root: View) {
        val container = root.findViewById<LinearLayout>(R.id.container_items_orden)
        val tvEmpty = root.findViewById<TextView>(R.id.tv_empty_cart)
        val tvTotal = root.findViewById<TextView>(R.id.tv_total_pedido)
        val btnCheckout = root.findViewById<View>(R.id.btn_checkout)
        val btnCancel = root.findViewById<View>(R.id.btn_cancel_order)

        container?.removeAllViews()
        var sumaTotal = 0.0

        val productos = CarritoGlobal.obtenerProductosSeleccionados()

        if (productos.isEmpty()) {
            tvEmpty?.visibility = View.VISIBLE
            container?.visibility = View.GONE
            btnCheckout?.visibility = View.GONE
            btnCancel?.visibility = View.GONE
            tvTotal?.text = "$0.00"
        } else {
            tvEmpty?.visibility = View.GONE
            container?.visibility = View.VISIBLE
            btnCheckout?.visibility = View.VISIBLE
            btnCancel?.visibility = View.VISIBLE

            val grupos = productos.groupBy { "${it.nombre}|${it.especificaciones}" }

            for (entry in grupos) {
                val listaDeIguales = entry.value
                val p = listaDeIguales[0]

                val subtotalGrupo = p.precio * listaDeIguales.size
                sumaTotal += subtotalGrupo

                val itemView = layoutInflater.inflate(R.layout.item_orden_card, container, false)

                itemView.findViewById<TextView>(R.id.tv_nombre_item_orden).text = p.nombre
                itemView.findViewById<TextView>(R.id.tv_cantidad_item_orden).text = "Cantidad: ${listaDeIguales.size}"
                itemView.findViewById<TextView>(R.id.tv_precio_item_orden).text = "$${String.format("%.2f", subtotalGrupo)}"

                val tvEspec = itemView.findViewById<TextView>(R.id.tv_especificaciones_orden)
                if (p.especificaciones.isNotEmpty()) {
                    tvEspec.text = p.especificaciones
                } else {
                    tvEspec.text = when {
                        p.nombre == "Espresso" -> "• Tamaño Chico\n• Azúcar"
                        listOf("Latte Clásico", "Capuccino", "Chocolate caliente", "Matcha").contains(p.nombre) ->
                            "• Tamaño Grande\n• Leche Entera\n• Azúcar"
                        p.categoria == "comida" -> "• Tamaño Estándar\n• Preparación Tradicional"
                        p.categoria == "extra" -> "• Producto Cerrado"
                        else -> "• Tamaño Grande\n• Azúcar"
                    }
                }

                val imgProducto = itemView.findViewById<ImageView>(R.id.img_item_orden)
                when (p.nombre) {
                    "Latte Clásico"      -> imgProducto.setImageResource(R.drawable.latte)
                    "Espresso"           -> imgProducto.setImageResource(R.drawable.espresso)
                    "Chocolate caliente" -> imgProducto.setImageResource(R.drawable.chocolate)
                    "Capuccino"          -> imgProducto.setImageResource(R.drawable.capuccino)
                    "Té Frío"            -> imgProducto.setImageResource(R.drawable.tefrio)
                    "Limonada de Fresa"  -> imgProducto.setImageResource(R.drawable.limonada)
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
                    else                 -> imgProducto.setImageResource(R.drawable.img)
                }

                val btnPerso = itemView.findViewById<MaterialButton>(R.id.btn_customize_item)
                if (p.categoria == "extra") {
                    btnPerso?.visibility = View.GONE
                } else {
                    btnPerso?.visibility = View.VISIBLE
                    btnPerso?.setOnClickListener {
                        val intent = Intent(requireContext(), CustomizationActivity::class.java)
                        intent.putExtra("EXTRA_NOMBRE", p.nombre)
                        intent.putExtra("EXTRA_CATEGORIA", p.categoria)

                        val imgRes = when (p.nombre) {
                            "Latte Clásico"      -> R.drawable.latte
                            "Espresso"           -> R.drawable.espresso
                            "Chocolate caliente" -> R.drawable.chocolate
                            "Capuccino"          -> R.drawable.capuccino
                            "Té Frío"            -> R.drawable.tefrio
                            "Limonada de Fresa"  -> R.drawable.limonada
                            "Smoothie Asha"      -> R.drawable.smoothie
                            "Matcha"             -> R.drawable.matcha
                            "Baguette Pizza"     -> R.drawable.baguette
                            "Ensalada César"     -> R.drawable.ensalada
                            "Sandwich Pavo"      -> R.drawable.sandwich
                            "Bagel Guacamole"    -> R.drawable.bagel
                            "Dona Caramelo"      -> R.drawable.dona
                            "Tarta de Moras"     -> R.drawable.tarta
                            "Pastel Zanahoria"   -> R.drawable.pastel
                            "Cheesecake"         -> R.drawable.cheesecake
                            else                 -> R.drawable.img
                        }

                        intent.putExtra("EXTRA_IMAGEN", imgRes)
                        startActivity(intent)
                    }
                }

                container?.addView(itemView)
            }
            tvTotal?.text = "$${String.format("%.2f", sumaTotal)}"
        }
    }

    private fun mostrarDialogoConfirmacion() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmar_pedido, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val containerResumen = dialogView.findViewById<LinearLayout>(R.id.container_resumen_pedido)
        val tvTotal = dialogView.findViewById<TextView>(R.id.tv_total_confirmacion)
        val btnFinalizar = dialogView.findViewById<View>(R.id.btn_finalizar_pedido)
        val btnCancelar = dialogView.findViewById<View>(R.id.btn_cancelar_pedido)

        var totalAcumulado = 0.0
        containerResumen.removeAllViews()

        val mapaProductos = mapOf(
            "Latte Clásico" to CarritoGlobal.latte,
            "Espresso" to CarritoGlobal.espresso,
            "Chocolate Caliente" to CarritoGlobal.chocolate,
            "Capuccino" to CarritoGlobal.capuccino,
            "Té Frío" to CarritoGlobal.teFrio,
            "Limonada" to CarritoGlobal.limonada,
            "Smoothie Asha" to CarritoGlobal.smoothie,
            "Matcha" to CarritoGlobal.matcha,

            "Baguette Pizza" to CarritoGlobal.baguette,
            "Ensalada César" to CarritoGlobal.cesar,
            "Sandwich Pavo" to CarritoGlobal.pavo,
            "Bagel Guacamole" to CarritoGlobal.bagel,
            "Dona Caramelo" to CarritoGlobal.dona,
            "Tarta de Moras" to CarritoGlobal.tarta,
            "Pastel Zanahoria" to CarritoGlobal.zanahoria,

            "Grano Oaxaca" to CarritoGlobal.oaxaca,
            "Molido Michoacán" to CarritoGlobal.michoacan,
            "Termo Rosa" to CarritoGlobal.rosa,
            "Termo Aniversario" to CarritoGlobal.aniversario,
            "Tote Bag Mhaisi" to CarritoGlobal.tote,
            "Galletas Avena" to CarritoGlobal.galletas,
            "Mix Energético" to CarritoGlobal.mix
        )

        for ((nombre, cantidad) in mapaProductos) {
            if (cantidad > 0) {
                val precioUnitario = CarritoGlobal.obtenerPrecioBase(nombre)
                val subtotalProducto = precioUnitario * cantidad
                totalAcumulado += subtotalProducto

                val tvProducto = TextView(context).apply {
                    text = "${cantidad}x  $nombre  ->  $${String.format("%.2f", subtotalProducto)}"
                    // CORREGIDO: Definición correcta de tamaño de SP en código de Android
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                    setTextColor(android.graphics.Color.parseColor("#424242"))
                    setPadding(0, 8, 0, 8)
                }
                containerResumen.addView(tvProducto)
            }
        }

        tvTotal.text = "$${String.format("%.2f", totalAcumulado)}"

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        btnFinalizar.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "¡Pedido confirmado! Generando código QR... ☕", Toast.LENGTH_LONG).show()
            // Aquí puedes limpiar el carrito o mandar a la pantalla del QR final
        }

        dialog.show()
    }
}