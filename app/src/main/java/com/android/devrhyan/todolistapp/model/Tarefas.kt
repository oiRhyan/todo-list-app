package com.android.devrhyan.todolistapp.model

import java.io.Serializable

data class Tarefas (
    val idTarefa : Int,
    val descricao : String,
    val dataCadastro : String
) : Serializable