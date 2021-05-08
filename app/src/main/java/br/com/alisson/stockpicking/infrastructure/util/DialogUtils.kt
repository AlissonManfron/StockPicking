package br.com.alisson.stockpicking.infrastructure.util

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.infrastructure.`interface`.StockOnclickListener

class DialogUtils {

    companion object {
        fun showPopupAddSctock(activity: Activity, callback: StockOnclickListener) {
            activity.let {
                val builder = AlertDialog.Builder(it)

                val inflater = activity.layoutInflater

                val layout = inflater.inflate(R.layout.dialog_add_stock, null)

                val editTicker = layout.findViewById<EditText>(R.id.ticker)
                val editWeight = layout.findViewById<EditText>(R.id.weight)
                val btnSave = layout.findViewById<Button>(R.id.save)

                builder.setView(layout)

                val alert = builder.create()
                alert.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                alert.setOnShowListener {
                    btnSave.setOnClickListener {

                        val ticker = editTicker.text.toString()
                        val weight = editWeight.text.toString()

                        if (ticker.isEmpty() || weight.isEmpty()) {
                            Toast.makeText(
                                activity,
                                "Preencha todos os campos!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            callback.onclickListener(Stock(null, ticker, weight.toInt()))
                            alert.dismiss()
                        }
                    }
                }
                alert.show()
            }
        }
    }
}