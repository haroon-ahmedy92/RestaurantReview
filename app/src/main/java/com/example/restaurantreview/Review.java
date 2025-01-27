// Review.java
package com.example.restaurantreview;

public class Review {
    private String restaurantName;
    private String reviewText;
    private int rating;

    public Review(String restaurantName, String reviewText, int rating) {
        this.restaurantName = restaurantName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters
    public String getRestaurantName() { return restaurantName; }
    public String getReviewText() { return reviewText; }
    public int getRating() { return rating; }
}