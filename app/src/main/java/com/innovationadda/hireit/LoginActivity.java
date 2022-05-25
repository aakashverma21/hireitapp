package com.innovationadda.hireit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private TextView forgotPass;
    private Button login;

    private EditText email;
    private EditText password;
    SharedPreferences sp;

//    private Project_Database db;

    private Button customer;
    private Button vehicleCategory;
    private Button vehicle;

    private Button populate;
    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        clickListenHandler();

    }

    public void login(View view) {

        Intent intent=new Intent(this,UserViewActivity.class);
        startActivity(intent);

    }

    public void forgetpass(View view) {

        Intent intent=new Intent(this,ForgetPassword.class);
        startActivity(intent);

    }

    public void registration(View view) {

        Intent intent=new Intent(this,RegistrationActivity.class);
        startActivity(intent);

    }

    //This will initialize all the clickable components in Login page
    private void initComponents(){
        myDb = new DatabaseHandler(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        //Register Button
        register = findViewById(R.id.register);

        //Login Button
        login = findViewById(R.id.login);

        //Forgot Password Button
        forgotPass = findViewById(R.id.forgot_password);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    //This will handle all the click events on the login page
    private void clickListenHandler(){

        //Register Listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerPage);
            }
        });

        //Login Listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty() &&  password.getText().toString().isEmpty()){
                    toast("Please fill email and password field first.");
                }else if(email.getText().toString().isEmpty()){
                    toast("Email field is required");
                }else if(password.getText().toString().isEmpty()){
                    toast("Password field is required");
                }else{

                    Cursor res = myDb.getAllData();

                    if (res.getCount() == 0) {
                        Toast.makeText(LoginActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String stremail="",strpass = "";
                        while (res.moveToNext()) {
                            stremail = res.getString(5);
                            strpass = res.getString(13);
                        }

                        if (email.getText().toString().equals(stremail) && password.getText().toString().equals(strpass)){
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("email", stremail);
                            e.commit();
                            Toast.makeText(LoginActivity.this, "Your are Successfully Login in Hire-It", Toast.LENGTH_LONG).show();
                            Intent homePage = new Intent(LoginActivity.this,UserViewActivity.class);
//                            homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homePage);
                            finish();
                        }else {

                            Toast.makeText(LoginActivity.this, "No data found", Toast.LENGTH_SHORT).show();

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