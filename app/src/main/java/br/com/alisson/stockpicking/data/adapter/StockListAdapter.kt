package br.com.alisson.stockpicking.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alisson.stockpicking.R
import br.com.alisson.stockpicking.data.model.Stock
import kotlinx.android.synthetic.main.stock_item.view.*

class StockListAdapter(private var stocks: List<Stock>,
                       private val context: Context,
                       private val itemClickListener: (Stock) -> Unit
) : RecyclerView.Adapter<StockListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

        private var itemLongClickListener: ItemLongClickListener? = null

        fun bindView(stock: Stock) {
            val title = itemView.stock_item_title
            title.text = stock.ticker
            itemView.setOnLongClickListener(this)
        }

        fun setMyItemLongClickListener(ic: ItemLongClickListener?) {
            itemLongClickListener = ic!!
        }

        override fun onLongClick(v: View?): Boolean {
            itemLongClickListener?.onItemLongClick(v, layoutPosition)
            return false
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(stocks[position])

        holder.setMyItemLongClickListener(object : ItemLongClickListener {
            override fun onItemLongClick(v: View?, pos: Int) {
                itemClickListener(stocks[position])
            }
        })
    }

    fun setStocks(stocks: List<Stock>) {
        this.stocks = stocks
        this.notifyDataSetChanged()
    }
}

interface ItemLongClickListener {
    fun onItemLongClick(v: View?, pos: Int)
}