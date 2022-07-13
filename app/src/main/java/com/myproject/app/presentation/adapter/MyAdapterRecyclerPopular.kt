package com.myproject.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCase
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCase
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.selectbycurrency.SelectCurrencyByCurrencyUseCaseImpl
import kotlinx.coroutines.*

class MyAdapterRecyclerPopular(context: Context) :
    RecyclerView.Adapter<DataCurrencyViewHolder>() {

    private var listCurrency = mutableListOf<DataCurrency>()
    private val dataJsonRepository: DataJsonRepository = DataJsonRepository(context)
    private val insertCurrencyUseCase: InsertCurrencyUseCase =
        InsertCurrencyUseCaseImpl(dataJsonRepository)
    private val deleteCurrencyByCurrencyUseCase: DeleteCurrencyByCurrencyUseCase =
        DeleteCurrencyByCurrencyUseCaseImpl(dataJsonRepository)
    private val selectCurrencyByCurrency = SelectCurrencyByCurrencyUseCaseImpl(dataJsonRepository)

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

            if (selectCurrencyByCurrency.selectCurrencyByCurrency(dataCurrency.currency) != null) {
                holder.binding.iconFavorite.setImageResource(R.drawable.favorite_icon)
            } else {
                holder.binding.iconFavorite.setImageResource(R.drawable.unfavorite_icon)
            }

        }

        holder.binding.iconFavorite.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                if (holder.binding.iconFavorite.drawable.constantState
                    != holder.binding.root.resources.getDrawable(R.drawable.favorite_icon).constantState
                ) {
                    dataCurrency.favorite = true
                    insertCurrencyUseCase.insertCurrency(dataCurrency)
                } else {
                    dataCurrency.favorite = false
                    deleteCurrencyByCurrencyUseCase.deleteCurrencyByCurrency(dataCurrency.currency)
                }

                notifyItemChanged(position)

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
