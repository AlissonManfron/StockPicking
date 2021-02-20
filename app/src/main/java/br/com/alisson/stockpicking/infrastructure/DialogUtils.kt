package br.com.alisson.stockpicking.infrastructure

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.ui.portfolio.StockOnclickListener
import br.com.alisson.stockpicking.data.model.Stock

class DialogUtils {

    companion object {
        fun showPopupAddSctock(activity: Activity, callback: StockOnclickListener) {
            activity.let {
                val builder = AlertDialog.Builder(it)

                val inflater = activity.layoutInflater;

                val layout = inflater.inflate(R.layout.dialog_add_stock, null)

                val editTicker = layout.findViewById<EditText>(R.id.ticker)
                val editWeight = layout.findViewById<EditText>(R.id.weight)

                builder.setView(layout)
                    // Add action buttons
                    .setPositiveButton(
                        R.string.button_save_stock
                    ) { p0, p1 ->
                        val ticker = editTicker.text.toString()
                        val weight = editWeight.text.toString().toInt()
                        callback.onclickListener(Stock(null, ticker, weight))
                    }
                builder.create()
                builder.show()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}