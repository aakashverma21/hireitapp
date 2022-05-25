package com.innovationadda.hireit.FragmentPages;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.innovationadda.hireit.Adapter.VehicleCategoryAdapter;
import com.innovationadda.hireit.LoginActivity;
import com.innovationadda.hireit.Model.VehicleCategory;
import com.innovationadda.hireit.R;

public class VehicleCategoryFragment extends Fragment implements VehicleCategoryAdapter.onCategoryListener {
//public class VehicleCategoryFragment extends Fragment {
//    private VehicleCategoryDao vehicleCategoryDao;

    private RecyclerView recyclerView;
    private VehicleCategoryAdapter adapter;

    private RecyclerView mRecyclerView;
    private VehicleCategoryAdapter mExampleAdapter;
    private ArrayList<VehicleCategory> mExampleList;


    private Button home;

    private ArrayList<VehicleCategory> list;

    public VehicleCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_vehicle_category, container, false);
        
        initComponents(view);
        listenHandler();



        return view;
    }

    private void initComponents(View view) {

//        vehicleCategoryDao = Room.databaseBuilder(getContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
//                .build()
//                .vehicleCategoryDao();

//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mExampleList = new ArrayList<>();

//        list = (ArrayList<VehicleCategory>) vehicleCategoryDao.getAllCategory();

        mExampleList.add(new VehicleCategory("sedan",1,1,"sedan"));
        mExampleList.add(new VehicleCategory("suv",2,2,"suv"));
        mExampleList.add(new VehicleCategory("muv",3,3,"muv"));
        mExampleList.add(new VehicleCategory("hatchback",3,3,"hatchback"));

        mExampleAdapter = new VehicleCategoryAdapter(getContext(), mExampleList,this);
        mRecyclerView.setAdapter(mExampleAdapter);


//        adapter = new VehicleCategoryAdapter(getContext(), list,this);
//        recyclerView.setAdapter(adapter);
    }

    private void listenHandler() {

      /*  home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(getContext(), LoginActivity.class);
                startActivity(homePage);
            }
        });*/


    }

    //DEBUGING
    private void toast(String txt) {
        Toast toast = Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onCategoryClick(int position) {
//        toast(mExampleList.get(position).getCategory());

        String selectedCategory = mExampleList.get(position).getCategory();

        Bundle bundle=new Bundle();
        bundle.putString("CATEGORY", selectedCategory);

        Fragment viewVehicle = new VehicleFragment();
        viewVehicle.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, viewVehicle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onSelectClick(int position) {
//        toast(mExampleList.get(position).getCategory() + " Select");
    }

}
