package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "note_list") // название таблицы
data class NoteItem(
    @PrimaryKey (autoGenerate = true)//первая колона, нумерация каждой позиции автоматически заполняется
    var id: Int?,

    @ColumnInfo (name = "title") //заголовок заметки
    val title: String,

    @ColumnInfo (name = "content") //описание заметки
    val content: String,

    @ColumnInfo (name = "time") //время создание
    val time: String,

    @ColumnInfo (name = "category") //категория заметки
    val category: String
): Serializable
