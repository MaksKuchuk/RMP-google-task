package com.example.rmpgoogletask.View

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.ViewModel.TaskAdapter
import com.example.rmpgoogletask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val taskGroupList: RecyclerView = findViewById(R.id.groupList)
//        taskGroupList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        init()
    }

    private fun init() {
        binding.apply {
            tasksList.layoutManager = LinearLayoutManager(this@MainActivity)
            tasksList.adapter = taskAdapter

            addTaskIcon.setOnClickListener { openCreationOfTask() }
        }
    }

    fun createTask(id: Int, title: String, isFavourite: Boolean) {
        taskAdapter.addTask(Task(id, title, isFavourite))
    }

    fun openCreationOfTask() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_task, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.taskName)

        with(builder) {
            setTitle("Create task")
            setPositiveButton("ok") {dialog, which ->
                createTask(1, editText.text.toString(), false)
            }
            setNegativeButton("exit") { dialog, which ->}
            setView(dialogLayout)
            show()
        }
    }

    override fun addToFavourite(task: Task) {
        Toast.makeText(this, "task ${task.id}", Toast.LENGTH_SHORT).show()
    }

    override fun removeByPosition(position: Int) {
        taskAdapter.taskList.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }
}