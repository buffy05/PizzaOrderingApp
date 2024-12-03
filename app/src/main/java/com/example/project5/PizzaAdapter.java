package com.example.project5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import RUPizza.*;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<?> pizzaList;
    private Context context;
    private int selectedPosition = -1;

    public PizzaAdapter(Context context, List<?> pizzaList) {
        this.context = context;
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pizza_row_item, parent, false);
        return new PizzaViewHolder(view);
    }

    //will need to cut down / separate into other functions for clarity, needs to be below 40 lines
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Object item = pizzaList.get(position);

        if (item instanceof Pizza) {
            // CurrentOrderActivity logic for pizza
            Pizza pizza = (Pizza) item;

            holder.itemName.setText(pizza.toString());
            holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
            holder.selectedToppings.setText("Crust: " + pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());

            holder.itemView.setBackgroundColor(selectedPosition == position ? 0xFFE0E0E0 : 0xFFFFFFFF);
            holder.itemView.setOnClickListener(v -> {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            });
            holder.toppingSpinner.setVisibility(View.GONE);
            holder.sizeGroup.setVisibility(View.GONE);
            holder.addToCartButton.setVisibility(View.GONE);
        } else if (item instanceof PizzaObject) {
            // SelectPizzaActivity logic for PizzaObject
            PizzaObject pizzaObject = (PizzaObject) item;
            Pizza pizza = pizzaObject.getPizza();

            holder.itemName.setText(pizzaObject.getPizzaName());
            holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
            holder.selectedToppings.setText("Crust: " + pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());
            holder.itemImage.setImageResource(pizzaObject.getImageResId());

            ArrayAdapter<Topping> spinnerAdapter = new ArrayAdapter<>(
                    context,
                    android.R.layout.simple_spinner_item,
                    Topping.values()
            );
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.toppingSpinner.setAdapter(spinnerAdapter);

            holder.sizeGroup.setOnCheckedChangeListener((group, checkedId) -> {
                Size selectedSize;
                if (checkedId == R.id.sizeSmall) {
                    selectedSize = Size.SMALL;
                } else if (checkedId == R.id.sizeMedium) {
                    selectedSize = Size.MEDIUM;
                } else if (checkedId == R.id.sizeLarge) {
                    selectedSize = Size.LARGE;
                } else {
                    selectedSize = Size.SMALL; // Default
                }
                pizza.setSize(selectedSize);
                holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
            });
            holder.toppingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Topping selectedTopping = (Topping) parent.getItemAtPosition(position);
                    if (position == 0) return;

                    if (!pizzaObject.getPizzaName().contains("Build Your Own")) {
                        Toast.makeText(context, "Toppings can only be added to 'Build Your Own' pizzas.", Toast.LENGTH_SHORT).show();
                        holder.toppingSpinner.setSelection(0);
                        return;
                    }

                    if (pizza.getToppings().contains(selectedTopping)) {
                        pizza.removeTopping(selectedTopping);
                    } else {
                        pizza.addTopping(selectedTopping);
                    }

                    holder.selectedToppings.setText("Crust: " + pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());
                    holder.toppingSpinner.setSelection(0);
                    holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No action needed
                }
            });

            holder.addToCartButton.setOnClickListener(v -> {
                if (holder.sizeGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(context, "Please select a size before adding to cart.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new androidx.appcompat.app.AlertDialog.Builder(context)
                        .setTitle("Add to Cart")
                        .setMessage("Pizza successfully added to the order:\n" + pizza.toString())
                        .setPositiveButton("OK", (dialog, which) -> {
                            Toast.makeText(context, "Pizza added to cart.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }

    }

    //may not really need this, might delete later
    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, selectedToppings;
        Spinner toppingSpinner;
        RadioGroup sizeGroup;
        Button addToCartButton;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            toppingSpinner = itemView.findViewById(R.id.toppingSpinner);
            selectedToppings = itemView.findViewById(R.id.selectedToppings);
            sizeGroup = itemView.findViewById(R.id.sizeGroup);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
    public void updateData(List<?> newPizzaList) {
        this.pizzaList = newPizzaList;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setPizzas(List<?> pizzas) {
        this.pizzaList = pizzas;
        notifyDataSetChanged();
    }
}
