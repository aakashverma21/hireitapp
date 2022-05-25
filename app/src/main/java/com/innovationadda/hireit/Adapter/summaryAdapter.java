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
import com.innovationadda.hireit.Model.summary;
import com.innovationadda.hireit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class summaryAdapter extends RecyclerView.Adapter<summaryAdapter.summaryHolder> {

    private Context context;
    private ArrayList<summary> summary;
    private onSummaryListener onSummaryListener;

    public summaryAdapter(Context context, ArrayList<summary> summary, onSummaryListener onSummaryListener){
        this.context = context;
        this.summary = summary;
        this.onSummaryListener = onSummaryListener;
    }

    @NonNull
    @Override
    public summaryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.summary,null);
        return new summaryHolder(view,onSummaryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull summaryHolder summaryHolder, int i) {
        summary v = summary.get(i);
        summaryHolder.vehicleName.setText(v.getVehicleName());
        summaryHolder.cost.setText(v.getPrice());
        summaryHolder.bookingID.setText(""+v.getBookingID());
        summaryHolder.returnDate.setText(v.getReturndate());
        summaryHolder.pickupDate.setText(v.getPickupdate());
    }

    @Override
    public int getItemCount() {
        return summary.size();
    }

    class summaryHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView vehicleName, bookingID,pickupDate, returnDate, cost;
        onSummaryListener onSummaryListener;
        public summaryHolder(@NonNull View itemView, onSummaryListener onSummaryListener) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.vehicleName);
            bookingID = itemView.findViewById(R.id.summaryText);
            pickupDate = itemView.findViewById(R.id.tvpickup);
            returnDate = itemView.findViewById(R.id.tvreturn);
            cost = itemView.findViewById(R.id.tvcost);

            this.onSummaryListener = onSummaryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSummaryListener.onClick(getAdapterPosition());
        }
    }

    public interface onSummaryListener{
        void onClick(int position);
    }

}
