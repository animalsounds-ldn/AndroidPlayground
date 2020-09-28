package com.example.androidplayground;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewProduct extends AppCompatActivity {
    private EditText mEditTextname;
    private TextView mTextViewAmount;
    private int mAmount = 0;
    private SQLiteDatabase mDB;
    private GroceryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproduct);
    
        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        mDB = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEditTextname = findViewById(R.id.editTextName);
        mTextViewAmount = findViewById(R.id.textViewAmount);
        mAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        Button buttonIncrease = findViewById(R.id.plusButton);
        Button buttonDecrease = findViewById(R.id.minusButton);
        Button buttonAdd = findViewById(R.id.addButton);
    }
    
    public void increase(View view){
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }
    
    public void decrease(View view){
        
        if (mAmount > 0) {
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }
    
    public void addGroceryItem(View view){
        if(mEditTextname.getText().toString().trim().length() == 0 || mAmount == 0){
            return;
        }
        String name = mEditTextname.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME, name);
        cv.put(GroceryContract.GroceryEntry.COLUMN_AMOUNT, mAmount);
        mDB.insert(GroceryContract.GroceryEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        mEditTextname.getText().clear();
    }
    
    private Cursor getAllItems(){
        return mDB.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}
