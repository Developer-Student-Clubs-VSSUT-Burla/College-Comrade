package com.example.tt.todo.TodoRV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.CompoundButton;
import android.widget.RadioGroup;
>>>>>>> upstream/master
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tt.R;
import com.example.tt.todo.RoomDb.Todo;
<<<<<<< HEAD
=======
import com.example.tt.todo.RoomDb.TodoDatabase;
import com.google.android.material.checkbox.MaterialCheckBox;
>>>>>>> upstream/master

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    ArrayList<Todo> allNotes = new ArrayList<>();
    Context context;

<<<<<<< HEAD
=======

>>>>>>> upstream/master
    public TodoAdapter(Context context) {
        this.context = context;
    }

<<<<<<< HEAD
=======
    @Override
    public long getItemId(int position) {
        return allNotes.get(position).getId();
    }

>>>>>>> upstream/master
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoViewHolder viewHolder = new
                TodoViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo currentTodo = allNotes.get(position);
        holder.tvTitle.setText(currentTodo.getTitle());
        holder.tvCategory.setText(currentTodo.getCategory());
        holder.tvDescription.setText(currentTodo.getDescription());
        updateTime(holder, currentTodo);
        updateDate(holder, currentTodo);
<<<<<<< HEAD
       int[] colors= holder.itemView.getResources().getIntArray(R.array.random_color);
       int randomColor = colors[new Random().nextInt(colors.length)];
       holder.viewColorTag.setBackgroundColor(randomColor);
=======
        int[] colors= holder.itemView.getResources().getIntArray(R.array.random_color);
        int randomColor = colors[new Random().nextInt(colors.length)];
        holder.viewColorTag.setBackgroundColor(randomColor);

>>>>>>> upstream/master

    }

    private void updateTime(TodoViewHolder holder, Todo currentTodo) {
        //02:49 pm
        String myTimeFormat = "hh:mm a";
        String time = new SimpleDateFormat(myTimeFormat).format(new Date(currentTodo.getTime()));
        holder.tvDueTime.setText(time);
    }

    private void updateDate(TodoViewHolder holder, Todo currentTodo) {
        //Sun, 14-Mar,2021
        String format = "EEE, d-MMM,yyyy";
        String date = new SimpleDateFormat(format).format(new Date(currentTodo.getTime()));
        holder.tvDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    void updateList(List<Todo> newList) {
        allNotes.clear();
        allNotes.addAll(newList);
        notifyDataSetChanged();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCategory;
        TextView tvDescription;
        TextView tvDate;
        TextView tvDueTime;
        View viewColorTag;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDueDate);
            tvDueTime = itemView.findViewById(R.id.tvDueTime);
            viewColorTag=itemView.findViewById(R.id.viewColorTag);

        }
    }
}
<<<<<<< HEAD
=======

>>>>>>> upstream/master
