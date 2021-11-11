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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.request.ConductorRequest;
import com.example.transporte_pay.utils.SessionManager;
import com.example.transporte_pay.views.activity.MapsActivity;
import com.example.transporte_pay.views.activity.PaymentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConductorListAdapter extends RecyclerView.Adapter<ConductorListAdapter.ViewHolder> {
    private List<Booking> bookingList;
    private Context context;
    private Integer roleId,id;
    public  String token;
    SessionManager sessionManager;





    public void setBookingList(List<Booking> bookings, Integer r) {
        this.bookingList = bookings;
        this.roleId = r;
//        this.logsListener = listener;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locations, date, status, name;
        LinearLayout linearLayout;

        Button view,geo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
            view = itemView.findViewById(R.id.button);
            geo = itemView.findViewById(R.id.geobutton);
            name = itemView.findViewById(R.id.passengerName_tv);
            linearLayout=itemView.findViewById(R.id.box);

        }
    }
    @NonNull
    @Override
    public ConductorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sessionManager = new SessionManager(parent.getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);
        HashMap<String, Integer> uSerDetails= sessionManager.getID();
        id =  Integer.valueOf(uSerDetails.get(SessionManager.ID));

        return new ConductorListAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.booking_list, parent, false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String destination, start, locations, name,bus_gcash_number,longitude,latitude,platenumber,sStatus,userId,bookingId;
        int status,bus_id;

        if (roleId == 2) {
            holder.name.setVisibility(View.VISIBLE);
        }
        sStatus= bookingList.get(position).getSchedule().getsStatus();

        if(sStatus.equals("open") )
        {
//            Toast.makeText(context, sStatus, Toast.LENGTH_SHORT).show();
            String condId = bookingList.get(position).getConductorId().toString();
            String drId = bookingList.get(position).getDriverId().toString();
            String uId = id.toString();

            if(condId.equals(uId) || drId.equals(uId) )
            {
                name = bookingList.get(position).getUser().getName();
                userId= String.valueOf(bookingList.get(position).getUser().getId());
                bookingId= String.valueOf(bookingList.get(position).getId());
                status = bookingList.get(position).getStatus().getId();
                bus_gcash_number = bookingList.get(position).getBus().getGcashNumber();
                bus_id = bookingList.get(position).getBus().getId();
                latitude = bookingList.get(position).getBus().getLatitude();
                longitude = bookingList.get(position).getBus().getLongitude();
                platenumber = bookingList.get(position).getBus().getPlateNumber();
                holder.name.setText(name);
                destination = bookingList.get(position).getSchedule().getDestination().getName();
                start = bookingList.get(position).getSchedule().getStartingPoint().getName();
                locations = context.getString(R.string.locations, start, destination);
                if (status == 3 ) {
                    holder.geo.setText("Verify");
                    holder.geo.setVisibility(View.VISIBLE);
                }
                holder.locations.setText(locations);
                holder.date.setText(bookingList.get(position).getSchedule().getScheduleDate());
                holder.status.setText(bookingList.get(position).getStatus().getTitle());

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
                    }
                    bookingPay.setVisibility(View.GONE);
                    bookingCancel.setVisibility(View.GONE);

                    bookingPay.setOnClickListener(v13 -> {
                        Log.e("CLICK", "YOU CLICKED BOOKING PAY BUTTON");
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


                        ConductorRequest conductorRequest = new ConductorRequest();
                        conductorRequest.setUserId(userId);
                        conductorRequest.setBookingId(bookingId);
                        conductorRequest.setHealth("approve");


                        Call<ConductorRequest> conductorRequestCall = ApiClient.getConductorClient().getConductorBusDetail(conductorRequest,"Bearer :" +token);
                        conductorRequestCall.enqueue(new Callback<ConductorRequest>() {
                            @Override
                            public void onResponse(Call<ConductorRequest> call, Response<ConductorRequest> response) {
                                if(response.body().isStatus()){
//                                Toast.makeText(context,response.body().getRemarks(),Toast.LENGTH_LONG);
                                    holder.status.setText("onBoard");
                                }else{
//                                Toast.makeText(context,response.body().getRemarks(),Toast.LENGTH_LONG);
                                }


                            }

                            @Override
                            public void onFailure(Call<ConductorRequest> call, Throwable t) {

                            }
                        });


                    }
                });

            }else{
                holder.linearLayout.setVisibility(View.GONE);
            }

        }else{
                holder.linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
