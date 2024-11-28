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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import RUPizza.*;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<PizzaObject> pizzaObjectList;
    private Context context;

    public PizzaAdapter(Context context, List<PizzaObject> pizzaList) {
        this.context = context;
        this.pizzaObjectList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pizza_row_item, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        PizzaObject pizzaObject = pizzaObjectList.get(position);
        Pizza pizza = pizzaObject.getPizza();

        // Bind data to views
        holder.itemName.setText(pizzaObject.getPizzaName());
        holder.selectedToppings.setText("Crust: "+ pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());
        //holder.itemPrice.setText(String.format("Price: $%.2f", pizzaObject.price()));
        holder.itemImage.setImageResource(pizzaObject.getImageResId());

        // Spinner and RadioGroup binding logic
        // Additional logic for selected toppings, size, etc.
        // Populate Spinner with Toppings enum values
        ArrayAdapter<Topping> spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                Topping.values()
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.toppingSpinner.setAdapter(spinnerAdapter);

        // RadioGroup size listener
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
            // Update price based on size
            holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
        });


        // Set listener for Spinner if needed
        holder.toppingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Topping selectedTopping = (Topping) parent.getItemAtPosition(position);
                // Handle selected topping (e.g., update selectedToppings TextView)
                //holder.selectedToppings.setText(selectedTopping.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle case when no selection is made
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaObjectList.size();
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice;
        Spinner toppingSpinner;
        TextView selectedToppings;
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
}
