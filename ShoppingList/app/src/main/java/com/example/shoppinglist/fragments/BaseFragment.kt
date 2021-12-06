package com.example.shoppinglist.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() { //функция, которая добавляет новый фрагмент(полиморфизм)
    abstract fun onClickNew()
}