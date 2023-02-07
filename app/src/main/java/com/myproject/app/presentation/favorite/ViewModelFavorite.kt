package com.myproject.app.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.data.db.DataCurrency
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCase
import com.myproject.app.domain.usecase.deleteCurrency.DeleteCurrencyUseCaseImpl
import com.myproject.app.domain.usecase.selecttfavoritecarrency.SelectFavoriteCurrencyUseCase
import com.myproject.app.domain.usecase.selecttfavoritecarrency.SelectFavoriteCurrencyUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelFavorite(var dataJsonRepository: DataJsonRepository) : ViewModel() {

    private val selectFavoriteCurrency: SelectFavoriteCurrencyUseCase =
        SelectFavoriteCurrencyUseCaseImpl(dataJsonRepository)

    private val deleteCurrencyUseCase: DeleteCurrencyUseCase =
        DeleteCurrencyUseCaseImpl(dataJsonRepository)

    val favoriteCurrency = MutableLiveData<MutableList<DataCurrency>>()

    suspend fun selectAllCurrencyDatabase() {

        viewModelScope.launch(Dispatchers.IO) {

            favoriteCurrency.postValue(selectFavoriteCurrency.selectFavoriteCurrency())

        }

    }

    suspend fun deleteFavoriteCurrency(dataCurrencyId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            deleteCurrencyUseCase.deleteCurrency(dataCurrencyId)

        }

    }

}