package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.NoteListItemBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.utilities.HtmlManager

class NoteAdapter(private val listener: Listener): ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) { //указываем в <> какой элемент будет в списке и спец класс, который будет создавать и содержать всю разметку

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder { 
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)

        fun setData(note: NoteItem, listener: Listener)= with(binding) {// передаем в этой функцию данные NoteItem, данная функция будет заполнять разметку
            tvTitle.text = note.title // присваиваем данным разметки данные NoteItem
            tvDesc.text = HtmlManager.getFromHtml(note.content) // присваиваем данным разметки данные NoteItem
            textTime.text = note.time // присваиваем данным разметки данные NoteItem
            itemView.setOnClickListener{
                listener.onClickItem(note)
            }
            imDelete.setOnClickListener{
                listener.deleteItem(note.id!!)

            }
        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context). //ссылка на загруженную в память разметку
                inflate(R.layout.note_list_item, parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<NoteItem>() { //сравниваем записи по id
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean { // сравниваем полностью item
            return oldItem == newItem
        }
    }

    interface Listener{
        fun deleteItem(id:Int) //удаление по индентификатору
        fun onClickItem(note: NoteItem) //редактирование всей заметки

    }


}