package com.example.rmpgoogletask.ViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rmpgoogletask.Model.Domain.Task
import com.example.rmpgoogletask.R
import com.example.rmpgoogletask.databinding.SubtaskItemBinding

class SubtaskAdapter(val listener: Listener) : RecyclerView.Adapter<SubtaskAdapter.SubtaskHolder>() {
    val subtaskList = ArrayList<Task>()

    class SubtaskHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = SubtaskItemBinding.bind(item)

        fun bind(task: Task, listener: SubtaskAdapter.Listener, position: Int) = with(binding) {
            subtaskTitle.text = task.title
            subCheckIcon.setImageResource(R.drawable.checkmark_complete_correct_svgrepo_com)

            subCheckIcon.setOnClickListener { listener.removeSubtask(task) }
            subtaskTitle.setOnClickListener { listener.goToSubtask(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subtask_item, parent, false)
        return SubtaskAdapter.SubtaskHolder(view)
    }

    override fun onBindViewHolder(holder: SubtaskHolder, position: Int) {
        holder.bind(subtaskList[position], listener, position)
    }

    override fun getItemCount(): Int {
        return subtaskList.size
    }

    fun addSubtask(task: Task) {
        subtaskList.add(task)
        notifyDataSetChanged()
    }

    interface Listener {
        fun removeSubtask(subtask: Task)
        fun goToSubtask(subtask: Task)
    }
}