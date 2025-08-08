package com.example.budyymate.util

import java.text.NumberFormat
import java.util.*

object CurrencyFormatter {
    
    fun format(amount: Double, currency: String): String {
        val format = NumberFormat.getCurrencyInstance().apply {
            this.currency = Currency.getInstance(currency)
        }
        return format.format(amount)
    }
    
    fun getCurrencySymbol(currency: String): String {
        return when (currency) {
            "TRY" -> "₺"
            "USD" -> "$"
            "EUR" -> "€"
            else -> currency
        }
    }
    
    fun formatWithSymbol(amount: Double, currency: String): String {
        val symbol = getCurrencySymbol(currency)
        return "$symbol${String.format("%.2f", amount)}"
    }
} 