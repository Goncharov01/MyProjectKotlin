package com.myproject.app.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.room.DataCurrency
import com.myproject.app.room.DataCurrencyDao
import kotlinx.coroutines.*

class MyAdapterRecyclerPopular(var dataCurrencyDao: DataCurrencyDao) :
    RecyclerView.Adapter<DataCurrencyViewHolder>() {

    var listCurrency = mutableListOf<DataCurrency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCurrencyViewHolder {
        val binding: SingleItemBinding =
            SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataCurrencyViewHolder, position: Int) {
        val dataCurrency = listCurrency[position]
        holder.binding.textCurrency.text = dataCurrency.currency
        holder.binding.textValue.text = dataCurrency.value

        holder.binding.iconFavorite.setOnClickListener {

            if (dataCurrency.id == 0) {

                GlobalScope.launch(Dispatchers.Main) {
                    if (holder.binding.iconFavorite.drawable.constantState
                        != holder.binding.root.resources.getDrawable(R.drawable.favorite_icon).constantState
                    ) {
                        holder.binding.iconFavorite.setImageResource(R.drawable.favorite_icon)
                        dataCurrencyDao.insertCurrency(dataCurrency)

                    } else {
                        holder.binding.iconFavorite.setImageResource(R.drawable.unfavorite_icon)
                        dataCurrencyDao.deleteCurrencyByCurrency(dataCurrency.currency)
                    }

                }

            }

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
