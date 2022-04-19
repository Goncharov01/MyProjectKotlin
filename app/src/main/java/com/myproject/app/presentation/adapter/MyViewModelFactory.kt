package com.myproject.app.presentation.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myproject.app.data.DataJsonRepository
import com.myproject.app.presentation.favorite.ViewModelFavorite
import com.myproject.app.presentation.popular.ViewModelPopular

class MyViewModelFactory(private val repository: DataJsonRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelFavorite::class.java)) {
            ViewModelFavorite(this.repository) as T
        } else if (modelClass.isAssignableFrom(ViewModelPopular::class.java)) {
            ViewModelPopular(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}