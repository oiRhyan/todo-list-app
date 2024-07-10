package com.android.devrhyan.todolistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.devrhyan.todolistapp.adapter.TarefaAdapter
import com.android.devrhyan.todolistapp.database.TarefaDAO
import com.android.devrhyan.todolistapp.databinding.ActivityMainBinding
import com.android.devrhyan.todolistapp.model.Tarefas

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var tarefaLista = emptyList<Tarefas>()
    private var tarefaAdapter : TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tarefaAdapter = TarefaAdapter(
            { id ->
                confirmDelete(id)
            }, {tarefa -> setEditTask(tarefa)}
        )
        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)


        binding.fabButton.setOnClickListener {
            val Intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(Intent)
        }
    }

    private fun updateList() {
        val TarefaDAO = TarefaDAO(this)
        tarefaLista = TarefaDAO.listar()
        tarefaAdapter?.addListaTarefa(tarefaLista)
    }

    private fun setEditTask(tarefa : Tarefas){
        val  EditIntent = Intent(this, AdicionarTarefaActivity::class.java)
        EditIntent.putExtra("tarefa", tarefa)
        startActivity(EditIntent)
    }


    private fun confirmDelete(id : Int) {
        val AlertBuilder = AlertDialog.Builder(this)

        AlertBuilder.setTitle("Confirmar Exclusão")
        AlertBuilder.setMessage("Deseja realmente excluir esta tarefa?")
        AlertBuilder.setPositiveButton("Sim"){ _, _ ->
              val tarefaDAO = TarefaDAO(this)

              if(tarefaDAO.remover(id)) {
                  updateList()
                  Toast.makeText(this, "Tarefa Removida", Toast.LENGTH_LONG).show()
              } else {
                  Toast.makeText(this, "Erro ao remover tarefa", Toast.LENGTH_LONG).show()
              }
        }
        AlertBuilder.setNegativeButton("Não"){ _, _ ->

        }
        AlertBuilder.create().show()
    }

    override fun onStart() {
        super.onStart()
        updateList()
    }
}