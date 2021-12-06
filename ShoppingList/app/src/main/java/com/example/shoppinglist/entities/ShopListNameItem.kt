package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_list_names" ) //название таблицы
data class ShopListNameItem(
    @PrimaryKey(autoGenerate = true) //первая колона, нумерация каждой позиции автоматически заполняется
    val id: Int?,

    @ColumnInfo (name = "name") //название списка
    val name: String,

    @ColumnInfo (name = "time") //время создания списка
    val time: String,

    @ColumnInfo (name = "allItemCounter") //общее количество элиментов (покупок, которых необходимо купить)
    val allItemCount: Int,

    @ColumnInfo (name = "checkedItemCounter") //количество элментов, которые уже куплены
    val checkedItemCounter: Int,

    @ColumnInfo (name = "itemIds") //идентификаторы элементов
    val itemIds: String,

): Serializable //чтобы передать по интенту весь класс
