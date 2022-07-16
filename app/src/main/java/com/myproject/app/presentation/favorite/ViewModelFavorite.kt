package com.myproject.app.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCase
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCase
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelFavorite(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val selectAllCurrencyUseCase: SelectAllCurrencyUseCase =
        SelectAllCurrencyUseCaseImpl(dataJsonRepository)

    private val deleteCurrencyUseCase: DeleteCurrencyUseCase =
        DeleteCurrencyUseCaseImpl(dataJsonRepository)

    val selectAllLiveData = MutableLiveData<List<DataCurrency>>()

    suspend fun selectAllCurrencyDatabase() {

        viewModelScope.launch(Dispatchers.IO) {

            selectAllLiveData.postValue(selectAllCurrencyUseCase.selectAll())

        }

    }

    suspend fun deleteFavoriteCurrency(dataCurrencyId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            deleteCurrencyUseCase.deleteCurrency(dataCurrencyId)

        }

    }

}