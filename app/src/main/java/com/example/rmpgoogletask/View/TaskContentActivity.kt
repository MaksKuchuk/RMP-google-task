package com.example.rmpgoogletask.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rmpgoogletask.Model.DataBase.DB.MainDB
import com.example.rmpgoogletask.Model.DataBase.Repository.TaskRepository
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.ViewModel.SubtaskAdapter
import com.example.rmpgoogletask.databinding.ActivityTaskContentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskContentActivity : AppCompatActivity(), SubtaskAdapter.Listener {
    private lateinit var binding: ActivityTaskContentBinding
    private val subtaskAdapter = SubtaskAdapter(this)
    lateinit var locTask: Task
    lateinit var db: MainDB
    lateinit var taskRepo: TaskRepository

    val subtaskObserveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val TIMEPATTERN = "dd-MM HH:mm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MainDB.getDb(this@TaskContentActivity)
        taskRepo = TaskRepository(db)

        locTask = intent.getSerializableExtra("taskItem") as Task

        val subtaskObserver = Observer<Int> {
            subtaskAdapter.notifyDataSetChanged()
        }

        subtaskObserveData.observe(this@TaskContentActivity, subtaskObserver)

        subtaskObserveData.value = 0

        initSubtasks()

        binding.apply {
            subtasksList.layoutManager = LinearLayoutManager(this@TaskContentActivity)
            subtasksList.adapter = subtaskAdapter

            taskNameContent.text = locTask.title
            taskDescriptionContent.text = locTask.description
            creationTime.text = locTask.date

            addSubtask.setOnClickListener { openCreationOfSubtask() }

            refreshNameDesc.setOnClickListener { openRefreshingOfTask() }
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
                    createSubtask(tskNm.text.toString(), tskDesc.text.toString())
            }
            setNegativeButton("exit") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    fun openRefreshingOfTask() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.create_task, null)
        val tskNm = dialogLayout.findViewById<EditText>(R.id.taskName)
        val tskDesc = dialogLayout.findViewById<EditText>(R.id.taskDescription)

        with(builder) {
            setTitle("Edit Task")
            setPositiveButton("ok") {dialog, which ->
                if (tskNm.text.toString() != "")
                    locTask.title = tskNm.text.toString()
                if (tskDesc.text.toString() != "")
                    locTask.description = tskDesc.text.toString()
                refreshTask()
            }
            setNegativeButton("exit") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    fun createSubtask(title: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            taskRepo.Create(
                Task(
                    null,
                    title,
                    description,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMEPATTERN)).toString(),
                    false,
                    locTask.id,
                    0
                )
            )
            initSubtasks()
        }
    }

    fun refreshTask() {
        binding.taskNameContent.text = locTask.title
        binding.taskDescriptionContent.text = locTask.description

        CoroutineScope(Dispatchers.IO).launch {
            taskRepo.Update(locTask)
        }
    }

    override fun removeSubtask(subtask: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskRepo.Delete(subtask)
            initSubtasks()
        }
    }

    fun initSubtasks() {
        CoroutineScope(Dispatchers.IO).launch {
            subtaskAdapter.subtaskList.clear()
            subtaskAdapter.subtaskList.addAll(taskRepo.GetItemsBySubtaskFor(locTask.id!!))
            subtaskObserveData.postValue(subtaskObserveData.value!! + 1)
        }
    }

    override fun goToSubtask(subtask: Task) {
        startActivity(Intent(this, TaskContentActivity::class.java).apply {
            putExtra("taskItem", subtask)
        })
    }
}