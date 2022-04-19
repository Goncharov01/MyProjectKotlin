package com.myproject.app.presentation.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCase
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelPopular(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val listDataCurrency = mutableListOf<DataCurrency>()
    private val getCurrencyUseCase: GetCurrencyUseCase = GetCurrencyUseCaseImpl(dataJsonRepository)

    val getCurrencyLiveData = MutableLiveData<List<DataCurrency>>()

    suspend fun getCurrencyData() {

        CoroutineScope(Dispatchers.IO).launch {

            val dataCurrency = getCurrencyUseCase.getCurrency().rates

            for (entry in dataCurrency) {
                listDataCurrency.add(
                    DataCurrency(
                        currency = entry.key,
                        value = entry.value.toString()
                    )
                )
            }

            getCurrencyLiveData.postValue(listDataCurrency)

        }

    }

}