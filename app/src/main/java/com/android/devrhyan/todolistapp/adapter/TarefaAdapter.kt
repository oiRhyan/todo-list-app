package com.android.devrhyan.todolistapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.devrhyan.todolistapp.R
import com.android.devrhyan.todolistapp.databinding.ItemTarefaBinding
import com.android.devrhyan.todolistapp.model.Tarefas

class TarefaAdapter(
    val onClickDelete : (Int) -> Unit,
    val onClickEdit : (Tarefas) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(){

    private var ListaTarefas = listOf<Tarefas>()

    fun addListaTarefa(list: List<Tarefas>) {
        this.ListaTarefas = list
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(binding: ItemTarefaBinding) : ViewHolder(binding.root) {

        private val support : ItemTarefaBinding

        init {
            support = binding
        }

        fun binding(tarefa : Tarefas) {
            support.textData.text = tarefa.dataCadastro
            support.textDescricao.text = tarefa.descricao

            support.btnExcluir.setOnClickListener {
                onClickDelete(tarefa.idTarefa)
            }

            support.btnEditar.setOnClickListener {
                onClickEdit(tarefa)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val layoutsupport = LayoutInflater.from(parent.context)
        val item_render = ItemTarefaBinding.inflate(layoutsupport, parent, false)
        return TarefaViewHolder(item_render)
    }

    override fun getItemCount(): Int {
        return ListaTarefas.size
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val Tarefas = ListaTarefas[position]
        holder.binding(Tarefas)
    }
}