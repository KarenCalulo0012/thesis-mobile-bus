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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.model.Status;
import com.example.transporte_pay.views.activity.MapsActivity;
import com.example.transporte_pay.views.activity.PaymentActivity;
import com.example.transporte_pay.views.activity.RefundActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> bookingList;
    private Context context;
    private Integer roleId;

    public void setBookingList(List<Booking> bookings, Integer r) {
        this.bookingList = bookings;
        this.roleId = r;
//        this.logsListener = listener;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locations, date, status, name,busPlate_tv,passengerType_tv;


        Button view,geo,refund;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
            view = itemView.findViewById(R.id.button);
            geo = itemView.findViewById(R.id.geobutton);
            name = itemView.findViewById(R.id.passengerName_tv);
            busPlate_tv = itemView.findViewById(R.id.busPlate_tv);
            refund = itemView.findViewById(R.id.buttonRefund);
            passengerType_tv = itemView.findViewById(R.id.passengerType_tv);
            refund.setText("Refund");
        }
    }

    @NonNull
    @NotNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.booking_list, parent, false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String destination, start, locations, name,bus_gcash_number,longitude,latitude,platenumber,grand_total,id,scDate,passenger_type;
        int status,bus_id;

        if (roleId == 2) {
            holder.name.setVisibility(View.VISIBLE);
        }
        id   = String.valueOf(bookingList.get(position).getId());
        name = bookingList.get(position).getUser().getName();
        status = bookingList.get(position).getStatus().getId();
        grand_total = String.valueOf(bookingList.get(position).getGrandTotal());
        bus_gcash_number = bookingList.get(position).getBus().getGcashNumber();
        bus_id = bookingList.get(position).getBus().getId();
        latitude = bookingList.get(position).getBus().getLatitude();
        longitude = bookingList.get(position).getBus().getLongitude();
        scDate = bookingList.get(position).getSchedule().getScheduleDate();
        passenger_type = bookingList.get(position).getPassenger_type();
        Log.d("Longitude",longitude);
        Log.d("Longitude",latitude);
        if(passenger_type.equals("Normal") || passenger_type.equals("normal")){
            passenger_type="Ordinary";
        }

        platenumber = bookingList.get(position).getBus().getPlateNumber();
        holder.name.setText(name);
        destination = bookingList.get(position).getSchedule().getDestination().getName();
        start = bookingList.get(position).getSchedule().getStartingPoint().getName();
        locations = context.getString(R.string.locations, start, destination);

        if(status == 13 || status == 1 || status == 4 || status == 12) {
            holder.refund.setVisibility(View.GONE);

        }else{
            holder.refund.setVisibility(View.VISIBLE);
        }
        if (status == 4) {
            holder.view.setVisibility(View.GONE);
        }

        if (status == 6) {
            holder.geo.setVisibility(View.VISIBLE);
        }

        if(roleId == 3){
            holder.passengerType_tv.setVisibility(View.VISIBLE);

        }
        if(roleId == 4){
            holder.busPlate_tv.setVisibility(View.VISIBLE);
        }
        holder.refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builderNew = new AlertDialog.Builder(v.getRootView().getContext());

                    View dialogView1 = LayoutInflater.from(v.getRootView().getContext())
                            .inflate(R.layout.refund_dialog, null);
                    Button proceedRefund;
                    proceedRefund = dialogView1.findViewById(R.id.cancel_book_btn);
                    builderNew.setView(dialogView1)
                            .setCancelable(true)
                            .show();

                    proceedRefund.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, RefundActivity.class);
                            intent.putExtra("fullname", name);
                            intent.putExtra("amount", grand_total);
                            intent.putExtra("booking_id", id);
                            intent.putExtra("scDate",scDate);
                            context.startActivity(intent);
                        }
                    });

                }
            });
        holder.locations.setText(locations);
        holder.date.setText(bookingList.get(position).getSchedule().getScheduleDate());
        holder.status.setText(bookingList.get(position).getStatus().getTitle());
        holder.busPlate_tv.setText(platenumber);
        holder.passengerType_tv.setText(passenger_type);
        holder.view.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext())
                    .inflate(R.layout.receipt_dialog, null);

            TextView transId, plateNo, locations1, fare, quantity, gTotal;
            Button bookingPay, bookingCancel;
            ImageView close;

            transId = dialogView.findViewById(R.id.transId_tv);
            plateNo = dialogView.findViewById(R.id.plate_tv);
            locations1 = dialogView.findViewById(R.id.location_tv);
            fare = dialogView.findViewById(R.id.fare_tv);
            quantity = dialogView.findViewById(R.id.qty_tv);
            gTotal = dialogView.findViewById(R.id.gTotal_tv);

            bookingPay = dialogView.findViewById(R.id.payment_btn);
            bookingCancel = dialogView.findViewById(R.id.cancel_btn);


            //SET DATA
            String a, b, c;
            b = bookingList.get(position).getSchedule().getDestination().getName();
            a = bookingList.get(position).getSchedule().getStartingPoint().getName();
            c = context.getString(R.string.locations, a, b);
            locations1.setText(c);
            transId.setText(Integer.toString(bookingList.get(position).getId()));
            plateNo.setText(bookingList.get(position).getBus().getPlateNumber());
            fare.setText(Integer.toString(bookingList.get(position).getFareAmount()));
            quantity.setText(Integer.toString(bookingList.get(position).getQuantity()));
            gTotal.setText(Integer.toString(bookingList.get(position).getGrandTotal()));

            if (roleId == 2) {
                bookingCancel.setVisibility(View.GONE);
                bookingPay.setVisibility(View.GONE);
            }else if (status !=1){
                bookingPay.setVisibility(View.GONE);
                bookingCancel.setVisibility(View.GONE);

            }

            bookingPay.setOnClickListener(v13 -> {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("busGcash", bus_gcash_number);
                intent.putExtra("fullname", name);
                intent.putExtra("bus_id", Integer.toString(bus_id));
                intent.putExtra("transId", Integer.toString(bookingList.get(position).getId()));
                context.startActivity(intent);
            });
            bookingCancel.setOnClickListener(v12 -> {

            });
            builder.setView(dialogView)
                    .setCancelable(true)
                    .show();


        });
        
        holder.geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("longitude", longitude.trim());
                intent.putExtra("latitude", latitude.trim());
                intent.putExtra("fullname", name);
                intent.putExtra("platenumber", platenumber);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
