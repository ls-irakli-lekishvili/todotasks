package com.cst.todotasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cst.todotasks.R
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTodoFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detail_todo, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todo = arguments?.getParcelable<Todo>("todo")
        setContent(todo as Todo)
    }

    private fun setContent(todo: Todo) {
        view?.let {
            val titleView = it.findViewById<TextView>(R.id.title_edit_view)
            val bodyView = it.findViewById<TextView>(R.id.body_edit_view)
            val checkBox = it.findViewById<CheckBox>(R.id.checkbox_edit)
            val button = it.findViewById<Button>(R.id.circle_option_edit)

            titleView.text = todo.title
            bodyView.text = todo.body
            checkBox.isChecked = todo.isChecked


            button.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("todo", todo)
                }

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, EditTodoFragment().apply {
                        arguments = bundle
                    })
                    .addToBackStack(EditTodoFragment::class.java.simpleName)
                    .commit()

            }
            
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        MainActivity.dao.updateChecked(todo.id!!, isChecked)
                    }
                }

                if(isChecked) {
                    Toast.makeText(context, "task marked complete", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "task marked active", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}