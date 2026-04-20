package com.example.navegacion_menus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class BebidasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bebidas, container, false)

        setupCounter(root, R.id.btn_plus_latte, R.id.btn_minus_latte, R.id.tv_count_latte, "Latte Clásico")
        setupCounter(root, R.id.btn_plus_espresso, R.id.btn_minus_espresso, R.id.tv_count_espresso, "Espresso")
        setupCounter(root, R.id.btn_plus_chocolate, R.id.btn_minus_chocolate, R.id.tv_count_chocolate, "Chocolate Caliente")
        setupCounter(root, R.id.btn_plus_capuccino, R.id.btn_minus_capuccino, R.id.tv_count_capuccino, "Capuccino")

        setupCounter(root, R.id.btn_plus_te, R.id.btn_minus_te, R.id.tv_count_te, "Té Frío")
        setupCounter(root, R.id.btn_plus_limonada, R.id.btn_minus_limonada, R.id.tv_count_limonada, "Limonada")
        setupCounter(root, R.id.btn_plus_smoothie, R.id.btn_minus_smoothie, R.id.tv_count_smoothie, "Smoothie Asha")
        setupCounter(root, R.id.btn_plus_matcha, R.id.btn_minus_matcha, R.id.tv_count_matcha, "Matcha")

        return root
    }

    private fun setupCounter(view: View, plusId: Int, minusId: Int, countId: Int, productName: String) {
        val btnPlus = view.findViewById<View>(plusId)
        val btnMinus = view.findViewById<View>(minusId)
        val tvCount = view.findViewById<TextView>(countId)

        tvCount.text = obtenerValorGlobal(productName).toString()

        btnPlus?.setOnClickListener {
            modificarValorGlobal(productName, true)
            val nuevoValor = obtenerValorGlobal(productName)
            tvCount.text = nuevoValor.toString()

            Log.d("Tarea3_Mhaisi", "Bebidas: Se añadió '$productName'. Cantidad actual: $nuevoValor")
        }

        btnMinus?.setOnClickListener {
            val valorActual = obtenerValorGlobal(productName)
            if (valorActual > 0) {
                modificarValorGlobal(productName, false)
                val nuevoValor = obtenerValorGlobal(productName)
                tvCount.text = nuevoValor.toString()

                Log.d("Tarea3_Mhaisi", "Bebidas: Se quitó '$productName'. Cantidad actual: $nuevoValor")
            }
        }
    }

    private fun obtenerValorGlobal(nombre: String): Int {
        return when(nombre) {
            "Latte Clásico" -> CarritoGlobal.latte
            "Espresso" -> CarritoGlobal.espresso
            "Chocolate Caliente" -> CarritoGlobal.chocolate // Antes decía "Americano"
            "Capuccino" -> CarritoGlobal.capuccino
            "Té Frío" -> CarritoGlobal.teFrio
            "Limonada" -> CarritoGlobal.limonada
            "Smoothie Asha" -> CarritoGlobal.smoothie
            "Matcha" -> CarritoGlobal.matcha
            else -> 0
        }
    }

    private fun modificarValorGlobal(nombre: String, aumentar: Boolean) {
        val cambio = if (aumentar) 1 else -1
        when(nombre) {
            "Latte Clásico" -> CarritoGlobal.latte += cambio
            "Espresso" -> CarritoGlobal.espresso += cambio
            "Chocolate Caliente" -> CarritoGlobal.chocolate += cambio // Antes decía "Americano"
            "Capuccino" -> CarritoGlobal.capuccino += cambio
            "Té Frío" -> CarritoGlobal.teFrio += cambio
            "Limonada" -> CarritoGlobal.limonada += cambio
            "Smoothie Asha" -> CarritoGlobal.smoothie += cambio
            "Matcha" -> CarritoGlobal.matcha += cambio
        }
    }
}