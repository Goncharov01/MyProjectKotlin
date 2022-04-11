package com.myproject.app.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["currency"], unique = true)]
)
data class DataCurrency(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var favorite: Boolean = false,

    var currency: String,

    var value: String
)