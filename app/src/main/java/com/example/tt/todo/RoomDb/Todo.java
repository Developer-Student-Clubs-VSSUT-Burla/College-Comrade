package com.example.tt.todo.RoomDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Todo_Table")
public class Todo implements Serializable {
    @ColumnInfo(name = "Title")
    String title;
    @ColumnInfo(name = "Description")
    String description;
    @ColumnInfo(name = "Category")
    String category;
    @ColumnInfo(name = "Date")
    long date;
    @ColumnInfo(name = "Time")
    long time;
    @ColumnInfo(name = "isFinished")
    int isFinished = -1;
    @ColumnInfo(name = "isDone")
    int isDone=0;
    @PrimaryKey(autoGenerate = true)
    long id =0;

    // Setters and Getters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

