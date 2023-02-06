package com.myproject.app.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.myproject.app.data.db.DataCurrency

class CurrencyDiffUtil(
    private val oldList: List<DataCurrency>,
    private val newList: List<DataCurrency>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, favoriteOld, _, valueOld) = oldList[oldItemPosition]
        val (_, favoriteNew, _, valueNew) = newList[newItemPosition]
        return favoriteOld == favoriteNew && valueOld == valueNew
    }
}