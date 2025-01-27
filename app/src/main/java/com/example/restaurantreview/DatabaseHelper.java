// DatabaseHelper.java
package com.example.restaurantreview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RestaurantDB";
    private static final int DATABASE_VERSION = 1;

    // Table: Reviews
    private static final String TABLE_REVIEWS = "reviews";
    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
    private static final String COLUMN_REVIEW_TEXT = "review_text";
    private static final String COLUMN_RATING = "rating";

    // Table: Favorites
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_FAVORITE_ID = "favorite_id";
    private static final String COLUMN_FAVORITE_NAME = "favorite_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Reviews Table
        String createReviewsTable = "CREATE TABLE " + TABLE_REVIEWS + "("
                + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RESTAURANT_NAME + " TEXT,"
                + COLUMN_REVIEW_TEXT + " TEXT,"
                + COLUMN_RATING + " INTEGER)";
        db.execSQL(createReviewsTable);

        // Create Favorites Table
        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FAVORITE_NAME + " TEXT)";
        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Add a new review
    public void addReview(String restaurantName, String reviewText, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_NAME, restaurantName);
        values.put(COLUMN_REVIEW_TEXT, reviewText);
        values.put(COLUMN_RATING, rating);
        db.insert(TABLE_REVIEWS, null, values);
        db.close();
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REVIEWS, null);

        if (cursor.moveToFirst()) {
            do {
                Review review = new Review(
                        cursor.getString(1), // restaurantName
                        cursor.getString(2), // reviewText
                        cursor.getInt(3)     // rating
                );
                reviewList.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reviewList;
    }

    // Delete a review
    public void deleteReview(String restaurantName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REVIEWS, COLUMN_RESTAURANT_NAME + " = ?", new String[]{restaurantName});
        db.close();
    }

    // Update a review
    public void updateReview(String restaurantName, String newReviewText, int newRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_TEXT, newReviewText);
        values.put(COLUMN_RATING, newRating);
        db.update(TABLE_REVIEWS, values, COLUMN_RESTAURANT_NAME + " = ?", new String[]{restaurantName});
        db.close();
    }

    // Add a favorite
    public void addFavorite(String restaurantName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAVORITE_NAME, restaurantName);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    // Remove a favorite
    public void removeFavorite(String restaurantName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_FAVORITE_NAME + " = ?", new String[]{restaurantName});
        db.close();
    }

    // Get all favorites
    public List<String> getAllFavorites() {
        List<String> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);

        if (cursor.moveToFirst()) {
            do {
                favoriteList.add(cursor.getString(1)); // favorite_name
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }
}