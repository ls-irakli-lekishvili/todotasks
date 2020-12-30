package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cst.todotasks.R
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTodoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_todo, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            val circleOptions = it.findViewById<Button>(R.id.circle_option_done)
            circleOptions.setOnClickListener {
                doneClickHandler()
            }
        }
    }

    private fun doneClickHandler() {
        val addTitle = view?.findViewById<EditText>(R.id.adding_title)
        val addTask = view?.findViewById<EditText>(R.id.adding_task)

        when {
            addTitle!!.text.isBlank() -> {
                Toast.makeText(context, "Enter Title", Toast.LENGTH_LONG).show()
            }
            addTask!!.text.isBlank() -> {
                Toast.makeText(context, "Enter Task", Toast.LENGTH_LONG).show()
            }
            else -> {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        MainActivity.dao.insertTodo(Todo(
                            title = addTitle.text.toString(),
                            body = addTask.text.toString(),
                            isChecked = false
                        ))
                    }
                }
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }
}