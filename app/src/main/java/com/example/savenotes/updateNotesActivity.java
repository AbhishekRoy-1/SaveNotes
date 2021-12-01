package com.example.savenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateNotesActivity extends AppCompatActivity {
 EditText title, description1;
 Button updateNotes;
 String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        title = findViewById(R.id.title);
        description1 = findViewById(R.id.description1);
        updateNotes = findViewById(R.id.updateNote);
        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        description1.setText(i.getStringExtra("description"));
        id = i.getStringExtra("id");
        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description1.getText().toString())){
                    Database db = new Database(updateNotesActivity.this);
                    db.updateNotes(title.getText().toString(), description1.getText().toString(), id);
                    Intent i = new Intent(updateNotesActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(updateNotesActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}