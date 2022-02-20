package com.example.transporte_pay.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Date;
import com.example.transporte_pay.data.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private List<Date> dateList;
    private Context context;
    private RecyclerViewClickListener listener;


    public interface RecyclerViewClickListener{
        void OnClick(View itemView, int adapterPosition);
    }

    public void setData(ArrayList<Date> dates, DateAdapter.RecyclerViewClickListener listener) {
        this.dateList = dates;
        this.listener = listener;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dateText, monthText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
            monthText = (TextView) itemView.findViewById(R.id.monthText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnClick(itemView,getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new DateAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.schedule_list_horizontal,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapter.ViewHolder holder, int position) {
        Date date = dateList.get(position);
        holder.dateText.setText(dateList.get(position).getDate());
        holder.monthText.setText(dateList.get(position).getMonth());

    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
