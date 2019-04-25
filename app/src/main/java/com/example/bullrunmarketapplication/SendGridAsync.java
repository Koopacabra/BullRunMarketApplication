package com.example.bullrunmarketapplication;

import android.os.AsyncTask;

import com.github.sendgrid.SendGrid;

import java.util.Hashtable;

public class SendGridAsync extends AsyncTask <Hashtable<String, String>, Void, String> {


    @Override
    protected String doInBackground(Hashtable<String, String>... hashtables) {
        Hashtable<String, String> h = hashtables[0];
        SendGridCredentials sendGridCredentials = new SendGridCredentials();
        SendGrid sendGrid = new SendGrid(sendGridCredentials.getUsername(), sendGridCredentials.getPassword());
        sendGrid.addTo(h.get("to"));
        sendGrid.setFrom(h.get("from"));
        sendGrid.setSubject(h.get("subject"));
        sendGrid.setText(h.get("text"));
        String response = sendGrid.send();

        return response;
    }
}
