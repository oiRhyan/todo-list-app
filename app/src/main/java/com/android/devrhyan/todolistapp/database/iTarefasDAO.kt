package com.android.devrhyan.todolistapp.database

import com.android.devrhyan.todolistapp.model.Tarefas

interface iTarefasDAO {
    fun salvar(item : Tarefas) : Boolean
    fun aualizar(item: Tarefas) : Boolean
    fun remover(itemID: Int) : Boolean
    fun listar() : List<Tarefas>
}