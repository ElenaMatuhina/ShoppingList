package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "library") //название таблицы библиотеки, которая будет давать подсказку при последующих написаниях item
data class LibraryItem(
    @PrimaryKey (autoGenerate = true) // нумерация каждой позиции автоматически заполняется
    val id: Int?,

    @ColumnInfo (name = "name") //название элемента
    val name: String
)
