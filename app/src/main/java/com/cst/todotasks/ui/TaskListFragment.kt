package com.cst.todotasks.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cst.todotasks.R
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TaskListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTasks()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_clear -> {
                // TODO თქვენი კოდი
                true
            }
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.active -> {
                        // TODO თქვენი კოდი
                    }
                    R.id.completed -> {
                        // TODO თქვენი კოდი
                    }
                    else -> {
                        // TODO თქვენი კოდი
                    }
                }
                true
            }
            show()
        }
    }

    private fun getTasks() {
        context?.let {

            val circleOptions = activity?.findViewById<Button>(R.id.circle_option_add)
            circleOptions?.setOnClickListener {
                addClickHandler()
            }

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    val tasks = MainActivity.dao.getAll()
                    withContext(Dispatchers.Main) {
                        if (tasks.isEmpty()) {
                            emptyTasksHandler()
                        } else {
                            showTodo(tasks)
                        }
                    }
                }
            }
        }
    }

    private fun emptyTasksHandler() {
        activity?.let {
            val allTasks = it.findViewById<TextView>(R.id.all_tasks_title)
            allTasks.visibility = TextView.GONE
        }

    }

    private fun addClickHandler() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AddTodoFragment())
            .addToBackStack(AddTodoFragment::class.java.simpleName)
            .commit()
    }

    private fun showTodo(tasks: List<Todo>) {
        activity?.let {
            val allTasks = it.findViewById<TextView>(R.id.all_tasks_title)
            val emptyHolder = it.findViewById<LinearLayout>(R.id.empty_todo_list_container)
            val recyclerView = it.findViewById<RecyclerView>(R.id.todo_recycler_view)

            allTasks.visibility = TextView.VISIBLE
            emptyHolder.visibility = LinearLayout.GONE
            recyclerView.visibility = RecyclerView.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = TodoListAdapter(tasks)
        }

    }

    companion object {

        fun createInstance() = TaskListFragment()

    }

}