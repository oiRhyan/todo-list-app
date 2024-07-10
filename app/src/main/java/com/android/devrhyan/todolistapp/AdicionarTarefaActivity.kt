package com.android.devrhyan.todolistapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.devrhyan.todolistapp.database.TarefaDAO
import com.android.devrhyan.todolistapp.databinding.ActivityAdicionarTarefaBinding
import com.android.devrhyan.todolistapp.model.Tarefas

class AdicionarTarefaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdicionarTarefaBinding
    var tarefa: Tarefas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        val bundle = intent.extras
        if (bundle != null && bundle.containsKey("tarefa")) {
            tarefa = bundle.getSerializable("tarefa") as Tarefas
            binding.editTarefa.setText(tarefa?.descricao)
        }
    }

    private fun salvarTarefa() {
        val descricao = binding.editTarefa.text.toString()
        if (descricao.isNotEmpty()) {
            if (tarefa != null) {
                edit(tarefa!!)
            } else {
                extracted()
            }
        } else {
            Toast.makeText(this, "Digite uma tarefa", Toast.LENGTH_LONG).show()
        }
    }

    private fun extracted() {
        val actions = TarefaDAO(this)
        val desc = binding.editTarefa.text.toString()
        val todo = Tarefas(
            -1, desc, "DATE('now')"
        )

        if (actions.salvar(todo)) {
            Toast.makeText(this, "Tarefa registrada com sucesso", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun edit(tarefa: Tarefas) {
        val desc = binding.editTarefa.text.toString()
        val todo = Tarefas(
            tarefa.idTarefa, desc, "DATE('now')"
        )
        val tarefaDao = TarefaDAO(this)

        if (tarefaDao.aualizar(todo)) {
            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
