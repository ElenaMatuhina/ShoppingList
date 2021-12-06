package com.example.shoppinglist.db

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.databinding.ShopLibListItemBinding
import com.example.shoppinglist.databinding.ShopListItemBinding
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.entities.ShopListItem

class ShopListNameItemAdapter(private val listener: Listener): ListAdapter<ShopListItem, ShopListNameItemAdapter.ItemHolder>(ItemComparator()) { //указываем в <> какой элемент будет в списке и спец класс, который будет создавать и содержать всю разметку

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder { 
        return if(viewType == 0)
            ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       if(getItem(position).itemType == 0) {
           holder.setItemData(getItem(position),listener)
       }else {
           holder.setLibraryData(getItem(position), listener)
       }
    }

override fun getItemViewType(position: Int): Int {
    return getItem(position).itemType
}


    class ItemHolder(val view: View): RecyclerView.ViewHolder(view) {


        fun setItemData(shopListItem: ShopListItem, listener: Listener) {// передаем в этой функцию данные NoteItem, данная функция будет заполнять разметку
            val binding = ShopListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
                checkBox.setOnClickListener {
                    setPaintFlagAndColor(binding)
                }
            }
        }

        fun setLibraryData(shopListNameItem: ShopListItem, listener: Listener) {// передаем в этой функцию данные NoteItem, данная функция будет заполнять разметку

        }

        private fun setPaintFlagAndColor(binding: ShopListItemBinding) {
            binding.apply {
                if (checkBox.isChecked) {
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey))
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey))
                } else {
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                }
            }
        }

        fun infoVisibility(shopListItem: ShopListItem): Int {
            return if (shopListItem.itemInfo.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        companion object{
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context). //ссылка на загруженную в память разметку
                inflate(R.layout.shop_list_item, parent, false))
            }
            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context). //ссылка на загруженную в память разметку
                inflate(R.layout.shop_lib_list_item, parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() { //сравниваем записи по id
        override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean { // сравниваем полностью item
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopListNameItem: ShopListNameItem)//удаление по индентификатору
        fun onClickItem(shopListNameItem: ShopListNameItem) //редактирование всей заметки


    }
}