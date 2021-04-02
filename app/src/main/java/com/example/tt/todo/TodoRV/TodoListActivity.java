package com.example.tt.todo.TodoRV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tt.MainActivity;
import com.example.tt.R;
import com.example.tt.todo.RoomDb.Todo;
import com.example.tt.todo.TaskAddingActivity;
import com.example.tt.todo.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {
    FloatingActionButton fbtnTodoTaskAdd;
    TodoViewModel viewModel;
    ArrayList<Todo> todoList =new ArrayList<>();
    TodoAdapter todoAdapter =new TodoAdapter(this);
    RecyclerView rvTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        setTitle("My TodoList");
        rvTodo = (RecyclerView)findViewById(R.id.rvTodo);

        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(getApplication()
                        )).get(TodoViewModel.class);

        rvTodo.setLayoutManager(new LinearLayoutManager(TodoListActivity.this));
        rvTodo.setAdapter(TodoListActivity.this.todoAdapter);

        viewModel.getAllTask().observe(TodoListActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                if (!todos.isEmpty()) {
                    todoList.clear();
                    todoList.addAll(todos);
                    todoAdapter.updateList(todoList);
                }else{
                    todoList.clear();
                    todoAdapter.notifyDataSetChanged();
                }
            }
        });
        //Opening TaskAddingActivity
         fbtnTodoTaskAdd=(FloatingActionButton)findViewById(R.id.fbtnTodoTaskAdd);
         fbtnTodoTaskAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent= new Intent(getApplicationContext(), TaskAddingActivity.class);
                 startActivity(intent);
             }
         });
    }
}