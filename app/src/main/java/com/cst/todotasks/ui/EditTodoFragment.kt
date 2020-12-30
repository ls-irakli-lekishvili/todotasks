package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cst.todotasks.R
import com.cst.todotasks.extensions.toEditable
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTodoFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edit_todo, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavBar()

        val todo = arguments?.getParcelable<Todo>("todo")
        setContent(todo as Todo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpNavBar() {
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Edit Task"
    }

    private fun setContent(todo: Todo) {
        view?.let {
            val titleView = it.findViewById<EditText>(R.id.editing_title)
            val bodyView = it.findViewById<EditText>(R.id.editing_task)
            val button = it.findViewById<Button>(R.id.circle_option_done_edit)

            titleView.text = todo.title.toEditable()
            bodyView.text = todo.body.toEditable()

            button.setOnClickListener {
                when {
                    titleView!!.text.isBlank() -> {
                        Toast.makeText(context, "Enter Title", Toast.LENGTH_LONG).show()
                    }
                    bodyView!!.text.isBlank() -> {
                        Toast.makeText(context, "Enter Task", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        todo.title = titleView.text.toString()
                        todo.body = bodyView.text.toString()
                        GlobalScope.launch {
                            withContext(Dispatchers.IO) {
                                MainActivity.dao.updateTodo(todo)
                            }
                        }
                        activity?.supportFragmentManager?.popBackStack()
                    }
                }
            }

        }

    }
}