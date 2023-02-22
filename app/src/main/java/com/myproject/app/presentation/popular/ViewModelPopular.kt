package com.myproject.app.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.myproject.app.data.CurrencyPagingSource
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCase
import com.myproject.app.domain.usecase.deletecurrencybycurrency.DeleteCurrencyByCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCase
import com.myproject.app.domain.usecase.getcurrency.GetCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCase
import com.myproject.app.domain.usecase.insertcurrency.InsertCurrencyUseCaseImpl
import com.myproject.app.presentation.util.SingleLiveEvent
import kotlinx.coroutines.*

class ViewModelPopular(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val getCurrencyUseCase: GetCurrencyUseCase = GetCurrencyUseCaseImpl(dataJsonRepository)
    private val insertCurrencyUseCase: InsertCurrencyUseCase =
        InsertCurrencyUseCaseImpl(dataJsonRepository)
    private val deleteCurrencyByCurrencyUseCase: DeleteCurrencyByCurrencyUseCase =
        DeleteCurrencyByCurrencyUseCaseImpl(dataJsonRepository)

    private val getCurrencyLiveData = SingleLiveEvent<List<DataCurrency>>()

    val currencyGetList = Pager(PagingConfig(5)) {
        CurrencyPagingSource(dataJsonRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun getCurrencyData() {

        val getCurrencyFromDataBaseOrNetwork = getCurrencyUseCase.getCurrency()
        getCurrencyLiveData.postValue(getCurrencyFromDataBaseOrNetwork)

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

}