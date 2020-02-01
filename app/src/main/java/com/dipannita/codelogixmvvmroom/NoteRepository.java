package com.dipannita.codelogixmvvmroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application)
    {
        NoteDataBase data = NoteDataBase.getInstance(application);
        noteDao = data.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note)
    {

        new InserNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note)
    {

        new updateNoteAsyncTask(noteDao).execute(note);
    }


    public  LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }
    private static class InserNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;
        public InserNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class updateNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;


        public updateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
}
