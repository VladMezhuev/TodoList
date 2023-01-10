package com.example.todolist.presentations.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.room.entities.TodoDbEntity
import com.example.todolist.databinding.TodoItemBinding

interface TodoDelegate {
  fun editItem(item: TodoDbEntity)
  fun toggleStatus(item: TodoDbEntity)
}

class TodoListAdapter: RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
  private val todoList =  arrayListOf<TodoDbEntity>()
  private var delegate: TodoDelegate? = null

  fun attachDelegate(delegate: TodoDelegate) {
    this.delegate = delegate
  }

  @SuppressLint("NotifyDataSetChanged")
  fun setList(list: List<TodoDbEntity>) {
    todoList.clear()
    todoList.addAll(list)

    notifyDataSetChanged()
  }

  class TodoListViewHolder(view: View, private val delegate: TodoDelegate?): RecyclerView.ViewHolder(view) {
    private val binding = TodoItemBinding.bind(view)

    fun bind(model: TodoDbEntity) = with(binding) {
      cardItemTitleTv.text = model.title
      cardItemTimeTv.text = model.createdAt
      cardItemPriorityTv.text = model.priority.toString()
      cardItemStatusIv.setImageResource(
        if (model.status) R.drawable.ic_check else R.drawable.ic_not_check
      )

      todoItem.setOnClickListener {
        delegate?.editItem(model)
      }

      cardItemStatusIv.setOnClickListener {
        delegate?.toggleStatus(model)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
    return TodoListViewHolder(view, delegate)
  }

  override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
    holder.bind(model = todoList[position])
  }

  override fun getItemCount(): Int {
    return todoList.size
  }
}