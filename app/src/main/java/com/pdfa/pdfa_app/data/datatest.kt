package com.pdfa.pdfa_app.data

data class FridgeItem(
    val name: String,
    val calories: Int,
    val quantity: Int,
    val unit: String = "Gramme", // "Gramme" ou "Pièce"
    val purchaseDate: String = "",
    val expiryInDays: Int = 0,
    val price: Float? = null
)



