package com.example.rmpgoogletask.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.ViewModel.SubtaskAdapter
import com.example.rmpgoogletask.databinding.ActivityTaskContentBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskContentActivity : AppCompatActivity(), SubtaskAdapter.Listener {
    private lateinit var binding: ActivityTaskContentBinding
    private val subtaskAdapter = SubtaskAdapter(this)
    lateinit var locTask: Task
    val TIMEPATTERN = "dd-MM HH:mm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locTask = intent.getSerializableExtra("taskItem") as Task

        binding.apply {
            subtasksList.layoutManager = LinearLayoutManager(this@TaskContentActivity)
            subtasksList.adapter = subtaskAdapter

            taskNameContent.text = locTask.title
            taskDescriptionContent.text = locTask.description
            creationTime.text = locTask.date.format(DateTimeFormatter.ofPattern(TIMEPATTERN)).toString()

            addSubtask.setOnClickListener { openCreationOfSubtask() }
        }
    }

    fun openCreationOfSubtask() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_task, null)
        val tskNm = dialogLayout.findViewById<EditText>(R.id.taskName)
        val tskDesc = dialogLayout.findViewById<EditText>(R.id.taskDescription)

        with(builder) {
            setTitle("Create task")
            setPositiveButton("ok") {dialog, which ->
                if (tskNm.text.toString() != "")
                    createSubtask(1, tskNm.text.toString(), tskDesc.text.toString(),
                        LocalDateTime.now(), false, locTask.id, -1)
            }
            setNegativeButton("exit") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    fun createSubtask(id: Int, title: String, description: String, date: LocalDateTime,
                      isFavourite: Boolean, isSubtaskFor: Int, groupId: Int) {
        subtaskAdapter.addSubtask(Task(id, title, description, date, isFavourite,
            isSubtaskFor, groupId))
    }

    override fun removeSubtask(subtask: Task) {
        for (i in 0 until subtaskAdapter.subtaskList.size) {
            if (subtaskAdapter.subtaskList[i] == subtask) {
                subtaskAdapter.subtaskList.removeAt(i);
                break
            }
        }
        subtaskAdapter.notifyDataSetChanged()
    }

    override fun goToSubtask(subtask: Task) {
        startActivity(Intent(this, TaskContentActivity::class.java).apply {
            putExtra("taskItem", subtask)
        })
    }
}