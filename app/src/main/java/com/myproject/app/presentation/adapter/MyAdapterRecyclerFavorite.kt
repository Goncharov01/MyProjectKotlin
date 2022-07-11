package com.myproject.app.presentation.adapter

import android.annotation.SuppressLint
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 *  Нужно вынести работу с юзкейсами и потоками во вьюмодель, потому что задача адаптера - просто связать список данных с
 *  вьюшками. Как вынести? Передавать в параметры адаптера клик листенер, а сам листенер написать во фрагменте.
 *  Он в свою очередь будет обращаться ко вьюмодели, которая будет вызывать viewModelScope и внутри него юзкейсы
 *  ViewModelScope потому что он создан специально для вьюмодели, в отличие от CoroutineScope
 *
 *  В самом адаптере мы просто берем DataCurrency текущий и проверяем его поле фейворит.
 *  Если оно true, ставим одну картинку, если false - другую
 *
 *  п.с. почитай про ListAdapter
**/

class MyAdapterRecyclerFavorite(context: Context) :
    RecyclerView.Adapter<MyViewHolderFavorite>() {

    private var listCurrency = mutableListOf<DataCurrency>()
    private val dataJsonRepository: DataJsonRepository = DataJsonRepository(context)
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase =
        DeleteCurrencyUseCaseImpl(dataJsonRepository)

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

            CoroutineScope(Dispatchers.Main).launch {
                deleteCurrencyUseCase.deleteCurrency(dataCurrency.id)
                listCurrency.remove(dataCurrency)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }

            notifyItemChanged(position)

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

class MyViewHolderFavorite(var binding: SingleItemBinding) :
    RecyclerView.ViewHolder(binding.root)
