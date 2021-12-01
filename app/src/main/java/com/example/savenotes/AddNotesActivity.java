package com.example.savenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity {
 EditText title, description1;
 Button addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        title = findViewById(R.id.title);
        description1 = findViewById(R.id.description1);
        addNote =  findViewById(R.id.addNotes);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString()) || !TextUtils.isEmpty(description1.getText().toString()))
                {
                    Database db = new Database(AddNotesActivity.this);
                    db.addNotes(title.getText().toString(),description1.getText().toString());
                    Intent intent = new Intent(AddNotesActivity.this,MainActivity.class);
                    getIntent().setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();



                }
                else{
                    Toast.makeText(AddNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}