// ReviewFragment.java
package com.example.restaurantreview;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;

public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadReviews();

        return view;
    }

    private void loadReviews() {
        List<Review> reviews = dbHelper.getAllReviews();
        adapter = new ReviewAdapter(reviews, this::onEditReview, this::onDeleteReview, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void onEditReview(Review review) {
        // Update the review in the database
        dbHelper.updateReview(review.getRestaurantName(), review.getReviewText(), review.getRating());
        loadReviews(); // Refresh the list
        Toast.makeText(getContext(), "Review updated!", Toast.LENGTH_SHORT).show();
    }

    private void onDeleteReview(Review review) {
        dbHelper.deleteReview(review.getRestaurantName());
        loadReviews(); // Refresh the list
        Toast.makeText(getContext(), "Review deleted!", Toast.LENGTH_SHORT).show();
    }
}