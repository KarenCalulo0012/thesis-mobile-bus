package com.example.transporte_pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Schedule;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private List<Schedule> scheduleList;
    private Context context;
    private RecyclerViewClickListener listener;

//    public ScheduleAdapter(){
//
//    }
    public interface RecyclerViewClickListener {
        void OnClick(View v, int position);
    }

    public void RecyclerViewClickListener (RecyclerViewClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<Schedule> schedules, RecyclerViewClickListener listener) {
        this.scheduleList = schedules;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView aTime, dTime, plateNo, seat, fare;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            aTime = (TextView) itemView.findViewById(R.id.arriveTime_tv);
            dTime = (TextView) itemView.findViewById(R.id.departTime_tv);
            plateNo = (TextView) itemView.findViewById(R.id.plateNo_tv);
            seat = (TextView) itemView.findViewById(R.id.seats_tv);
            fare = (TextView) itemView.findViewById(R.id.fare_tv);
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
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item,parent, false );
        context = parent.getContext();

        return new ScheduleAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.schedule_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScheduleAdapter.ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.aTime.setText(scheduleList.get(position).getTimeArrival());
        holder.dTime.setText(scheduleList.get(position).getTimeDeparture());
        holder.plateNo.setText(scheduleList.get(position).getBus().getPlateNumber());
        holder.fare.setText(Integer.toString(scheduleList.get(position).getFare()));
        holder.seat.setText(scheduleList.get(position).getSeatsAvailable());
//        holder.bind(schedule, listener);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
