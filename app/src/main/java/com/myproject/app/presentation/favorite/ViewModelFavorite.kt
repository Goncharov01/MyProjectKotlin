package com.myproject.app.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCase
import com.myproject.app.domain.usecase.selectallcurrency.SelectAllCurrencyUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelFavorite(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val selectAllCurrencyUseCase: SelectAllCurrencyUseCase =
        SelectAllCurrencyUseCaseImpl(dataJsonRepository)

    val selectAllLiveData = MutableLiveData<List<DataCurrency>>()

    suspend fun selectAllCurrencyDatabase() {

        CoroutineScope(Dispatchers.IO).launch {

            selectAllLiveData.postValue(selectAllCurrencyUseCase.selectAll())

        }

    }

}