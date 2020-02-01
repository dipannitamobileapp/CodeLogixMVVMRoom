package com.dipannita.codelogixmvvmroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recview;
    NoteAdapter adap;


    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    FloatingActionButton floatingActionButton;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adap.setNotes(notes);
            }
        });
        adap.setOnItemClickListner(new NoteAdapter.ONitemClickListner() {
            @Override
            public void onItemClick(Note note) {
                Intent i = new Intent(MainActivity.this, AddingDataActivity.class);
                i.putExtra(AddingDataActivity.SPECIAL_TITLE, note.getTitle());
                i.putExtra(AddingDataActivity.SPECIAL_DESC, note.getDescription());
                i.putExtra(AddingDataActivity.SPECIAL_ID,note.getId());
                startActivityForResult(i, EDIT_NOTE_REQUEST);
            }
        });
    }

    private void initialise()
    {
        recview = findViewById(R.id.recycler_view);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recview.setLayoutManager(new LinearLayoutManager(this));
        adap = new NoteAdapter();

        recview.setAdapter(adap);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddingDataActivity.class);
                startActivityForResult(i, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(AddingDataActivity.SPECIAL_TITLE);
            String descriptio = data.getStringExtra(AddingDataActivity.SPECIAL_DESC);
            Note note = new Note(title, descriptio);
            noteViewModel.insert(note);
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK)
        {
         int id = data.getIntExtra(AddingDataActivity.SPECIAL_ID, -1);
         if (id == -1)
         {
             Toast.makeText(getApplicationContext(),"Cant update note", Toast.LENGTH_SHORT).show();

         }
         else
         {
             String title = data.getStringExtra(AddingDataActivity.SPECIAL_TITLE);
             String descriptio = data.getStringExtra(AddingDataActivity.SPECIAL_DESC);
             Note note = new Note(title, descriptio);
             note.setId(id);
             noteViewModel.update(note);
             Toast.makeText(getApplicationContext(),"Updated successfully", Toast.LENGTH_SHORT).show();

         }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"cant save note", Toast.LENGTH_SHORT).show();
        }
    }
}
