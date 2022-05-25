package com.innovationadda.hireit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    AlertDialog alertDialog;
    private Button submit;

    private EditText email;
    SharedPreferences sp;
    DatabaseHandler myDb;
    String pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initComponents();
        clickListenHandler();


    }

    //This will initialize all the clickable components in Login page
    private void initComponents(){
        myDb = new DatabaseHandler(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        submit = findViewById(R.id.submit);
        email = findViewById(R.id.email);
    }

    //This will handle all the click events on the login page
    private void clickListenHandler(){

        //Login Listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(email.getText().toString().isEmpty()){
                    toast("Email field is required");
                }

               else{
                    Cursor res = myDb.getAllData();
                    if (res.getCount() == 0) {
                        Toast.makeText(ForgetPassword.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String stremail="",strpass = "";
                        while (res.moveToNext()) {
                            stremail = res.getString(5);
                            strpass = res.getString(13);
                        }

                        if (email.getText().toString().equals(stremail)){

                            final AlertDialog.Builder alert = new AlertDialog.Builder(ForgetPassword.this);
                            View mView = getLayoutInflater().inflate(R.layout.coustom_dialog,null);
                            TextView textView=mView.findViewById(R.id.tvDialogTitle);
                            TextView cancel = mView.findViewById(R.id.cancel);
                            Button btNext = (Button)mView.findViewById(R.id.logOut);
                            cancel.setVisibility(View.INVISIBLE);
                            alert.setView(mView);
                            alertDialog = alert.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            textView.setText("Your Password is = "+strpass);
                            btNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.cancel();
                                    finish();
                                }
                            });
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            alertDialog.show();
                        }else{

                            Toast.makeText(ForgetPassword.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });

    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }

}