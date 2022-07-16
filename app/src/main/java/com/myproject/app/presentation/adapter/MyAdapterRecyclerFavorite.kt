package com.myproject.app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.data.db.DataCurrency

class MyAdapterRecyclerFavorite(
    private val clickListener: (DataCurrency) -> Unit
) : RecyclerView.Adapter<MyViewHolderFavorite>() {

    private var listCurrency = mutableListOf<DataCurrency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFavorite {
        val binding: SingleItemBinding =
            SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolderFavorite(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolderFavorite, position: Int) {
        val dataCurrency = listCurrency[position]
        holder.binding.textValue.text = dataCurrency.value
        holder.binding.textCurrency.text = dataCurrency.currency
        holder.binding.iconFavorite.setImageResource(R.drawable.favorite_icon)

        holder.binding.iconFavorite.setOnClickListener {
            clickListener(dataCurrency)
            listCurrency.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listCurrency.size)
        }
    }

    override fun getItemCount(): Int {
        return listCurrency.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(listCurrency: List<DataCurrency>) {
        this.listCurrency.clear()
        this.listCurrency.addAll(listCurrency)
        notifyDataSetChanged()
    }

}

class MyViewHolderFavorite(
    var binding: SingleItemBinding,
) : RecyclerView.ViewHolder(binding.root)