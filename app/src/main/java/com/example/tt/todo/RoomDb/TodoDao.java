package com.example.tt.todo.RoomDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//Creating the Data Access object (Dao)
@Dao
public
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTask(Todo task);
    @Query("Delete from Todo_Table where id=:uid")
    void deleteTask(long uid);
    @Query("Select * from Todo_Table where isFinished=-1")
    LiveData<List<Todo>> getAllTask();
    @Query("update Todo_Table set isFinished=1 where id=:uid")
    void finishTask(long uid);
    @Query("update Todo_Table set isDone=:d where id=:uid")
    void taskDone(long uid,int d);
    @Query("Select * from Todo_Table where id=:uid")
    Todo getTask(long uid);
}
