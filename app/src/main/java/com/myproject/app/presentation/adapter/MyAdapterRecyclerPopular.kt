package com.myproject.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.data.db.DataCurrency
import kotlinx.coroutines.*

class MyAdapterRecyclerPopular(
    private val clickListener: (DataCurrency) -> Unit,
    private val checkFavoriteCurrency: suspend (String) -> DataCurrency
) : RecyclerView.Adapter<DataCurrencyViewHolder>() {

    private var listCurrency = mutableListOf<DataCurrency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCurrencyViewHolder {
        val binding: SingleItemBinding =
            SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataCurrencyViewHolder, position: Int) {
        val dataCurrency = listCurrency[position]
        holder.binding.textCurrency.text = dataCurrency.currency
        holder.binding.textValue.text = dataCurrency.value

        CoroutineScope(Dispatchers.Main).launch {

            if (checkFavoriteCurrency(dataCurrency.currency) != null) {
                holder.binding.iconFavorite.setImageResource(R.drawable.favorite_icon)
            } else {
                holder.binding.iconFavorite.setImageResource(R.drawable.unfavorite_icon)
            }

        }

        holder.binding.iconFavorite.setOnClickListener {

            clickListener(dataCurrency)
            notifyItemChanged(position)

        }

    }

    fun addList(listCurrency: List<DataCurrency>) {
        this.listCurrency.clear()
        this.listCurrency.addAll(listCurrency)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listCurrency.size
    }

}

class DataCurrencyViewHolder(var binding: SingleItemBinding) :
    RecyclerView.ViewHolder(binding.root)
