package com.example.showme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SideViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_view);
    }


    @Override
    public void onBackPressed() {
        if(getIntent().getExtras().get("activity").equals("left"))
        {
            MainActivity.leftView--;
        }
        else
        {
            MainActivity.rightView--;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.finish();
    }
}