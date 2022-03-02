package br.com.alisson.stockpicking.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alisson.stockpicking.data.model.Stock
import br.com.alisson.stockpicking.databinding.StockItemBinding

class StockListAdapter : RecyclerView.Adapter<StockListAdapter.ViewHolder>() {
    private var stocks: List<Stock> = emptyList()
    private lateinit var itemClickListener: (Stock) -> Unit

    fun setListener(listener: (Stock) -> Unit) {
        itemClickListener = listener
    }

    class ViewHolder(private val item: StockItemBinding) : RecyclerView.ViewHolder(item.root),
        View.OnLongClickListener {

        private var itemLongClickListener: ItemLongClickListener? = null

        fun bindView(stock: Stock) {
            item.stockItemTitle.text = stock.ticker
            item.stockItemCurrentBalance.text = stock.getCurrentBalance()
            item.stockItemQtd.text = stock.quantity.toString()
            item.stockItemPercentage.text = stock.getWeight()
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
        val itemBinding =
            StockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
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