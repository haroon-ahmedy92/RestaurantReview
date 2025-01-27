// HomeFragment.java
package com.example.restaurantreview;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    private EditText etRestaurantName, etReviewText;
    private RatingBar ratingBar;
    private Button btnSubmit, btnMarkFavorite;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etRestaurantName = view.findViewById(R.id.etRestaurantName);
        etReviewText = view.findViewById(R.id.etReviewText);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnMarkFavorite = view.findViewById(R.id.btnMarkFavorite);
        dbHelper = new DatabaseHelper(getContext());

        btnSubmit.setOnClickListener(v -> submitReview());
        btnMarkFavorite.setOnClickListener(v -> markAsFavorite());

        return view;
    }

    private void submitReview() {
        String restaurantName = etRestaurantName.getText().toString();
        String reviewText = etReviewText.getText().toString();
        int rating = (int) ratingBar.getRating();

        if (restaurantName.isEmpty() || reviewText.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.addReview(restaurantName, reviewText, rating);
        Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();

        // Clear fields
        etRestaurantName.setText("");
        etReviewText.setText("");
        ratingBar.setRating(0);
    }

    private void markAsFavorite() {
        String restaurantName = etRestaurantName.getText().toString();

        if (restaurantName.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a restaurant name", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.addFavorite(restaurantName);
        Toast.makeText(getContext(), "Marked as favorite!", Toast.LENGTH_SHORT).show();
    }
}