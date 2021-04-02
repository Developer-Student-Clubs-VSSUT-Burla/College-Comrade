package com.example.tt.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tt.R;
import com.example.tt.todo.RoomDb.Todo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class TaskAddingActivity extends AppCompatActivity {

    TodoViewModel viewModel;
    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    String[] labelsArray = {"Default", "Personal", "Professional", "Shopping", "Wishlist", "Work"};
    ArrayList<String> labels = new ArrayList<>();

    long finalDate = 0;
    long finalTime = 0;
    String title;
    String description;
    String category;
    TextInputEditText etDate;
    TextInputEditText etTime;
    TextInputLayout tilTime;
    Spinner spinnerCategory;
    ImageView imgAddCategory;
    MaterialButton btnSaveTask;
    TextInputLayout titleInpLay;
    TextInputLayout taskInpLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_adding);
        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider
                        .AndroidViewModelFactory
                        .getInstance(getApplication()
                        )).get(TodoViewModel.class);

        setTitle("Add Task");
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        labels.addAll(Arrays.asList(labelsArray));
        etDate = (TextInputEditText) findViewById(R.id.etDate);
        tilTime = (TextInputLayout) findViewById(R.id.tilTime);
        titleInpLay = (TextInputLayout) findViewById(R.id.titleInpLay);
        taskInpLay = (TextInputLayout) findViewById(R.id.taskInpLay);
        //Set up the datePickerDialog
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateListener();
            }
        });

        etTime = (TextInputEditText) findViewById(R.id.etTime);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeListener();
            }
        });

        //Setting Spinner 
        setUpSpinner();

        //adding new item to the spinner
        imgAddCategory = (ImageView) findViewById(R.id.imgAddCategory);
        imgAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a dialogbox to add Item
                addNewLabel();
            }
        });

        //deleting item in spinner
        spinnerItemDeleteDialog();

        btnSaveTask = (MaterialButton) findViewById(R.id.btnSaveTask);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });

    }

    private void saveTodo() {
        category = spinnerCategory.getSelectedItem().toString();
        title = titleInpLay.getEditText().getText().toString();
        description = taskInpLay.getEditText().getText().toString();
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCategory(category);
        todo.setDate(finalDate);
        todo.setTime(finalTime);
        viewModel.insertTask(todo);
        finish();
    }


    private void spinnerItemDeleteDialog() {
        spinnerCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(TaskAddingActivity.this);
                deleteDialog.setTitle(labels.get(position) + " Category");
                deleteDialog.setMessage("Do you want to remove this Category?");
                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        labels.remove(position);
                        Collections.sort(labels);
                        spinnerCategory.setVisibility(View.VISIBLE);
                    }
                });
                deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        spinnerCategory.setVisibility(View.VISIBLE);
                    }
                });
                if (!labels.get(position).equals("Default")) {
                    spinnerCategory.setVisibility(View.INVISIBLE);
                    deleteDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(TaskAddingActivity.this, "Select the item to be removed", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void addNewLabel() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("LABEL");
        alertDialog.setMessage("Enter New Label");
        EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_playlist_add_black_24dp);
        alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newLabel = input.getText().toString();
                labels.add(newLabel);
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.setCancelable(true);
            }
        });
        alertDialog.show();
    }

    //Setting spinner items
    private void setUpSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(labels);
        }
        spinnerCategory.setAdapter(adapter);
    }

    //Time picker dialog box
    private void setTimeListener() {
        myCalendar = Calendar.getInstance();
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), false
        );
        timePickerDialog.show();
    }

    private void updateTime() {
        //02:49 pm
        String myTimeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myTimeFormat);
        finalTime = myCalendar.getTime().getTime();
        etTime.setText(sdf.format(myCalendar.getTime().getTime()));
    }

    private void setDateListener() {
        myCalendar = Calendar.getInstance();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, dateSetListener, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void updateDate() {
        //Mon, 29-Mar,2021
        String format = "EEE, d-MMM,yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        finalDate = myCalendar.getTime().getTime();
        etDate.setText(sdf.format(myCalendar.getTime()));
        tilTime.setVisibility(View.VISIBLE);
    }
}