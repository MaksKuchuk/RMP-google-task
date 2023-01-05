package com.example.rmpgoogletask.ViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.databinding.TaskItemBinding

class TaskAdapter(val listener: Listener): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    val taskList = ArrayList<Task>()
    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = TaskItemBinding.bind(item)
        lateinit var adapter: TaskAdapter

        fun bind(task: Task, listener: Listener, position: Int) = with(binding){

            checkIcon.setImageResource(R.drawable.checkmark_complete_correct_svgrepo_com)

            checkIcon.setOnClickListener {
                listener.removeByPosition(position)
            }

            favouriteBtnIcon.setOnClickListener {
                listener.addToFavourite(task)
                task.isFavourite = !task.isFavourite
                if (task.isFavourite) {
                    favouriteBtnIcon.setImageResource(R.drawable.star_svgrepo_yellow_com);
                } else {
                    favouriteBtnIcon.setImageResource(R.drawable.star_svgrepo_com)
                }
            }

            if (task.isFavourite) {
                favouriteBtnIcon.setImageResource(R.drawable.star_svgrepo_yellow_com);
            } else {
                favouriteBtnIcon.setImageResource(R.drawable.star_svgrepo_com)
            }

            taskTitle.text = task.title;
        }

        fun setAdap(taskAdapter: TaskAdapter) {
            adapter = taskAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position], listener, position);
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

    fun removeTask(position: Int) {
        taskList.removeAt(position)
    }

    interface Listener {
        fun addToFavourite(task: Task)
        fun removeByPosition(position: Int)
    }
}