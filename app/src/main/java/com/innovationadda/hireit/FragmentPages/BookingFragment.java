package com.innovationadda.hireit.FragmentPages;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.innovationadda.hireit.Adapter.BookingAdapter;
import com.innovationadda.hireit.Adapter.VehicleAdapter;
import com.innovationadda.hireit.Adapter.summaryAdapter;
import com.innovationadda.hireit.BookingCarActivity;
import com.innovationadda.hireit.DatabaseHandler;
import com.innovationadda.hireit.Model.Vehicle;
import com.innovationadda.hireit.Model.summary;
import com.innovationadda.hireit.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

    public class BookingFragment extends Fragment implements summaryAdapter.onSummaryListener{

    private RecyclerView mRecyclerView;
    private summaryAdapter mExampleAdapter;
    private ArrayList<summary> mExampleList;
    DatabaseHandler myDb;
    Button btBookingStatus,btBookingview;
    private int customerID;
    SharedPreferences sp;
    String Session="";
    Button back;
    public BookingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        initComponents(view);
        listenHandler();
        return view;
    }

    private void initComponents(View view) {
        back = view.findViewById(R.id.back);
        myDb = new DatabaseHandler(getActivity());
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        Session = sp.getString("email", null);
        btBookingStatus = view.findViewById(R.id.btBookingStatus);
        btBookingview = view.findViewById(R.id.btBookingview);
        btBookingStatus.setVisibility(View.VISIBLE);
        btBookingview.setVisibility(View.VISIBLE);


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mExampleList = new ArrayList<>();

//        list = (ArrayList<VehicleCategory>) vehicleCategoryDao.getAllCategory();
        mExampleAdapter = new summaryAdapter(getContext(), mExampleList,this);


    }

    private void listenHandler() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                btBookingStatus.setVisibility(View.VISIBLE);
                btBookingview.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);

            }
        });

        btBookingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Cursor res = myDb.getAllDataBooking();

                if (res.getCount() == 0) {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    back.setVisibility(View.GONE);
                        String stremail="",strCheckPickup = "",strId="",car="",vec="",date="",time="",rate="";
                        while (res.moveToNext()) {
                            stremail = res.getString(11);
                            strCheckPickup = res.getString(4);
                            strId = res.getString(1);
                            date = res.getString(2);
                            time = res.getString(3);
                            car = res.getString(9);
                            vec = res.getString(10);
                            rate = res.getString(12);
                        }

                    if (Session.equals(stremail) && strCheckPickup.equals("true")){
                                Intent vehicleInfoPage = new Intent(getActivity(), BookingCarActivity.class);
                                vehicleInfoPage.putExtra("bookingid", strId);
                                vehicleInfoPage.putExtra("pickupBookDate", date);
                                vehicleInfoPage.putExtra("pickupBookTime", time);
                                vehicleInfoPage.putExtra("rateReturn", rate);
                                vehicleInfoPage.putExtra("car", car);
                                vehicleInfoPage.putExtra("vechicle", vec);
                                vehicleInfoPage.putExtra("return", "true");
                                startActivity(vehicleInfoPage);
                    }else {

                        Toast.makeText(getActivity(), "Please Book your ride first", Toast.LENGTH_SHORT).show();
                    }

                    }

//                btBookingStatus.setVisibility(View.GONE);
//                btBookingview.setVisibility(View.GONE);




            }
        });

        btBookingview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRecyclerView.setVisibility(View.VISIBLE);
                Cursor res = myDb.getAllDataSummary();

                if (res.getCount() == 0) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
//                    btBookingStatus.setVisibility(View.VISIBLE);
//                    btBookingview.setVisibility(View.VISIBLE);
                }
                else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    btBookingStatus.setVisibility(View.GONE);
                    btBookingview.setVisibility(View.GONE);
//                    mRecyclerView.setVisibility(View.VISIBLE);
                    String stremail="",pickupdate = "",returndate="",vec="",cost=""; int id;
                    while (res.moveToNext()) {
                        stremail = res.getString(6);
                        pickupdate = res.getString(2);
                        returndate = res.getString(3);
                        vec = res.getString(4);
                        cost = res.getString(5);
                        id = Integer.parseInt(res.getString(1));

                        if (stremail.equals(Session)){
                            mExampleList.add(new summary(id,vec,cost,pickupdate,returndate));
                        }
                    }
                    mRecyclerView.setAdapter(mExampleAdapter);
                }




            }
        });

    }

    @Override
    public void onClick(int position) {

        Toast.makeText(getActivity(), "onclick", Toast.LENGTH_SHORT).show();

    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt, Toast.LENGTH_SHORT);
        toast.show();
    }


}
