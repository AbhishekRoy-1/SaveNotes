package com.example.savenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    Context context;
    private static final String DatabaseName = "MyNotes";
    private static final int DatabaseVersion = 1;
    private static final String TableName= "mynotes";
    private static final String ColoumnId= "id";
    private static final String ColoumnTitle= "title";
    private static final String ColoumnDescription= "Description";




    public Database(@Nullable Context context) {
        super(context, DatabaseName,null,DatabaseVersion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TableName  +
                " (" + ColoumnId +" INTEGER PRIMARY KEY AUTOINCREMENT , " +
                ColoumnTitle +" TEXT, " +
                ColoumnDescription + " TEXT);" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);

    }
    void addNotes (String title , String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColoumnTitle,title);
        cv.put(ColoumnDescription,description);

        long resultValue = db.insert(TableName,null, cv);
        if (resultValue == -1){
            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Data added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData()
    {
        String query = "SELECT  * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        if (database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllNotes(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TableName;
        database.execSQL(query);
    }

    void updateNotes(String title, String description, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColoumnTitle,title);
        cv.put(ColoumnDescription,description);
        long result = database.update(TableName,cv,"id=?",new String[]{id});
        if (result ==-1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Update Successful ", Toast.LENGTH_SHORT).show();
        }
    }


}
