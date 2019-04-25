package com.example.bullrunmarketapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustLogin extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_cust_login);

        //Firebase
        //super.onCreate();
        FirebaseApp.initializeApp(this);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signIn(edtUsername.getText().toString(),
                        edtPassword.getText().toString());
            }
        });
    }

    private void signIn(final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()){ //user
                    if(!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        //asserting to avoid NullPointerException potential
                        assert login != null;
                        if(login.getPassword() !=  null && login.getPassword().equals(password)){
                            Toast.makeText(CustLogin.this, "Login success!", Toast.LENGTH_SHORT).show();

                            //saving the username on successful login to be used within the order
                            saveUsername(login.getUsername());

                            //navigating to TruckSelection
                            Intent intent = new Intent(getApplicationContext(), TruckSelection.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(CustLogin.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(CustLogin.this, "Username not registered!", Toast.LENGTH_SHORT).show();
                }
            }

            //function for database errors
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //functions to save the username on success and enter it as a Firebase key
    void saveUsername(String username){
        getPref(this).edit().putString("username",username).apply();
    }

    public static String getUsername(Context context){
        return  getPref(context).getString("username",null);
    }

    public static SharedPreferences getPref(Context context){
       return context.getSharedPreferences("bullrun",MODE_PRIVATE);
    }
}
