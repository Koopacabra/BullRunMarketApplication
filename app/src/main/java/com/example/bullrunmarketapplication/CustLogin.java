package com.example.bullrunmarketapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class CustLogin extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_cust_login);

        //Firebase
        //super.onCreate();
        FirebaseApp.initializeApp(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                edtUsername.setError(null);
                edtPassword.setError(null);

                String username  =  edtUsername.getText().toString();
                String password  = edtPassword.getText().toString();

                if(TextUtils.isEmpty(username)){
                    edtUsername.setError("Cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Cannot be empty");
                    return;
                }
                signIn(username,password);
            }
        });
    }

    private void signIn(final String username, final String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("User").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //username allows for null entries but toasts if it is null
                if(user == null){
                    Toast.makeText(CustLogin.this, "Please check the username.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //checks to see if the user logging in is a truck -- toasts accordingly
                if(user.isTruck()){
                    Toast.makeText(CustLogin.this, "Looks like you are a Food Truck!\nUse the Truck Login instead.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(user.getPassword().equals(password)){
                    Toast.makeText(CustLogin.this, "Login success!", Toast.LENGTH_SHORT).show();

                    //saving the username on successful login to be used within the order
                    saveUsername(user);

                    //navigating to TruckSelection
                    Intent intent = new Intent(getApplicationContext(), TruckSelection.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CustLogin.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustLogin.this, "No user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //functions to save the username on success and enter it as a Firebase key
    void saveUsername(User user){
        getPref(this).edit().putString("username",new Gson().toJson(user)).apply(); }

    public static String getUsername(Context context){
        return  getUser(context).getUsername();
    }

    //also saving the username's email as an object for sending the email notification
    public static String getUserEmail(Context context){
        return  getUser(context).getEmail();
    }

    //Gson allows java objects to be translated to Json and vice versa; needed when pulling usernames & email from Firebase
    public static User getUser(Context context){
        Gson gson = new Gson();
        return  gson.fromJson(getPref(context).getString("username",new Gson().toJson(new User())),User.class);
    }

    public static SharedPreferences getPref(Context context){
       return context.getSharedPreferences("bullrun",MODE_PRIVATE);
    }
}
