package com.example.bullrunmarketapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class About_Page extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_about);
    }
    public void about (View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://bstlouis95.wixsite.com/bullrunmarket"));
        startActivity(browserIntent);
    }
}


