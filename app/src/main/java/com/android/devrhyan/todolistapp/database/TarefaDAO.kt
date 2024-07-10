package com.android.devrhyan.todolistapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.android.devrhyan.todolistapp.model.Tarefas

class TarefaDAO(context : Context): iTarefasDAO {

    val handler = DataBaseHelper(context).writableDatabase
    val reader = DataBaseHelper(context).readableDatabase

    override fun salvar(item: Tarefas): Boolean {
        val values = ContentValues()
        values.put("descricao", item.descricao)

        try {
            handler.insert(DataBaseHelper.NOME_TABELA, null, values)
            Log.v("Cadastrar Tarefa", "Tarefa Cadastrada com Sucesso")
        }catch (e: Exception) {
            Log.e("Cadastrar Tarefa", "Erro ao cadastrar tarefa ${e.message}")
            return false
        }
        return true
    }

    override fun aualizar(item: Tarefas): Boolean {
        val args = arrayOf(item.idTarefa.toString())
        val content = ContentValues()

        content.put(DataBaseHelper.DESCRICAO_TAREFA, item.descricao)

        try {
            handler.update(DataBaseHelper.NOME_TABELA,
                 content,
                "${DataBaseHelper.ID_TAREFA} = ?",
                args
            )
        } catch (e: Exception){
            Log.e("Erro ao atualizar tarefa", "Erro ao atualizar tarefa")
            return false
        }

        return true
    }

    override fun remover(itemID: Int): Boolean {
        val args = arrayOf(itemID.toString())
         try {
             handler.delete(DataBaseHelper.NOME_TABELA,
                 "${DataBaseHelper.ID_TAREFA} = ?",
                 args
             )
         } catch (e: Exception){
             Log.e("Erro Delete", "Erro ao remover tarefa")
             return false
         }
        return true
    }

    override fun listar(): List<Tarefas> {
        val listTarefas = mutableListOf<Tarefas>()

        val sql = "SELECT ${DataBaseHelper.ID_TAREFA}, ${DataBaseHelper.DESCRICAO_TAREFA}, strftime('%d/%m/%Y - %H:%M:%S') ${DataBaseHelper.DATA_TAREFA} from Tarefas;"
        val cursor = reader.rawQuery(sql, null)

        val indexDesc = cursor.getColumnIndex(DataBaseHelper.DESCRICAO_TAREFA)
        val indexID = cursor.getColumnIndex(DataBaseHelper.ID_TAREFA)
        val indexData = cursor.getColumnIndex(DataBaseHelper.DATA_TAREFA)

        while(cursor.moveToNext()){
            val desc = cursor.getString(indexDesc)
            val id = cursor.getInt(indexID)
            val data = cursor.getString(indexData)

            listTarefas.add(
                Tarefas(id, desc, data)
            )
        }

        return listTarefas
    }
}