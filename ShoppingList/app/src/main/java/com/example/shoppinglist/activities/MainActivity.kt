package com.example.shoppinglist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.fragments.FragmentManager
import com.example.shoppinglist.fragments.NoteFragment
import com.example.shoppinglist.fragments.NoteFragment.Companion.newInstance
import com.example.shoppinglist.fragments.ShopListNamesFragment
import androidx.fragment.app.FragmentManager as Frag



class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener() //запуск функции
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
    }

    private fun setBottomNavListener(){ //функция для работы с элементами навигации
        binding.bNav.setOnItemSelectedListener  {
            when(it.itemId){
                R.id.settings->{
                    Toast.makeText(this,"Настройки", Toast.LENGTH_LONG).show()
                }
                R.id.notes->{
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list->{
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.new_item->{
                    FragmentManager.currentFrag?.onClickNew()  //currentFrag и NoteFragment наследуются от BaseFragment

                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        TODO("Not yet implemented")
    }

}