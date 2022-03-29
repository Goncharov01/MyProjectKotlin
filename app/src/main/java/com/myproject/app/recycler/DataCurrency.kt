package com.myproject.app.recycler

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataCurrency(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var favorite : Boolean = false,

    var currency: String,

    var value: String
)