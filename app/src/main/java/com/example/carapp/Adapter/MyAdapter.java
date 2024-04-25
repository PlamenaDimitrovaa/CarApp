package com.example.carapp.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carapp.Activity.CarDetailsActivity;
import com.example.carapp.Models.Car;
import com.example.carapp.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    // Variables
    private Context context;
    private List<Car> carList;

    public MyAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.car_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Car currentCar = carList.get(position);

        holder.model.setText(currentCar.getModel());
        holder.make.setText(currentCar.getMake());
        holder.name.setText(currentCar.getUserName());

        String imageUrl = currentCar.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                currentCar.getTimeAdded().getSeconds()*1000
        );
        holder.dateAdded.setText(timeAgo);


        // Glide Library to display the image
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarDetailsActivity.class);
            intent.putExtra("carDetails", currentCar.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }


    // View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView model, make, dateAdded, name;
        public ImageView image, shareButton;
        public String userId, username;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            model = itemView.findViewById(R.id.car_title_list);
            make = itemView.findViewById(R.id.car_thought_list);
            dateAdded = itemView.findViewById(R.id.car_timestamp_list);

            image = itemView.findViewById(R.id.car_image_list);
            name = itemView.findViewById(R.id.car_row_username);

            shareButton = itemView.findViewById(R.id.car_row_share_button);

            shareButton.setOnClickListener(v -> {

                // onClick()
                // Sharing the Post....
            });

        }
    }
}