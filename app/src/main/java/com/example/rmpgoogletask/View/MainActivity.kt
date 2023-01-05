package com.example.rmpgoogletask.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmpgoogletask.Model.Domain.Task
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
        }

        taskAdapter.addTask(Task(1, "task 1111", true))
        taskAdapter.addTask(Task(2, "task 2", false))
        taskAdapter.addTask(Task(3, "task 3", true))
        for (i in 1..100) {
            taskAdapter.addTask(Task(4, "task ${i}", false))
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