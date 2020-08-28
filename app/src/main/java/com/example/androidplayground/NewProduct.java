package com.example.androidplayground;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class NewProduct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproduct);



    }

    public void saveProduct(View view) {
        SharedPreferences preferences = getSharedPreferences("testDB", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEdit = preferences.edit();
        EditText editTextProductName = findViewById(R.id.editTextProductName);
        preferenceEdit.putString("savedProductName", editTextProductName.getText().toString());
        preferenceEdit.apply();

        String savedProdName = preferences.getString("savedProductName", "Value does not exist");
        Log.d("nameLog","saved message is: "+ savedProdName);
    }




}
