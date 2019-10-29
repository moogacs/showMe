package com.example.showme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDialog();
    }

    public void openDialog() {
        MainDialog mainDialog = new MainDialog();
        mainDialog.show(getSupportFragmentManager(), "Main Dialog");
    }
}
