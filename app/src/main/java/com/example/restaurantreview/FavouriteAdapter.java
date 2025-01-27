// FavouriteAdapter.java
package com.example.restaurantreview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private List<String> favoriteList;
    private OnFavouriteClickListener onFavouriteClickListener;

    public FavouriteAdapter(List<String> favoriteList, OnFavouriteClickListener onFavouriteClickListener) {
        this.favoriteList = favoriteList;
        this.onFavouriteClickListener = onFavouriteClickListener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourite, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        String favorite = favoriteList.get(position);
        holder.tvFavoriteName.setText(favorite);

        holder.btnRemove.setOnClickListener(v -> onFavouriteClickListener.onFavouriteClick(favorite));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvFavoriteName;
        Button btnRemove;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFavoriteName = itemView.findViewById(R.id.tvFavoriteName);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    public interface OnFavouriteClickListener {
        void onFavouriteClick(String favorite);
    }
}