package com.innovationadda.hireit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    String Session="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        Session = sp.getString("email", null);

//        myRef.child("user").setValue("tokenfcm");
        new CountDownTimer(2000, 100) {
            public void onTick(long millisUntilFinished) {
//                tvCountDownTimer.setText("" + millisUntilFinished / 100);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Intent intent;
                if (Session!=null){
                    intent=new Intent(MainActivity.this, UserViewActivity.class);
                }else{
                    intent=new Intent(MainActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();

            }
        }.start();

    }
}