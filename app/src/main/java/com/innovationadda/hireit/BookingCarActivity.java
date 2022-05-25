package com.innovationadda.hireit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.time.temporal.ChronoUnit.DAYS;

public class BookingCarActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    DatabaseHandler myDb;
    SharedPreferences sp;
    String Session="";
    //PICKUP AND RETURN DATE
    private TextView pickupDate, returnDate;

    //PICKUP AND RETURN TIME
    private TextView pickupTime, returnTime;

    //PICKUP DATE/TIME
    private Calendar _pickup;

    //RETURN DATE/TIME
    private Calendar _return;

    //DRIVER DETAILS
    private EditText firstName, lastName, email, phoneNumber;
    private RadioGroup customerTitle;


    //DATE FORMAT -> FOR DISPLAY PURPOSE
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.CANADA);

    //DATE/TIME STORING
    //GOING BACK BUTTON and CONTINUE BOOKING BUTTON
    private Button back, continueBooking;
    private static final String TAG = "Debug";
    private Boolean flag = false;
    String strLongitude="",strLatitude="",strAccuracy="";
    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;
    String address="";
    String pincode="";
    Boolean checkDate=false,checkTime=false;
    String car="",vechicle="",rate="",bookingId="",carReturn="",vechicleReturn="";
    private ProgressBar pb = null;


    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    Boolean checkLocation=false,internetCheck=false;
    CardView cvPickup,cvReturn;
    String Date="",Time="";
    String rateReturn="",pickupBookTime="",pickupBookDate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);
        ButterKnife.bind(this);
        initComponents();
        init();
        listenHandler();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
//        pb.setVisibility(View.INVISIBLE);
        continueBooking.setEnabled(true);

        if (mCurrentLocation != null) {
            checkLocation=true;
            Geocoder gcd = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                if (addresses.size() > 0)
                    address=addresses.get(0).getAddressLine(0);
                pincode=addresses.get(0).getPostalCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void initComponents() {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        Session = sp.getString("email", null);
        myDb = new DatabaseHandler(this);



        //BACK BUTTON
        back = findViewById(R.id.back);
        cvPickup = findViewById(R.id.rental_time);
        cvReturn = findViewById(R.id.rental_return);
        //CONTINUE BOOKING
        continueBooking = findViewById(R.id.continueBooking);

        //CAR RENTAL DATE AND TIME
        pickupDate = findViewById(R.id.pickupDate);
        pickupTime = findViewById(R.id.pickupTime);

        returnDate = findViewById(R.id.returnDate);
        returnTime = findViewById(R.id.returnTime);


        //PICKUP AND RETURN DATE OBJECT
        _pickup = Calendar.getInstance();
        _return = Calendar.getInstance();


        //SET THE DATE AND TIME TO CURRENT
        pickupDate.setText(dateFormat.format(_pickup.getTime()));
        pickupTime.setText(timeFormat.format(_pickup.getTime()));

        returnDate.setText(dateFormat.format(_return.getTime()));
        returnTime.setText(timeFormat.format(_return.getTime()));

        pb = (ProgressBar)findViewById(R.id.progress1);
//        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

        if (getIntent().hasExtra("pickup")){
            car=getIntent().getStringExtra("car");
            vechicle=getIntent().getStringExtra("vechicle");
            rate=getIntent().getStringExtra("rate");
            cvPickup.setVisibility(View.VISIBLE);
            cvReturn.setVisibility(View.GONE);
            continueBooking.setText("Continue Booking");
        }
        if (getIntent().hasExtra("return")){

            bookingId=getIntent().getStringExtra("bookingid");
            carReturn=getIntent().getStringExtra("car");
            rateReturn=getIntent().getStringExtra("rateReturn");
            vechicleReturn=getIntent().getStringExtra("vechicle");
            pickupBookDate=getIntent().getStringExtra("pickupBookDate");
            pickupBookTime=getIntent().getStringExtra("pickupBookTime");
//            Toast.makeText(this, "bookingId"+bookingId+"\npickupBookDate="+pickupBookDate, Toast.LENGTH_SHORT).show();


//            cvPickup.setVisibility(View.GONE);
//            cvReturn.setVisibility(View.VISIBLE);
            continueBooking.setText("Return Booking");
        }

    }

    //LISTEN HANDLER
    private void listenHandler() {

        //GOING BACK BUTTON
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //PICKUP DATE AND TIME LISTENER
        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_pickup,pickupDate);
//                checkDate=true;
            }
        });

        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(_pickup, pickupTime);
//                checkTime=true;
            }
        });

        //RETURN DATE AND TIME LISTENER
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_return,returnDate);
            }
        });
        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTimePicker(_return, returnTime);
            }
        });

        continueBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstchecknetwork();
                if(internetCheck){
                    pb.setVisibility(View.VISIBLE);
                    continueBooking.setEnabled(false);
                    startLocationButtonClick();
                    new CountDownTimer(3000, 100) {
                        public void onTick(long millisUntilFinished) {
//                tvCountDownTimer.setText("" + millisUntilFinished / 100);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            pb.setVisibility(View.INVISIBLE);
                            continueBooking.setEnabled(true);
                            if (mCurrentLocation!=null){

                                if(getIntent().hasExtra("return")){
                                    if (returnDate.getText().toString().isEmpty()){
                                        Toast.makeText(BookingCarActivity.this, "Please select date", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(returnTime.getText().toString().isEmpty()){
                                        Toast.makeText(BookingCarActivity.this, "Please select time", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(strLatitude.equals("") && strLongitude.equals("") && pincode.equals("")  && address.equals("")){
                                        Toast.makeText(BookingCarActivity.this, "Location is not updated", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        if (myDb.insertDataReturn(bookingId,
                                                returnDate.getText().toString(),
                                                returnTime.getText().toString(),"true",address,pincode,strLongitude,strLatitude,carReturn,vechicleReturn,Session,rateReturn)){
                                            long diff = 0;

                                            SimpleDateFormat obj = new SimpleDateFormat("dd/MM/yyyy");
                                            String dateStart = pickupBookDate;
                                            String dateEnd = returnDate.getText().toString();
                                            Date date1 = null;
                                            try {
                                                date1 = obj.parse(dateStart);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Date date2 = null;
                                            try {
                                                date2 = obj.parse(dateEnd);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            // Calucalte time difference in milliseconds
                                            long time_difference = date2.getTime() - date1.getTime();
                                            // Calucalte time difference in days
                                            long days_difference = (time_difference / (1000*60*60*24)) % 365;

                                            final int totalcosting= (int) (days_difference* Integer.parseInt(rateReturn));


                                            final AlertDialog.Builder alert = new AlertDialog.Builder(BookingCarActivity.this);

                                            View mView = getLayoutInflater().inflate(R.layout.booking_cmplt,null);
                                            TextView vehicleName = mView.findViewById(R.id.vehicleName);
                                            vehicleName.setText(vechicleReturn);
                                            LinearLayout lltotal = mView.findViewById(R.id.lltotal);
                                            lltotal.setVisibility(View.VISIBLE);
                                            TextView bookingCompleteText = mView.findViewById(R.id.bookingCompleteText);
                                            bookingCompleteText.setText("Return Complete");
                                            TextView bookingIsComplete = mView.findViewById(R.id.bookingIsComplete);
                                            bookingIsComplete.setText("Your Return Is Done");
                                            TextView tvpickdate = mView.findViewById(R.id.tvpickdate);
                                            tvpickdate.setText("Return Date:");
                                            TextView pickupText = mView.findViewById(R.id.pickupText);
                                            pickupText.setText("Return Time:");
                                            TextView total = mView.findViewById(R.id.total);
                                            total.setText(""+totalcosting);
                                            TextView rate1 = mView.findViewById(R.id.rate);
                                            rate1.setText(rateReturn+" / day");
                                            TextView pickdate = mView.findViewById(R.id.pickdate);
                                            pickdate.setText(returnDate.getText().toString());
                                            TextView _pickup = mView.findViewById(R.id.pickup);
                                            _pickup.setText(returnTime.getText().toString());
                                            TextView bookingID = mView.findViewById(R.id.bookingID);
                                            bookingID.setText(bookingId);
                                            Button btNext = (Button)mView.findViewById(R.id.logOut);
                                            alert.setView(mView);
                                            alertDialog = alert.create();
                                            alertDialog.setCanceledOnTouchOutside(false);
                                            btNext.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (myDb.updateData(bookingId,"false")) {
                                                        myDb.insertDataSummary(bookingId,
                                                                pickupBookDate,
                                                                returnDate.getText().toString(), vechicleReturn, String.valueOf(totalcosting), Session);//                                                            Toast.makeText(BookingCarActivity.this, "", Toast.LENGTH_SHORT).show();
                                                        alertDialog.cancel();
                                                        finish();
                                                    }
                                                }
                                            });
                                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            alertDialog.show();
                                        }
                                        stopLocationUpdates();


                                    }



                                }
                                else if(getIntent().hasExtra("pickup")){
                                    if (pickupDate.getText().toString().isEmpty()){
                                        Toast.makeText(BookingCarActivity.this, "Please select date", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(pickupTime.getText().toString().isEmpty()){
                                        Toast.makeText(BookingCarActivity.this, "Please select time", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(strLatitude.equals("") && strLongitude.equals("") && pincode.equals("")  && address.equals("")){
                                        Toast.makeText(BookingCarActivity.this, "Location is not update", Toast.LENGTH_SHORT).show();
                                    }else
                                    {


                                        final int id=generateID();

                                        if (myDb.insertDataBooking(String.valueOf(id),
                                            pickupDate.getText().toString(),
                                            pickupTime.getText().toString(),"true",address,pincode,strLongitude,strLatitude,car,vechicle,Session,rate)){


                                            final AlertDialog.Builder alert = new AlertDialog.Builder(BookingCarActivity.this);
                                            View mView = getLayoutInflater().inflate(R.layout.booking_cmplt,null);
                                            TextView vehicleName = mView.findViewById(R.id.vehicleName);
                                            vehicleName.setText(vechicle);
                                            LinearLayout lltotal = mView.findViewById(R.id.lltotal);
                                            lltotal.setVisibility(View.GONE);
                                            TextView bookingCompleteText = mView.findViewById(R.id.bookingCompleteText);
                                            bookingCompleteText.setText("Booking Complete");
                                            TextView bookingIsComplete = mView.findViewById(R.id.bookingIsComplete);
                                            bookingIsComplete.setText("Your Booking Is Done");
                                            TextView tvpickdate = mView.findViewById(R.id.tvpickdate);
                                            tvpickdate.setText("Pickup Date:");
                                            TextView pickupText = mView.findViewById(R.id.pickupText);
                                            pickupText.setText("Pickup Time:");

                                            TextView rate1 = mView.findViewById(R.id.rate);
                                            rate1.setText(rate+" / day");
                                            TextView pickdate = mView.findViewById(R.id.pickdate);
                                            pickdate.setText(pickupDate.getText().toString());
                                            TextView _pickup = mView.findViewById(R.id.pickup);
                                            _pickup.setText(pickupTime.getText().toString());
                                            TextView bookingID = mView.findViewById(R.id.bookingID);
                                            bookingID.setText(String.valueOf(id));
                                            Button btNext = (Button)mView.findViewById(R.id.logOut);
                                            alert.setView(mView);
                                            alertDialog = alert.create();
                                            alertDialog.setCanceledOnTouchOutside(false);
                                            btNext.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    alertDialog.cancel();
                                                    finish();
                                                }
                                            });
                                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            alertDialog.show();

                                        }


                                        stopLocationUpdates();
                                    }
                                }







                            }
                            else{
                                Toast.makeText(BookingCarActivity.this, "Location is not updated", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }.start();
                }
            }
        });

    }

    @OnClick(R.id.continueBooking)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

//                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(BookingCarActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(BookingCarActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    //OPEN CALENDAR DIALOG
    private void openCalendar(final Calendar rentalDate, final TextView rentalDateText) {
        DatePickerDialog datePickerDialog = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    rentalDate.set(year,month,dayOfMonth);
                    rentalDateText.setText(dateFormat.format(rentalDate.getTime()));
                }
            });
        }

        datePickerDialog.show();
    }

    //OPEN TIMEPICKER DIALOG
    private Date openTimePicker(final Calendar rentalTime, final TextView rentalTimeText){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                rentalTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                rentalTime.set(Calendar.MINUTE,minute);

                rentalTimeText.setText(timeFormat.format(rentalTime.getTime()));
            }
        },hour,min,false);

        timePickerDialog.show();

        return calendar.getTime();
    }

    private int generateID(){
        Random rnd = new Random();
        int id = 202000 + rnd.nextInt(65)+10;
        return id;
    }

    public void firstchecknetwork(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                        Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
                    internetCheck=true;
                }
                else {
                    Toast.makeText(this, "Please check your network connection and try again.", Toast.LENGTH_SHORT).show();
                    internetCheck=false;
                }
            }
        }

    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().hasExtra("return")){


        }

    }
}