package com.abanoub.samir.keep.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abanoub.samir.keep.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task(name = "Task One - Important", important = true))
                dao.insert(Task(name = "Task Two - Not Important", important = false))
                dao.insert(Task(name = "Task Three - Not Important", important = false))
                dao.insert(Task(name = "Task Four - Important", important = true))
                dao.insert(Task(name = "Task Five - Not Important", important = false))
                dao.insert(Task(name = "Task Six - Important", important = true))
                dao.insert(Task(name = "Task Seven - Not Important", important = false))
                dao.insert(Task(name = "Task Eight - Not Important", important = false))
                dao.insert(Task(name = "Task Nine - Important", important = true))
                dao.insert(Task(name = "Task Ten - Not Important", important = false))
            }

        }
    }

}