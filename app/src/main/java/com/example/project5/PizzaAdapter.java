package com.example.project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    public interface OnPizzaClickListener {
        void onPizzaClick(int position);
    }

    private final List<String> pizzaNames;
    private final List<Integer> pizzaImages;
    private final OnPizzaClickListener listener;

    public PizzaAdapter(List<String> pizzaNames, List<Integer> pizzaImages, OnPizzaClickListener listener) {
        this.pizzaNames = pizzaNames;
        this.pizzaImages = pizzaImages;
        this.listener = listener;
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView pizzaImage;
        TextView pizzaName;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaImage = itemView.findViewById(R.id.pizzaImage);
            pizzaName = itemView.findViewById(R.id.pizzaName);
        }
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        holder.pizzaName.setText(pizzaNames.get(position));
        holder.pizzaImage.setImageResource(pizzaImages.get(position));
        holder.itemView.setOnClickListener(v -> listener.onPizzaClick(position));
    }

    @Override
    public int getItemCount() {
        return pizzaNames.size();
    }
}