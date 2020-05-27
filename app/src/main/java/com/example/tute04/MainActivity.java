package com.example.tute04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab05_it18202632.Database.DBHandler;
import com.example.tute04.ListAllActivity;
import com.example.tute04.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText UserName,Password;
    Button addB,selectB,deleteB,updateB,signB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserName = findViewById(R.id.editText1);
        Password = findViewById(R.id.editText2);


        selectB = findViewById(R.id.button1);
        addB = findViewById(R.id.button2);
        signB = findViewById(R.id.button3);
        deleteB = findViewById(R.id.button4);
        updateB = findViewById(R.id.button5);



        //add in
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler  dbHandler = new DBHandler(getApplicationContext());
                long newID =  dbHandler.addInfo(UserName.getText().toString(),Password.getText().toString());
                Toast.makeText(MainActivity.this, "You Successfully Added..."+newID, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //update details
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHandler  dbHandler = new DBHandler(getApplicationContext());

                Boolean status = dbHandler.updateInfo(UserName.getText().toString(),Password.getText().toString());

                if (status){
                    Toast.makeText(MainActivity.this, "Successfully Updated...", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(MainActivity.this, "Update Unsuccessfully...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //delete details
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler  dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(UserName.getText().toString());
                Toast.makeText(MainActivity.this, "Successfully Deleted...", Toast.LENGTH_SHORT).show();
            }
        });

        //signIn
        signB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler  dbHandler = new DBHandler(getApplicationContext());
                if (dbHandler.loginUserInfo(UserName.getText().toString(),Password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Successfully Logged... ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid User Name or Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListAllActivity.class);
                startActivity(intent);
            }
        });



    }


}
