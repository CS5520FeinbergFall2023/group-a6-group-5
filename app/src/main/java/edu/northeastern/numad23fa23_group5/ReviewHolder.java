package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewHolder extends RecyclerView.ViewHolder {
    TextView productName;
    TextView brandName;
    TextView price;
    RatingBar rating;
    ImageView thumbnail;

    public ReviewHolder(@NonNull View itemView, final ItemClickListener listener, Context context) {
        super(itemView);
        productName=itemView.findViewById(R.id.item_name);
        brandName=itemView.findViewById(R.id.brand_name);
        price=itemView.findViewById(R.id.item_price);
        rating=itemView.findViewById(R.id.ratingBar);
        thumbnail=itemView.findViewById(R.id.imageViewThumbnail);
    }
}