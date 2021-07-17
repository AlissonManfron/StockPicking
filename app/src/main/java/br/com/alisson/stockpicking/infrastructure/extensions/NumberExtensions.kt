package br.com.alisson.stockpicking.infrastructure.extensions

class NumberExtensions {


    companion object {

        fun Int.toWeight() : Int {
            return (0 + this / 10)
        }
    }
}