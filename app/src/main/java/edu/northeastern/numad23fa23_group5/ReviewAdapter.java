package edu.northeastern.numad23fa23_group5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {
    private final ArrayList<ItemCard> itemList;
    private Context context;
    private ItemClickListener listener;


    //Constructor
    public ReviewAdapter(ArrayList<ItemCard> itemList) {
        this.itemList = itemList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_collapse, parent, false);
        final ReviewHolder holder = new ReviewHolder(view, listener, parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ItemCard currentItem = itemList.get(position);
        holder.productName.setText(currentItem.getTitle());
        holder.brandName.setText("by "+currentItem.getBrand());
        holder.price.setText("$" + currentItem.getPrice());
        holder.rating.setRating(currentItem.getRatings());
        new ImageLoaderThread(holder.thumbnail, currentItem.getThumbnailURL()).start();
        holder.reviews.setText("Number of Reviews: " + currentItem.getReviews());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.rating.getVisibility() == View.VISIBLE)
                   {
                    holder.rating.setVisibility(View.GONE);
                    holder.reviews.setVisibility(View.GONE);
                    holder.thumbnail.setVisibility(View.VISIBLE);
                    holder.imageViewLarge.setVisibility(View.GONE);
                }
                else {
                    holder.rating.setVisibility(View.VISIBLE);
                    holder.reviews.setVisibility(View.VISIBLE);
                    holder.thumbnail.setVisibility(View.GONE);
                    holder.imageViewLarge.setVisibility(View.VISIBLE);
                    new ImageLoaderThread(holder.imageViewLarge, currentItem.getImageURL()).start();
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // loads image from URL
    private static class ImageLoaderThread extends Thread {
        private final WeakReference<ImageView> imageViewReference;
        private final String imageUrl;

        public ImageLoaderThread(ImageView imageView, String url) {
            imageViewReference = new WeakReference<>(imageView);
            imageUrl = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(input);

                // Use a Handler to update the UI thread with the loaded image
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = imageViewReference.get();
                        if (imageView != null && bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

