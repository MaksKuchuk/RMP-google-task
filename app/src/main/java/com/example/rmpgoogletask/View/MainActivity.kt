package com.example.rmpgoogletask.View

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmpgoogletask.Model.Domain.Group
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.ViewModel.GroupAdapter
import com.example.rmpgoogletask.ViewModel.TaskAdapter
import com.example.rmpgoogletask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskAdapter.Listener, GroupAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter(this)
    private val groupAdapter = GroupAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            tasksList.layoutManager = LinearLayoutManager(this@MainActivity)
            tasksList.adapter = taskAdapter

            groupList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            groupList.adapter = groupAdapter

            addTaskIcon.setOnClickListener { openCreationOfTask() }

            createGroup(1, "listaa")
            createGroup(1, "listaa 1")
            createGroup(1, "listaa 2")
            createGroup(1, "listaa 3")
        }
    }

    fun createTask(id: Int, title: String, isFavourite: Boolean) {
        taskAdapter.addTask(Task(id, title, isFavourite, 1))
    }

    fun createGroup(id: Int, name: String) {
        groupAdapter.addGroup(Group(id, name))
    }

    fun openCreationOfTask() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_task, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.taskName)

        with(builder) {
            setTitle("Create task")
            setPositiveButton("ok") {dialog, which ->
                if (editText.text.toString() != "")
                    createTask(1, editText.text.toString(), false)
            }
            setNegativeButton("exit") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    override fun addToFavourite(task: Task) {
        Toast.makeText(this, "task ${task.id}", Toast.LENGTH_SHORT).show()
    }

    override fun toGroup(group: Group) {
        Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show()
    }

    override fun removeGroupByPosition(position: Int) {
        groupAdapter.groupList.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }

    override fun removeTaskByPosition(position: Int) {
        taskAdapter.taskList.removeAt(position)
        taskAdapter.notifyDataSetChanged()
    }
}