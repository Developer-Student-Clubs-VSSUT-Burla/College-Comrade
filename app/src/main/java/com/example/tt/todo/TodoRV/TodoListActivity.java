package com.example.tt.todo.TodoRV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.tt.MainActivity;
import com.example.tt.R;
import com.example.tt.todo.RoomDb.Todo;
import com.example.tt.todo.TaskAddingActivity;
import com.example.tt.todo.TodoViewModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

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

        //Swipe Functionality
        initSwipe();

        viewModel.getAllTask().observe(TodoListActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                if (!todos.isEmpty()) {
                    todoList.clear();
                    todoList.addAll(todos);
                    todoAdapter.updateList(todoList);
                }else{
                    todoList.clear();
                    todoAdapter.updateList(todoList);
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

    private void initSwipe() {
        SimpleCallback simpleItemTouchCallback=new SimpleCallback(
                0,
                LEFT | RIGHT
        ){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Deleting Task on swipe
                viewModel.deleteTask(todoAdapter.getItemId(viewHolder.getAdapterPosition()));
            }

            @Override
            public void onChildDraw(@NonNull Canvas canvas,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX,
                                    float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {
                if(actionState== ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    Paint paint =new Paint();
                    Bitmap icon;

                    if(dX>0){
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.trash_bin);

                        paint.setColor(Color.parseColor("#D32F2F"));

                        canvas.drawRect(
                                itemView.getLeft(), itemView.getTop(),
                                itemView.getLeft()+ dX, itemView.getBottom(), paint
                        );

                        canvas.drawBitmap(
                                icon,
                                itemView.getLeft()+10,
                                itemView.getTop() + (itemView.getBottom() - itemView.getTop() - icon.getHeight()) / 2,
                                paint
                        );
                    }else{
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.trash_bin);

                        paint.setColor(Color.parseColor("#D32F2F"));

                        canvas.drawRect(
                                itemView.getRight()+dX, itemView.getTop(),
                                itemView.getRight(), itemView.getBottom(), paint
                        );

                        canvas.drawBitmap(
                                icon,
                                itemView.getRight()-icon.getWidth()-10,
                                itemView.getTop() + (itemView.getBottom() - itemView.getTop() - icon.getHeight()) / 2,
                                paint
                        );
                    }
                    viewHolder.itemView.setTranslationX(dX);

                }else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };


        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTodo);
    }

}