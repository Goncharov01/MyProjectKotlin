package com.myproject.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.data.db.DataCurrency

class MyAdapterRecyclerPopular(
    private val clickListener: (DataCurrency) -> Unit,
) : PagingDataAdapter<DataCurrency,DataCurrencyViewHolder>(CallBackDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCurrencyViewHolder {
        val binding: SingleItemBinding =
            SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataCurrencyViewHolder, position: Int) {
        val dataCurrency = getItem(position)!!
        holder.binding.textCurrency.text = dataCurrency.currency
        holder.binding.textValue.text = dataCurrency.value

        if (dataCurrency.favorite) {
            holder.binding.iconFavorite.setImageResource(R.drawable.favorite_icon)
        } else {
            holder.binding.iconFavorite.setImageResource(R.drawable.unfavorite_icon)
        }

        holder.binding.iconFavorite.setOnClickListener {
            clickListener(dataCurrency)
            notifyItemChanged(position)
        }
    }

    private object CallBackDiffUtil : DiffUtil.ItemCallback<DataCurrency>() {

        override fun areItemsTheSame(oldItem: DataCurrency, newItem: DataCurrency): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataCurrency, newItem: DataCurrency): Boolean {
            return oldItem.currency == newItem.currency && oldItem.favorite == newItem.favorite
        }
    }
}

class DataCurrencyViewHolder(var binding: SingleItemBinding) :
    RecyclerView.ViewHolder(binding.root)