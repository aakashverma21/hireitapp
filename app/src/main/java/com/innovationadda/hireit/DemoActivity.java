package com.innovationadda.hireit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DemoActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        sp = getSharedPreferences("login", MODE_PRIVATE);
    }


    public void logout(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(DemoActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.coustom_dialog,null);
        TextView cancel = mView.findViewById(R.id.cancel);
        Button btNext = (Button)mView.findViewById(R.id.logOut);
        alert.setView(mView);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();
                Toast.makeText(getApplicationContext(), "You have been successfully sign out", Toast.LENGTH_LONG).show();
                alertDialog.cancel();
                Intent intent;
                intent=new Intent(DemoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();

    }
}