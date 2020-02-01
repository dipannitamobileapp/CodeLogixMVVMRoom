package com.dipannita.codelogixmvvmroom;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();

}
