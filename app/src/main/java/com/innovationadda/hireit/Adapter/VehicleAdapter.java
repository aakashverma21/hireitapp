package com.innovationadda.hireit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.innovationadda.hireit.Model.Vehicle;
import com.innovationadda.hireit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {

    private Context context;
    private ArrayList<Vehicle> vehicle;
    private onVehicleListener onVehicleListener;

    public VehicleAdapter(Context context, ArrayList<Vehicle> vehicle, onVehicleListener onVehicleListener){
        this.context = context;
        this.vehicle = vehicle;
        this.onVehicleListener = onVehicleListener;
    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vehicle_card,null);
        return new VehicleHolder(view,onVehicleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder vehicleHolder, int i) {
        Vehicle v = vehicle.get(i);
        vehicleHolder.vehicle.setText(v.getVehicleName());
        vehicleHolder.price.setText(v.getPrice()+"/day");
        vehicleHolder.detail.setText(v.getDetail());
        String url = v.getVehicleImageURL();
        int urlchange = R.drawable.suv;
        if (url.equalsIgnoreCase("verna")){
            urlchange=R.drawable.verna;
        }else if (url.equalsIgnoreCase("honda")){
            urlchange=R.drawable.honda;
        }else if (url.equalsIgnoreCase("grand")){
            urlchange=R.drawable.grand;
        }else if (url.equalsIgnoreCase("alto")){
            urlchange=R.drawable.alto;
        }else if (url.equalsIgnoreCase("xuv")){
            urlchange=R.drawable.xuv;
        }else if (url.equalsIgnoreCase("bmw")){
            urlchange=R.drawable.bmw;
        }else if (url.equalsIgnoreCase("for")){
            urlchange=R.drawable.fortu;
        }else if (url.equalsIgnoreCase("mer")){
            urlchange=R.drawable.mer;
        }else if (url.equalsIgnoreCase("elantra")){
            urlchange=R.drawable.fortu;
        }
        else if (url.equalsIgnoreCase("civic")){
            urlchange=R.drawable.civic;
        }
        else if (url.equalsIgnoreCase("a4")){
            urlchange=R.drawable.a4;
        }
        else if (url.equalsIgnoreCase("a6")){
            urlchange=R.drawable.a6;
        }
        else if (url.equalsIgnoreCase("slavia")){
            urlchange=R.drawable.slavia;
        }
        else if (url.equalsIgnoreCase("lexus")){
            urlchange=R.drawable.lexus;
        }
        else if (url.equalsIgnoreCase("ciaz")){
            urlchange=R.drawable.ciaz;
        }
        else if (url.equalsIgnoreCase("xe")){
            urlchange=R.drawable.xe;
        }
        else if (url.equalsIgnoreCase("vento")){
            urlchange=R.drawable.vento;
        }
        else if (url.equalsIgnoreCase("bmw5")){
            urlchange=R.drawable.bmw5;
        }
        else if (url.equalsIgnoreCase("mercedes")){
            urlchange=R.drawable.mercesdes;
        }
        else if (url.equalsIgnoreCase("altroz")){
            urlchange=R.drawable.altroz;
        }
        else if (url.equalsIgnoreCase("baleno")){
            urlchange=R.drawable.baleno;
        }
        else if (url.equalsIgnoreCase("celerio")){
            urlchange=R.drawable.celerio;
        }
        else if (url.equalsIgnoreCase("figo")){
            urlchange=R.drawable.figo;
        }
        else if (url.equalsIgnoreCase("brios")){
            urlchange=R.drawable.brio;
        }
        else if (url.equalsIgnoreCase("ignis")){
            urlchange=R.drawable.ignis;
        }
        else if (url.equalsIgnoreCase("nios")){
            urlchange=R.drawable.nios;
        }
        else if (url.equalsIgnoreCase("polo")){
            urlchange=R.drawable.polo;
        }
        else if (url.equalsIgnoreCase("santro")){
            urlchange=R.drawable.santro;
        }
        else if (url.equalsIgnoreCase("swift")){
            urlchange=R.drawable.swift;
        }
        else if (url.equalsIgnoreCase("creta")){
            urlchange=R.drawable.creta;
        }
        else if (url.equalsIgnoreCase("venue")){
            urlchange=R.drawable.venue;
        }
        else if (url.equalsIgnoreCase("brezza")){
            urlchange=R.drawable.brezza;
        }
        else if (url.equalsIgnoreCase("tucson")){
            urlchange=R.drawable.tucson;
        }
        else if (url.equalsIgnoreCase("seltos")){
            urlchange=R.drawable.seltos;
        }
        else if (url.equalsIgnoreCase("hector")){
            urlchange=R.drawable.hector;
        }
        else if (url.equalsIgnoreCase("ecospsort")){
            urlchange=R.drawable.ecosport;
        }
        else if (url.equalsIgnoreCase("duster")){
            urlchange=R.drawable.duster;
        }
        else if (url.equalsIgnoreCase("compass")){
            urlchange=R.drawable.compass;
        }
        else if (url.equalsIgnoreCase("kushaq")){
            urlchange=R.drawable.kushaq;
        }
        else if (url.equalsIgnoreCase("nexon")){
            urlchange=R.drawable.nexon;
        }
        else if (url.equalsIgnoreCase("taigun")){
            urlchange=R.drawable.taigun;
        }
        else if (url.equalsIgnoreCase("aircross")){
            urlchange=R.drawable.aircross;
        }
        else if (url.equalsIgnoreCase("crysta")){
            urlchange=R.drawable.crysta;
        }
        else if (url.equalsIgnoreCase("ertiga")){
            urlchange=R.drawable.ertiga;
        }
        else if (url.equalsIgnoreCase("marazzo")){
            urlchange=R.drawable.marazzo;
        }
        else if (url.equalsIgnoreCase("vellfire")){
            urlchange=R.drawable.vellfire;
        }
        else if (url.equalsIgnoreCase("carnival")){
            urlchange=R.drawable.carnival;
        }
        else if (url.equalsIgnoreCase("carens")){
            urlchange=R.drawable.carens;
        }
        else if (url.equalsIgnoreCase("triber")){
            urlchange=R.drawable.triber;
        }






//        Log.d("MainActivity",url);

        Picasso.get().load(urlchange).into(vehicleHolder.imageView);


//        Picasso.get().load(v.getVehicleImageURL()).into(vehicleHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return vehicle.size();
    }

    class VehicleHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView vehicle, detail, price;
        ImageView imageView;
        ConstraintLayout card;
        onVehicleListener onVehicleListener;
        public VehicleHolder(@NonNull View itemView, onVehicleListener onVehicleListener) {
            super(itemView);
            vehicle = itemView.findViewById(R.id.vehicle);
            detail = itemView.findViewById(R.id.detail);
            card = itemView.findViewById(R.id.card);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.vehicleImage);

            this.onVehicleListener = onVehicleListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVehicleListener.onClick(getAdapterPosition());
        }
    }

    public interface onVehicleListener{
        void onClick(int position);
    }

}
