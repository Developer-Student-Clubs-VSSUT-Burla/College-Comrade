package com.example.tt.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tt.todo.RoomDb.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;

    private final LiveData<List<Todo>> mAllTodos;
    // creating todoViewModel which gets application as parameter
    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository= new TodoRepository(application);
        mAllTodos= mRepository.getAllTask();
    }

    //getting all task
    public LiveData<List<Todo>> getAllTask() { return mAllTodos; }
    //inserting a task
    public void insertTask(Todo todo) { mRepository.insertTask(todo); }
    // deleting a task
    public void deleteTask(long uid) { mRepository.deleteTask(uid); }
    //finishing a task
    public void finishTask(long uid) { mRepository.finishTask(uid); }
    //Done a Task
    public void taskDone(long uid,int d) { mRepository.taskDone(uid,d); }
    //getting a task
    public Todo getTask(long uid) { return mRepository.getTask(uid); }
}