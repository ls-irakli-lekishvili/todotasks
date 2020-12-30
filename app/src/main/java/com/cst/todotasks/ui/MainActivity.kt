package com.cst.todotasks.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.room.Room
import com.cst.todotasks.R
import com.cst.todotasks.dao.TodoDao
import com.cst.todotasks.dao.TodoDatabase
import com.cst.todotasks.extensions.replaceFragment
import com.cst.todotasks.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        initDB(this)
        replaceFragment(R.id.fragment_container, TaskListFragment.createInstance())


    }

    companion object GetDao {
        lateinit var dao: TodoDao

        fun initDB(context: Context) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo"
            ).build()
            dao = db.getTodoDao()
        }

    }

}