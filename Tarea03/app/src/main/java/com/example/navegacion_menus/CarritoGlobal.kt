package com.example.navegacion_menus

data class Producto(
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val categoria: String // Esto obliga a que todos los 'add' tengan categoria
)

object CarritoGlobal {
    // --- BEBIDAS CALIENTES ---
    var latte = 0; var espresso = 0; var chocolate = 0; var capuccino = 0

    // --- BEBIDAS FRÍAS ---
    var teFrio = 0; var limonada = 0; var smoothie = 0; var matcha = 0

    // --- COMIDAS ---
    var baguette = 0; var cesar = 0; var pavo = 0; var bagel = 0
    var dona = 0; var tarta = 0; var zanahoria = 0; var cheesecake = 0

    // --- EXTRAS ---
    var oaxaca = 0; var michoacan = 0; var rosa = 0; var aniversario = 0
    var tote = 0; var galletas = 0; var mix = 0

    fun obtenerProductosSeleccionados(): List<Producto> {
        val lista = mutableListOf<Producto>()

        // Bebidas: Se agrega la categoría "bebida"
        if (latte > 0) lista.add(Producto("Latte Clásico", latte, 65.0, "bebida"))
        if (espresso > 0) lista.add(Producto("Espresso", espresso, 45.0, "bebida"))
        if (chocolate > 0) lista.add(Producto("Chocolate caliente", chocolate, 50.0, "bebida"))
        if (capuccino > 0) lista.add(Producto("Capuccino", capuccino, 70.0, "bebida"))
        if (teFrio > 0) lista.add(Producto("Té Frío", teFrio, 55.0, "bebida"))
        if (limonada > 0) lista.add(Producto("Limonada", limonada, 40.0, "bebida"))
        if (smoothie > 0) lista.add(Producto("Smoothie Asha", smoothie, 80.0, "bebida"))
        if (matcha > 0) lista.add(Producto("Matcha", matcha, 75.0, "bebida"))

        // Comidas: Se agrega la categoría "comida"
        if (baguette > 0) lista.add(Producto("Baguette Pizza", baguette, 95.0, "comida"))
        if (cesar > 0) lista.add(Producto("Ensalada César", cesar, 110.0, "comida"))
        if (pavo > 0) lista.add(Producto("Sandwich Pavo", pavo, 85.0, "comida"))
        if (bagel > 0) lista.add(Producto("Bagel Guacamole", bagel, 75.0, "comida"))
        if (dona > 0) lista.add(Producto("Dona Caramelo", dona, 35.0, "comida"))
        if (tarta > 0) lista.add(Producto("Tarta de Moras", tarta, 60.0, "comida"))
        if (zanahoria > 0) lista.add(Producto("Pastel Zanahoria", zanahoria, 70.0, "comida"))
        if (cheesecake > 0) lista.add(Producto("Cheesecake", cheesecake, 65.0, "comida"))

        // Extras: Se agrega la categoría "extra"
        if (oaxaca > 0) lista.add(Producto("Grano Oaxaca", oaxaca, 280.0, "extra"))
        if (michoacan > 0) lista.add(Producto("Molido Michoacán", michoacan, 265.0, "extra"))
        if (rosa > 0) lista.add(Producto("Termo Rosa", rosa, 450.0, "extra"))
        if (aniversario > 0) lista.add(Producto("Termo Aniversario", aniversario, 520.0, "extra"))
        if (tote > 0) lista.add(Producto("Tote Bag Mhaisi", tote, 190.0, "extra"))
        if (galletas > 0) lista.add(Producto("Galletas Avena", galletas, 38.0, "extra"))
        if (mix > 0) lista.add(Producto("Mix Energético", mix, 42.0, "extra"))

        return lista
    }

    fun limpiarCarrito() {
        latte = 0; espresso = 0; chocolate = 0; capuccino = 0
        teFrio = 0; limonada = 0; smoothie = 0; matcha = 0
        baguette = 0; cesar = 0; pavo = 0; bagel = 0
        dona = 0; tarta = 0; zanahoria = 0; cheesecake = 0
        oaxaca = 0; michoacan = 0; rosa = 0; aniversario = 0
        tote = 0; galletas = 0; mix = 0
    }
}