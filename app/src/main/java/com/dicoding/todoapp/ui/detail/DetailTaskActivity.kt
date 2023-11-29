package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.databinding.ActivityTaskDetailBinding
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding
    private val viewModel by viewModels<DetailTaskViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 11 : Show detail task and implement delete action
        val taskId = intent.getIntExtra(TASK_ID, 1)

        viewModel.setTaskId(taskId)
        viewModel.task.observe(this) { task ->
            binding.detailEdTitle.setText(task.title)
            binding.detailEdDescription.setText(task.description)
            binding.detailEdDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))

            binding.btnDeleteTask.setOnClickListener {
                viewModel.task.removeObservers(this)
                viewModel.deleteTask()

                val intent = Intent(this, TaskActivity::class.java)
                startActivity(intent)
            }
        }
    }
}