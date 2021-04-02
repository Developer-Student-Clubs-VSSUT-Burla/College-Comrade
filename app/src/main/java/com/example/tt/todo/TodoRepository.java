package com.example.tt.todo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.tt.todo.RoomDb.Todo;
import com.example.tt.todo.RoomDb.TodoDao;
import com.example.tt.todo.RoomDb.TodoDatabase;
import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    TodoRepository(Application application) {
        TodoDatabase db = TodoDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTask();
    }

   // getting all task of the table
    LiveData<List<Todo>> getAllTask() {
        return mAllTodos;
    }

    // inserting a todo_task into the Todo_Table of the database on a non-UI thread.
    void insertTask(Todo todo) {
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.insertTask(todo);
        });
    }

    // deleting a todo_task from the Todo_table of the database on a non-UI thread.
    void deleteTask(long uid) {
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.deleteTask(uid);
        });
    }

    // deleting a todo_task of the Todo_table of the database on a non-UI thread.
    void finishTask(long uid) {
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            mTodoDao.finishTask(uid);
        });
    }

}
