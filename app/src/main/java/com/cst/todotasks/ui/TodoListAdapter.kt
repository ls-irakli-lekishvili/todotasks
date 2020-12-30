package com.cst.todotasks.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TodoListAdapter(
    private var list: List<Todo>
): RecyclerView.Adapter<TodoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, null)
        return TodoListViewHolder(view, parent.context as MainActivity)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.setContent(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setNewLIst(list: List<Todo>) {
        this.list = list
    }


}

class TodoListViewHolder(itemView: View, private val context: MainActivity) : RecyclerView.ViewHolder(itemView) {
    private val checkbox = itemView.findViewById<CheckBox>(R.id.todo_checkbox)
    private val name = itemView.findViewById<TextView>(R.id.todo_name)
    private var id = -1

    fun setContent(todoInfo: Todo) {
        with(todoInfo) {
            name.text = title
            checkbox.isChecked = isChecked
            strikeText(isChecked)
            this@TodoListViewHolder.id = id!!
        }

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            strikeText(isChecked)
            todoInfo.isChecked = isChecked
        }

        name.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("todo", todoInfo)
            }

            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, DetailTodoFragment().apply {
                    arguments = bundle
                })
                .addToBackStack(DetailTodoFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun strikeText(hasStoke: Boolean) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                MainActivity.dao.updateChecked(id, hasStoke)
            }
        }

        if(hasStoke) {
            name.paintFlags = name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            name.paintFlags = name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

}