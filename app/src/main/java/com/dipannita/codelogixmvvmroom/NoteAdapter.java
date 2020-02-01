package com.dipannita.codelogixmvvmroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private ONitemClickListner listner;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recviewitem, parent, false);
        return new NoteHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note notenow = notes.get(position);
        holder.titletv.setText(notenow.getTitle());
        holder.desctv.setText(notenow.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder
    {
        private TextView titletv;
        private TextView desctv;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            titletv = itemView.findViewById(R.id.titletv);
            desctv = itemView.findViewById(R.id.desctv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view ) {
                    if (listner != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listner.onItemClick(notes.get(getAdapterPosition()));
                    }
                }
            });
        }
    }


    public interface ONitemClickListner
    {
        void onItemClick(Note note);
    }

    public void setOnItemClickListner(ONitemClickListner listner)
    {
        this.listner = listner;

    }

}
