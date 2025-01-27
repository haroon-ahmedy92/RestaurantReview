// FavouriteFragment.java
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

public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavouriteAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        List<String> favorites = dbHelper.getAllFavorites();
        adapter = new FavouriteAdapter(favorites, this::onRemoveFavorite);
        recyclerView.setAdapter(adapter);
    }

    private void onRemoveFavorite(String restaurantName) {
        dbHelper.removeFavorite(restaurantName);
        loadFavorites(); // Refresh the list
        Toast.makeText(getContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
    }
}