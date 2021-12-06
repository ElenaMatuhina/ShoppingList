package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem

class ShopListNameAdapter(private val listener: Listener): ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComparator()) { //указываем в <> какой элемент будет в списке и спец класс, который будет создавать и содержать всю разметку

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder { 
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem: ShopListNameItem, listener: Listener)= with(binding) {// передаем в этой функцию данные NoteItem, данная функция будет заполнять разметку
            tvTitleShopList.text = shopListNameItem.name // присваиваем данным разметки данные NoteIte
            tvTime.text = shopListNameItem.time // присваиваем данным разметки данные NoteItem
            itemView.setOnClickListener{
                listener.onClickItem(shopListNameItem)
            }
            imDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }

            imEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }

        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context). //ссылка на загруженную в память разметку
                inflate(R.layout.list_name_item, parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() { //сравниваем записи по id
        override fun areItemsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean { // сравниваем полностью item
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopListNameItem: ShopListNameItem)//удаление по индентификатору
        fun onClickItem(shopListNameItem: ShopListNameItem) //редактирование всей заметки


    }
}