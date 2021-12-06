package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch

class MainViewModel(dataBase: MainDataBase): ViewModel() { //класс посредник в паттерне MVVM
    val dao = dataBase.getDao()  // функция в MainDataBase (абстрактный класс MainDataBase)

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData() // считывать все наши заметки, список заметок будет постоянно обновляться
    val allShopListNameItem: LiveData<List<ShopListNameItem>> = dao.getAllShopListName().asLiveData()
    fun getAllItemsFromList(listId: Int): LiveData<List<ShopListItem>> {
        return  dao.getAllShopListItems(listId).asLiveData()
    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {//корутина класса ViewModel(), с помощью которой мы сможем запустить второстепенный поток и запишем на нем данные, не захламляя основной поток, на котором рисуем основной интерфейс
        dao.insertNote(note)
    }

    fun insertShopListName(listNameItem: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listNameItem)
    }

    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateShopListName(shopListNameItem: ShopListNameItem) = viewModelScope.launch {
        dao.updateShopListName(shopListNameItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {//корутина класса ViewModel(), с помощью которой мы сможем запустить второстепенный поток и запишем на нем данные, не захламляя основной поток, на котором рисуем основной интерфейс
        dao.deleteNote(id)
    }

    fun deleteShopListName(id: Int) = viewModelScope.launch {
        dao.deleteShoppingList(id)
    }

    class MainViewModelFactory(val database: MainDataBase) : ViewModelProvider.Factory{//этот класс инициализирует MainViewModel (прописано на офиц сайте в док-ции)
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return com.example.shoppinglist.db.MainViewModel(database) as T
            }
            throw IllegalAccessException("Inknow ViewModelClass")
        }

    }
}