package com.myproject.app.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.room.DataCurrencyDao
import kotlinx.coroutines.*

class MyAdapterRecycler(var dataCurrencyDao: DataCurrencyDao) :
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

        holder.binding.myLinerLayout.setOnClickListener {

            if (dataCurrency.id == 0 && dataCurrency.favorite == false) {

                GlobalScope.launch(Dispatchers.Main) {
                    dataCurrencyDao.insertCurrency(dataCurrency)
                }

            } else {

                GlobalScope.launch(Dispatchers.Main) {
                    dataCurrencyDao.deleteCurrency(dataCurrency.id)
                    listCurrency.remove(dataCurrency)
                    dataCurrency.favorite = false
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                }

            }

            notifyDataSetChanged()
            notifyItemChanged(position)

        }

    }

    override fun getItemCount(): Int {
        return listCurrency.size
    }


    fun addList(listCurrency: List<DataCurrency>) {
        this.listCurrency.clear()
        this.listCurrency.addAll(listCurrency)
        notifyDataSetChanged()
    }

}

class DataCurrencyViewHolder(var binding: SingleItemBinding) :
    RecyclerView.ViewHolder(binding.root)
