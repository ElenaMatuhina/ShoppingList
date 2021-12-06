package com.example.shoppinglist.fragments


import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R

object FragmentManager { //если object мы можем использовать функцию без инициализации класса
    var currentFrag: BaseFragment? = null //работа с текущим активным фрагментом

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) { //функция при помощи которой мы будем переключаться между активити
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)//место (FrameLayout - R.id.placeHolder) куда будем перемещать наш фрагмент  - newFrag
        transaction.commit() //применить все действия, которые мы указали
        currentFrag = newFrag
    }
}