package com.example.savenotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
   Adapter adapter;
    List<Model> notesList;
    Database database;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.layout_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,AddNotesActivity.class);
            startActivity(intent);
            }
        });



        notesList = new ArrayList<>();
        database = new Database(this);
        fetchAllNotesFromDatabase();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter ad = new adapter(this,MainActivity.this,notesList);
        recyclerView.setAdapter(ad);
    }

    void fetchAllNotesFromDatabase(){
      Cursor cursor=  database.readAllData();
      if (cursor.getCount()==0){
          Toast.makeText(this, "No Data To Show", Toast.LENGTH_SHORT).show();
      }
      else{
          while(cursor.moveToNext()){
              notesList.add(new Model(cursor.getString(0), cursor.getString(1),cursor.getString(2)));
          }
      }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Notes here");
        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(listener);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all_notes){
            deleteAllNotes();
        }


        return super.onOptionsItemSelected(item);
    }



    private void deleteAllNotes(){
        Database db = new Database(MainActivity.this);
        db.deleteAllNotes();
        recreate();
    }


}