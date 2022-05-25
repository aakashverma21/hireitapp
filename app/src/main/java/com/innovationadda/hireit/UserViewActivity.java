package com.innovationadda.hireit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.innovationadda.hireit.FragmentPages.AccountFragment;
import com.innovationadda.hireit.FragmentPages.BookingFragment;
import com.innovationadda.hireit.FragmentPages.VehicleCategoryFragment;


public class UserViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    AlertDialog alertDialog;
    private VehicleCategoryFragment vehicleCategoryFragment;
    private BookingFragment bookingFragment;
    private AccountFragment accountFragment;
    private String loggedInCustomerID;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        initComponents();
        setFragment(vehicleCategoryFragment);

        clickListener();
    }

    private void clickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_vehicle:
                        setFragment(vehicleCategoryFragment);
                        return true;

                    case R.id.nav_booking:
                        setFragment(bookingFragment, loggedInCustomerID);
                        return true;

                    case R.id.nav_account :
                        setFragment(accountFragment, loggedInCustomerID);
                        return true;
                }

                return false;
            }
        });
    }

    private void setFragment(Fragment fragment, String Data) {
        Bundle bundle = new Bundle();
        bundle.putString("CUSTOMERID",Data);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    private void initComponents(){
        sp = getSharedPreferences("login", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.framelayout);

        vehicleCategoryFragment = new VehicleCategoryFragment();
        bookingFragment= new BookingFragment();
        accountFragment = new AccountFragment();

        loggedInCustomerID = getIntent().getStringExtra("CUSTOMERID");

    }

    public void logout(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(UserViewActivity.this);
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
                intent=new Intent(UserViewActivity.this, LoginActivity.class);
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