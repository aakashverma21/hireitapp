package com.innovationadda.hireit.FragmentPages;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovationadda.hireit.Adapter.VehicleAdapter;
import com.innovationadda.hireit.Adapter.VehicleCategoryAdapter;
import com.innovationadda.hireit.BookingCarActivity;
import com.innovationadda.hireit.Model.Vehicle;
import com.innovationadda.hireit.Model.VehicleCategory;
import com.innovationadda.hireit.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment implements VehicleAdapter.onVehicleListener{
    private String selectVehicleCategory;
    private RecyclerView mRecyclerView;
    private VehicleAdapter mExampleAdapter;
    private ArrayList<Vehicle> mExampleList;

    public VehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        initComponents(view);
        
        return view;
    }

    private void initComponents(View view) {

        selectVehicleCategory = getArguments().getString("CATEGORY");

//        Toast.makeText(getContext(), "ccc="+selectVehicleCategory, Toast.LENGTH_SHORT).show();

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mExampleList = new ArrayList<>();

//        list = (ArrayList<VehicleCategory>) vehicleCategoryDao.getAllCategory();
//        for (int i=0;i<=5;i++){
        if (selectVehicleCategory.equalsIgnoreCase("sedan")){
            mExampleList.add(new Vehicle(1,"Honda city","1000","","honda"));
            mExampleList.add(new Vehicle(1," Hyundai Verna","2000","","verna"));
            mExampleList.add(new Vehicle(1,"Civic","5000","","civic"));
            mExampleList.add(new Vehicle(1,"Audi A4","16000","","a4"));
            mExampleList.add(new Vehicle(1,"Audi A6","17000","","a6"));
            mExampleList.add(new Vehicle(1,"Skoda Slavia","4000","","slavia"));
            mExampleList.add(new Vehicle(1,"Lexus Es","8000","","lexus"));
            mExampleList.add(new Vehicle(1,"Maruti Ciaz","3000","","ciaz"));
            mExampleList.add(new Vehicle(1,"Jaguar Xe","15000","","xe"));
            mExampleList.add(new Vehicle(1,"Volkswagen Vento","2000","","vento"));
            mExampleList.add(new Vehicle(1,"BMW 5","25000","","bmw5"));
            mExampleList.add(new Vehicle(1,"Mercedes E ","35000","","mercedes"));
        }else if (selectVehicleCategory.equalsIgnoreCase("hatchback")){
            mExampleList.add(new Vehicle(1,"Hyundai Grand i10","100","","grand"));
            mExampleList.add(new Vehicle(1,"Maruti Alto","200","","alto"));
            mExampleList.add(new Vehicle(1,"Altroz","200","","altroz"));
            mExampleList.add(new Vehicle(1,"Maruti Baleno","200","","baleno"));
            mExampleList.add(new Vehicle(1,"Maruti Celerio","200","","celerio"));
            mExampleList.add(new Vehicle(1,"Ford Figo","200","","figo"));
            mExampleList.add(new Vehicle(1,"Brios","200","","brios"));
            mExampleList.add(new Vehicle(1,"Ignis","200","","ignis"));
            mExampleList.add(new Vehicle(1,"Nios","200","","nios"));
            mExampleList.add(new Vehicle(1,"Polo","200","","polo"));
            mExampleList.add(new Vehicle(1,"Santro","200","","santro"));
            mExampleList.add(new Vehicle(1,"Swift","200","","swift"));
        }else if (selectVehicleCategory.equalsIgnoreCase("suv")){
            mExampleList.add(new Vehicle(1,"Mahindra Xuv 500","1000","","xuv"));
            mExampleList.add(new Vehicle(1,"Toyota Fortuner","2000","","for"));
            mExampleList.add(new Vehicle(1,"Hyundai Creta ","1000","","creta"));
            mExampleList.add(new Vehicle(1,"Hyubdai Venue","1000","","venue"));
            mExampleList.add(new Vehicle(1,"Brezza","1000","","brezza"));
            mExampleList.add(new Vehicle(1,"Tucson","1000","","tucson"));
            mExampleList.add(new Vehicle(1,"Seltos","1000","","seltos"));
            mExampleList.add(new Vehicle(1,"Mg Hector","1000","","hector"));
            mExampleList.add(new Vehicle(1,"Ecospsort","1000","","ecospsort"));
            mExampleList.add(new Vehicle(1,"Duster","1000","","duster"));
            mExampleList.add(new Vehicle(1,"Jeep Compasss","1000","","compass"));
            mExampleList.add(new Vehicle(1,"Kushaq","1000","","kushaq"));
            mExampleList.add(new Vehicle(1,"Nexon","1000","","nexon"));
            mExampleList.add(new Vehicle(1,"Taigun","1000","","taigun"));
            mExampleList.add(new Vehicle(1,"Aircross","1000","","aircross"));
        }else if (selectVehicleCategory.equalsIgnoreCase("muv")){
           // mExampleList.add(new Vehicle(1,"BMW 8 series","10000","","bmw"));
           // mExampleList.add(new Vehicle(1,"Mercedes benz e class","20000","","mer"));
            mExampleList.add(new Vehicle(1,"Toyota Crysta","10000","","crysta"));
            mExampleList.add(new Vehicle(1,"Maruti Ertiga","10000","","ertiga"));
            mExampleList.add(new Vehicle(1,"Marazzo","10000","","marazzo"));
            mExampleList.add(new Vehicle(1,"Toyota Vellfire","10000","","vellfire"));
            mExampleList.add(new Vehicle(1,"Carnival","10000","","carnival"));
            mExampleList.add(new Vehicle(1,"Carens","10000","","carens"));
            mExampleList.add(new Vehicle(1,"Triber","10000","","triber"));

        }

//        }

        mExampleAdapter = new VehicleAdapter(getContext(), mExampleList,this);
        mRecyclerView.setAdapter(mExampleAdapter);


    }
    @Override
    public void onClick(int position) {
        Intent vehicleInfoPage = new Intent(getActivity(), BookingCarActivity.class);
        vehicleInfoPage.putExtra("car", selectVehicleCategory);
        vehicleInfoPage.putExtra("vechicle", String.valueOf(mExampleList.get(position).getVehicleName()));
        vehicleInfoPage.putExtra("rate", String.valueOf(mExampleList.get(position).getPrice()));
        vehicleInfoPage.putExtra("pickup", "true");
        startActivity(vehicleInfoPage);
    }


    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt, Toast.LENGTH_SHORT);
        toast.show();
    }
}
