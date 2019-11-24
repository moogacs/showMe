package com.example.showme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SideViewActivity extends AppCompatActivity {

    private String alternateText;
    private static TextView alternatingText;
    private static boolean state = false;

    private static void setAlternatingText(String text) {
        alternatingText.setText(text);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_view);
        alternatingText = findViewById(R.id.alternateText);
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

    public static void alterTheText() {
        if (!state){
            setAlternatingText("This will vibrate when input is received");
            state = true;
        } else {
            setAlternatingText("A message was recived");
            state = false;
        }
    }

    public static void vibrate(){
        MainActivity.vibrate();
    }
}