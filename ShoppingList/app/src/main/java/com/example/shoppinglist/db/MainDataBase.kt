package com.example.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem


@Database (entities = [LibraryItem::class, NoteItem::class, ShopListItem::class, ShopListNameItem::class], version = 1) //все таблицы, которые будут в базе данных
abstract class MainDataBase: RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{ // можем использовать переменные и функции без инициализации класса (например, MainDataBase.INSTANCE)
        @Volatile //анатация нужна для того чтобы INSTANCE стала доступна для всех потоков, гарантируют, что считываемое значение поступает из основной памяти, а не из кэша процессора, поэтому все участники процесса будут ожидать окончания параллельной записи, прежде чем считать значение.
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase{
            return INSTANCE ?: synchronized(this) { //если несколько потоков пытаются создать базу данных, данная функция осталяет только один потом, остальные блокирует
                val instance = Room.databaseBuilder( //если инстанция = null, запускается код, если инстанция есть- значит берем ее
                    context.applicationContext,
                    MainDataBase::class.java,
                "shopping_list.db"
                ).build()
                instance
            }
        }
    }

}