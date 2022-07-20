package com.myproject.app.presentation.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCase
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCase
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCase
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.selectbycurrency.SelectCurrencyByCurrencyUseCaseImpl
import kotlinx.coroutines.*

class ViewModelPopular(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val listDataCurrency = mutableListOf<DataCurrency>()
    private val getCurrencyUseCase: GetCurrencyUseCase = GetCurrencyUseCaseImpl(dataJsonRepository)
    private val insertCurrencyUseCase: InsertCurrencyUseCase =
        InsertCurrencyUseCaseImpl(dataJsonRepository)
    private val selectCurrencyByCurrencyUseCase: SelectCurrencyByCurrencyUseCaseImpl =
        SelectCurrencyByCurrencyUseCaseImpl(dataJsonRepository)
    private val deleteCurrencyByCurrencyUseCase: DeleteCurrencyByCurrencyUseCase =
        DeleteCurrencyByCurrencyUseCaseImpl(dataJsonRepository)

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

    suspend fun insertCurrencyInDataBase(dataCurrency: DataCurrency) {

        viewModelScope.launch(Dispatchers.IO) {

            insertCurrencyUseCase.insertCurrency(dataCurrency)

        }

    }

    suspend fun deleteCurrencyInDataBase(currency: String) {

        viewModelScope.launch(Dispatchers.IO) {

            deleteCurrencyByCurrencyUseCase.deleteCurrencyByCurrency(currency)

        }

    }

    suspend fun selectCurrencyInDataBase(currency: String): DataCurrency {

        val dataCurrency = viewModelScope.async(Dispatchers.IO) {

            selectCurrencyByCurrencyUseCase.selectCurrencyByCurrency(currency)

        }.await()

        return dataCurrency
    }

}