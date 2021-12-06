package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "shop_list_item") //название таблицы
data class ShopListItem(
    @PrimaryKey (autoGenerate = true) //нумерация каждой позиции автоматически заполняется
    val id: Int?,

    @ColumnInfo (name = "name") //название продукта
    val name: String,

    @ColumnInfo (name = "itemInfo") //дополнительная инфа, возможна будет ровна null, если пользователь не хочет добавлять доп информацию
    val itemInfo: String?,

    @ColumnInfo (name = "itemChecked") //продукт куплен или нет, изначально = 0
    val itemChecked: Int = 0,

    @ColumnInfo (name = "listId") // идентификатор списка
    val listId:Int,

    @ColumnInfo (name = "itemType") //для библиотеки, которая будет давать подсказку
    val itemType: Int = 0
 )