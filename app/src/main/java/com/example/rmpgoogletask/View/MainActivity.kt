package com.example.rmpgoogletask.View

import android.content.Intent
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
            task0Title.setOnClickListener { setFilteredGroup(Group(-1, "")) }
            favIconBtn.setOnClickListener { taskAdapter.setFilterByFavourite() }
            removeList.setOnClickListener { openDeletionOfGroup() }

            createGroup(1, "group1")
            createGroup(2, "group2")
            createGroup(3, "group3")
            createTask(1, "task1", "desc for task1",  LocalDateTime.now(), false, 0, 1)
            createTask(2, "task2", "desc for task2 lalala lalala lalala lalala",  LocalDateTime.now(), false, 0, 2)
            createTask(3, "task3", "desc for task3 ya ne ya",  LocalDateTime.now(), false, 0, 3)
            createTask(4, "task4", "desc for task4 its my life i cant stop it",  LocalDateTime.now(), false, 0, 0)
            createTask(5, "task5", "desc for task5 lololoshka",  LocalDateTime.now(), false, 0, 0)
        }
    }

    fun createTask(id: Int, title: String, description: String, date: LocalDateTime,
                   isFavourite: Boolean, isSubtaskFor: Int, groupId: Int) {
        taskAdapter.addTask(Task(id, title, description, date, isFavourite,
            isSubtaskFor, groupId))
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
                        LocalDateTime.now(), false, 0, taskAdapter.filteredGroupId)
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

    fun openDeletionOfGroup() {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("Delete group")
            setPositiveButton("ok") {dialog, which ->
                removeGroup(groupAdapter.locGroup)
            }
            setNegativeButton("exit") { dialog, which -> }
            show()
        }
    }

    override fun addToFavourite(task: Task) {
        Toast.makeText(this, "task ${task.id}", Toast.LENGTH_SHORT).show()
    }

    override fun removeGroup(group: Group) {
        for (i in 0 until groupAdapter.groupList.size) {
            if (groupAdapter.groupList[i] == group) {
                groupAdapter.groupList.removeAt(i)
                break
            }
        }
        groupAdapter.notifyDataSetChanged()
    }

    override fun setFilteredGroup(group: Group) {
        taskAdapter.setFilterGroupId(group.id)
        groupAdapter.locGroup = group
    }

    override fun removeTask(task: Task) {
        for (i in 0 until taskAdapter.taskList.size) {
            if (taskAdapter.taskList[i] == task) {
                taskAdapter.taskList.removeAt(i);
                break
            }
        }
        for (i in 0 until taskAdapter.taskListFiltered.size) {
            if (taskAdapter.taskListFiltered[i] == task) {
                taskAdapter.taskListFiltered.removeAt(i);
                break
            }
        }
        taskAdapter.notifyDataSetChanged()
    }

    override fun goToTask(task: Task) {
        startActivity(Intent(this, TaskContentActivity::class.java).apply {
            putExtra("taskItem", task)
        })
    }
}