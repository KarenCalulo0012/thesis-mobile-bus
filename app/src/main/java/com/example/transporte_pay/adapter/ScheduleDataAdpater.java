package com.example.transporte_pay.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.model.Schedules;
import com.example.transporte_pay.views.activity.MapsActivity;
import com.example.transporte_pay.views.activity.PaymentActivity;
import com.example.transporte_pay.views.activity.RefundActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDataAdpater extends RecyclerView.Adapter<ScheduleDataAdpater.ViewHolder> {
    private List<Schedules> schedulesList;
    private Context context;
    private RecyclerViewClickListener listener;

    public interface RecyclerViewClickListener {
        void OnClick(View v, int position);
    }
    public void RecyclerViewClickListener (ScheduleDataAdpater.RecyclerViewClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<Schedules> schedules, RecyclerViewClickListener listener) {
        this.schedulesList = schedules;
        this.listener = listener;
        notifyDataSetChanged();
    }
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView aTime, dTime, plateNo, capacity, fare,startingPoint,destinationPoint,scDate;

    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        startingPoint = (TextView) itemView.findViewById(R.id.startpoint_tv);
        destinationPoint = (TextView) itemView.findViewById(R.id.destination_point_tv);
        aTime = (TextView) itemView.findViewById(R.id.arriveTime_tv);
        dTime = (TextView) itemView.findViewById(R.id.departTime_tv);
        plateNo = (TextView) itemView.findViewById(R.id.plateNo_tv);
        capacity = (TextView) itemView.findViewById(R.id.seats_tv);
        fare = (TextView) itemView.findViewById(R.id.fare_tv);
        scDate = (TextView) itemView.findViewById(R.id.scDate_tv);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.OnClick(itemView,getAdapterPosition());
    }
}

    @NonNull
    @NotNull
    @Override
    public ScheduleDataAdpater.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ScheduleDataAdpater.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.schedules_list,parent,false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ScheduleDataAdpater.ViewHolder holder, int position) {
        Schedules schedules = schedulesList.get(position);
        holder.startingPoint.setText(schedulesList.get(position).getStartingPoint());
        holder.destinationPoint.setText(schedulesList.get(position).getDestination());
        holder.aTime.setText(schedulesList.get(position).getTimeArrival());
        holder.dTime.setText(schedulesList.get(position).getTimeDeparture());
        holder.plateNo.setText(schedulesList.get(position).getPlateNumber());
        holder.fare.setText(Integer.toString(schedulesList.get(position).getFare()));
        holder.capacity.setText("Seats : "+Integer.toString(schedulesList.get(position).getCapacity()));
        holder.scDate.setText(schedulesList.get(position).getScheduleDate());


    }

    @Override
    public int getItemCount() {
        return schedulesList.size();
    }
}
