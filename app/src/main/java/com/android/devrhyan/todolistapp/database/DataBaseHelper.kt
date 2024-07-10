package com.android.devrhyan.todolistapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, 1, null
){

    companion object {
        const val DATABASE_NAME = "ListaTarefas.db"
        const val NOME_TABELA = "Tarefas"
        const val ID_TAREFA = "idTarefa"
        const val DATA_TAREFA = "dataCadastro"
        const val DESCRICAO_TAREFA = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val initQuery = "CREATE TABLE IF NOT EXISTS ${NOME_TABELA} (" +
                "     ${ID_TAREFA} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "     ${DATA_TAREFA} VARCHAR(70)," +
                "     ${DESCRICAO_TAREFA} DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(initQuery)
            Log.v("DatabaseStats", "Tabela criada com sucesso")
        } catch (e: Exception) {
            Log.e("DatabaseStats", "Erro ao criar tabela ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}