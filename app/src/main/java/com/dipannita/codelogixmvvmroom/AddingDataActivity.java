package com.dipannita.codelogixmvvmroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddingDataActivity extends AppCompatActivity {

    public static final String SPECIAL_TITLE = "com.dipannita.codelogix.TITLE";
    public static final String SPECIAL_DESC = "com.dipannita.codelogix.DESC";
    public static final String SPECIAL_ID = "com.dipannita.codelogix.ID";
    TextView headingtv;
    ImageView backbtn;
    EditText titleet, descet;
    FloatingActionButton floatbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_data);
        initialisation();
    }

    private void initialisation()
    {
        titleet = findViewById(R.id.titlet);
        descet = findViewById(R.id.descet);
        backbtn = findViewById(R.id.backbtn);
        floatbtn = findViewById(R.id.floatingActionButton2);
        headingtv = findViewById(R.id.addnote);
        if (getIntent().hasExtra(SPECIAL_ID))
        {
            headingtv.setText("EDIT NOTE");
            titleet.setText(getIntent().getStringExtra(SPECIAL_TITLE));
            descet.setText(getIntent().getStringExtra(SPECIAL_DESC));
        }
        else
        {
            headingtv.setText("ADD NOTE");
            titleet.setHint("Note TItle here");
            descet.setHint("Start writing your note here");
        }
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnote();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//
        Log.i("tag","has special id");
        String title = titleet.getText().toString().trim();
        String description = descet.getText().toString().trim();

        String sptitle =  getIntent().getStringExtra(SPECIAL_TITLE);
        String spdesc = getIntent().getStringExtra(SPECIAL_DESC);

        if (getIntent().hasExtra(SPECIAL_ID))
        {
            Log.i("tag",""+title+" "+sptitle);
            if (title.contentEquals(sptitle))
            {
                super.onBackPressed();
            }

            else
            {
                alertshow();
            }

        }
        else {
            if (title == null || description == null || title.length() == 0 || description.length() == 0)
            {
                Log.i("tag","showning alert");
                super.onBackPressed();
            }
            else
            {
                alertshow();

            }
        }

    }

    private void alertshow()
    {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                .setTitle("Exit wihtout Saving")
                .setMessage("Changes you have made in this page will be unsaved")
                .setPositiveButton("  SAVE  ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addnote();
                    }
                })

                .setNegativeButton("  DISCARD  ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddingDataActivity.super.onBackPressed();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void addnote()
    {
        String title = titleet.getText().toString();
        String description = descet.getText().toString();
        if (title.trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"Please provide note title",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Intent data2 = new Intent();
            data2.putExtra(SPECIAL_TITLE,title);
            data2.putExtra(SPECIAL_DESC, description);

            int id = getIntent().getIntExtra(SPECIAL_ID, -1);
            if (id != -1)
            {
                data2.putExtra(SPECIAL_ID, id);
            }
            setResult(RESULT_OK, data2);
            finish();
        }


    }
}
