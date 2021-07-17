package br.com.alisson.stockpicking.infrastructure.extensions

import java.util.*

class StringExtensions {

    companion object {
        fun String.toDate(): Date? {
            return try {
                Date(this)
            } catch (e: Exception) {
                null
            }
        }
    }
}