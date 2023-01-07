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
import java.time.LocalDateTime

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
            addList.setOnClickListener { openCreationOfList() }
        }
    }

    fun createTask(id: Int, title: String, description: String, date: LocalDateTime,
                   isFavourite: Boolean, isSubtaskFor: Int, groupId: Int) {
        taskAdapter.addTask(Task(id, title, description, date, isFavourite, isSubtaskFor, groupId))
    }

    fun createGroup(id: Int, name: String) {
        groupAdapter.addGroup(Group(id, name))
    }


    fun openCreationOfTask() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_task, null)
        val tskNm = dialogLayout.findViewById<EditText>(R.id.taskName)
        val tskDesc = dialogLayout.findViewById<EditText>(R.id.taskDescription)

        with(builder) {
            setTitle("Create task")
            setPositiveButton("ok") {dialog, which ->
                if (tskNm.text.toString() != "")
                    createTask(1, tskNm.text.toString(), tskDesc.text.toString(),
                        LocalDateTime.now(), false, 0, 0)
            }
            setNegativeButton("exit") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    fun openCreationOfList() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_list, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.listName)

        with(builder) {
            setTitle("Create group")
            setPositiveButton("ok") {dialog, which ->
                if (editText.text.toString() != "")
                    createGroup(1, editText.text.toString())
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