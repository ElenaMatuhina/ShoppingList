package com.example.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow


@Dao // методы работы с базой данных (data access object - объект доступа к данным)
interface Dao {

    @Query("SELECT * FROM note_list")//запрос для считывания, синтаксис SQL
    fun getAllNotes(): Flow<List<NoteItem>>//flow - специальный класс в корутинах,обновляет список с нашими записками, если есть изменения в базе данных

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListName(): Flow<List<ShopListNameItem>>

    @Query("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>

    @Query("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Query("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShoppingList(id: Int)


    @Insert
    suspend fun insertNote(note: NoteItem) //функцию будем запускать внутри корутины, поэтому "suspend"

    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)

    @Insert
    suspend fun insertShopListName(nameItem: ShopListNameItem)


    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateShopListName(nameItem: ShopListNameItem)
}