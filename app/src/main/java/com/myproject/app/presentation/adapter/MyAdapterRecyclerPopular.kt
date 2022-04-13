package com.myproject.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myproject.app.R
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.databinding.SingleItemBinding
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCase
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCase
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCase
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCaseImpl
import kotlinx.coroutines.*

class MyAdapterRecyclerPopular(var context: Context) :
    RecyclerView.Adapter<DataCurrencyViewHolder>() {

    private var listCurrency = mutableListOf<DataCurrency>()
    private val dataJsonRepository: DataJsonRepository = DataJsonRepository(context)
    private val insertCurrencyUseCase: InsertCurrencyUseCase =
        InsertCurrencyUseCaseImpl(dataJsonRepository)
    private val deleteCurrencyByCurrencyUseCase: DeleteCurrencyByCurrencyUseCase =
        DeleteCurrencyByCurrencyUseCaseImpl(dataJsonRepository)

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
                        insertCurrencyUseCase.insertCurrency(dataCurrency)

                    } else {
                        holder.binding.iconFavorite.setImageResource(R.drawable.unfavorite_icon)
                        deleteCurrencyByCurrencyUseCase.deleteCurrencyByCurrency(dataCurrency.currency)
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
