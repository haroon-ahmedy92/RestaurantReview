// ReviewAdapter.java
package com.example.restaurantreview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviewList;
    private OnReviewClickListener onEditClickListener;
    private OnReviewClickListener onDeleteClickListener;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, OnReviewClickListener onEditClickListener, OnReviewClickListener onDeleteClickListener, Context context) {
        this.reviewList = reviewList;
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.tvRestaurantName.setText(review.getRestaurantName());
        holder.tvReviewText.setText(review.getReviewText());
        holder.tvRating.setText(String.valueOf(review.getRating()));

        holder.btnEdit.setOnClickListener(v -> showEditDialog(review));
        holder.btnDelete.setOnClickListener(v -> onDeleteClickListener.onReviewClick(review));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    private void showEditDialog(Review review) {
        // Inflate the custom layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_review, null);

        // Initialize views in the dialog
        EditText etEditRestaurantName = dialogView.findViewById(R.id.etEditRestaurantName);
        EditText etEditReviewText = dialogView.findViewById(R.id.etEditReviewText);
        RatingBar ratingBarEdit = dialogView.findViewById(R.id.ratingBarEdit);
        Button btnApplyChanges = dialogView.findViewById(R.id.btnApplyChanges);

        // Pre-fill the dialog with the current review details
        etEditRestaurantName.setText(review.getRestaurantName());
        etEditReviewText.setText(review.getReviewText());
        ratingBarEdit.setRating(review.getRating());

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // Handle the "Apply Changes" button click
        btnApplyChanges.setOnClickListener(v -> {
            // Get the updated review details
            String updatedRestaurantName = etEditRestaurantName.getText().toString();
            String updatedReviewText = etEditReviewText.getText().toString();
            int updatedRating = (int) ratingBarEdit.getRating();

            // Validate the input
            if (updatedRestaurantName.isEmpty() || updatedReviewText.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the review in the database
            onEditClickListener.onReviewClick(new Review(updatedRestaurantName, updatedReviewText, updatedRating));

            // Dismiss the dialog
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvRestaurantName, tvReviewText, tvRating;
        Button btnEdit, btnDelete;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvReviewText = itemView.findViewById(R.id.tvReviewText);
            tvRating = itemView.findViewById(R.id.tvRating);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnReviewClickListener {
        void onReviewClick(Review review);
    }
}