package com.example.bullrunmarketapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Hashtable;

public class Email extends AppCompatActivity {
    EditText fromMail, toMail;
    Button sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        fromMail= (EditText)findViewById(R.id.fromMail);
        toMail= (EditText)findViewById(R.id.toMail);
        sendMail = (Button)findViewById(R.id.sendMail);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnClick event
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendMailUsingSendGrid(fromMail.getText().toString(), toMail.getText().toString(),
                                "This is the test subject",
                                "This is mail body send using sendgrid :)");

                        Toast.makeText(getApplicationContext(),"Sending mail...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    private void sendMailUsingSendGrid(String from, String to, String subject, String mailBody){
        Hashtable<String, String> params = new Hashtable<>();
        params.put("to", to);
        params.put("from", from);
        params.put("subject", subject);
        params.put("text", mailBody);

        SendGridAsync email = new SendGridAsync();
        try{
            email.execute(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
