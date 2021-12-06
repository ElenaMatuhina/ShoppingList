package com.example.shoppinglist.activities

import android.app.Application
import com.example.shoppinglist.db.MainDataBase

//наследуемся на основе общего класса всего приложения Application, чтобы инициализировать базу данных,для доступа к ней из любого активити
class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this)} //инициализация MainDataBase, запускает базу данных
}