package com.example.shoppinglist.fragments

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.activities.MainApp
import com.example.shoppinglist.activities.NewNoteActivity
import com.example.shoppinglist.databinding.FragmentNoteBinding
import com.example.shoppinglist.db.MainViewModel
import com.example.shoppinglist.db.NoteAdapter
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragments.NoteFragment

class NoteFragment : BaseFragment(), NoteAdapter.Listener {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent> //получения результата, тип  данных для передачи <Intent>
    private lateinit var adapter: NoteAdapter //сюда запишем адаптер после инициализации

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database) // на уровне всего приложения есть класс MainApp, где инициализированная база данных
    }

    override fun onClickNew() { //абстрактная функция класса BaseFragment
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.allNotes.observe(this, {
            it
        })
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//функция, запускается, когда все View созданы
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteAdapter(this@NoteFragment) //инициализация, создаем экземляр класса
        rcViewNote.adapter = adapter //адаптер, который будет обновлять rcViewNote
    }

    private fun observer() {//будет следить за изменениями в базе данных и будет выдавать нам новый обновленный список
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)

        })
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult( //регистрация контракта в активности или фрагменте с помощью вызова registerForActivityResult(). В параметры необходимо передать ActivityResultContract и ActivityResultCallback. Коллбек сработает при получении результата. Регистрация контракта не запускает новую Activity, а лишь возвращает специальный объект ActivityResultLauncher
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if(editState == "update") {
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)//insert из Dao, отправляем и получаем класс как байты, потом отражаем в классе
                } else {
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)//insert из Dao, отправляем и получаем класс как байты, потом отражаем в классе
                }

            }
        }
    }


    override fun deleteItem(id: Int) { //функция для удаления заметок
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}

