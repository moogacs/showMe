package com.example.showme;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;


public class MainActivity extends AppCompatActivity {
    public static int leftView = 0, rightView = 0, mainView = 0;

    private static Vibrator vibrator;
    private static MainActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);


        //setAlternatingText("This will vibrate when input is received");
        openDialog();
        vibrator = (Vibrator) getSystemService(MainActivity.VIBRATOR_SERVICE);
    }

    public void openDialog() {
        MainDialog mainDialog = new MainDialog();
        mainDialog.show(getSupportFragmentManager(), "Main Dialog");
        mainDialog.setCancelable(false);
    }

    public static MainActivity getContext() {
        return mContext;
    }

    public static void vibrate(){
        vibrator.vibrate(1000);
    }

}
