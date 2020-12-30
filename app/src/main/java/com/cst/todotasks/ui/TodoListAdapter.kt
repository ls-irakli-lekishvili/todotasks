package com.cst.todotasks.ui

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.models.Todo


class TodoListAdapter(
    private val list: List<Todo>
): RecyclerView.Adapter<TodoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, null)
        return TodoListViewHolder(view, parent.context as MainActivity)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.setContent(list[position])
    }

    override fun getItemCount(): Int = list.size

}

class TodoListViewHolder(itemView: View, private val context: MainActivity) : RecyclerView.ViewHolder(itemView) {
    private val checkbox = itemView.findViewById<CheckBox>(R.id.todo_checkbox)
    private val name = itemView.findViewById<TextView>(R.id.todo_name)

    fun setContent(todoInfo: Todo) {
        with(todoInfo) {
            name.text = title
            checkbox.isChecked = isChecked
            strikeText(isChecked)
        }

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            strikeText(isChecked)
        }

        name.setOnClickListener {

            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, EditTodoFragment())
                .addToBackStack(EditTodoFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun strikeText(hasStoke: Boolean) {
        if(hasStoke) {
            name.paintFlags = name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//            MainActivity.dao.updateTodo()
        } else {
            name.paintFlags = name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

}