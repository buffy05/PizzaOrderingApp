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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

    //will need to cut down / separate into other functions for clarity, needs to be below 40 lines
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        PizzaObject pizzaObject = pizzaObjectList.get(position);
        Pizza pizza = pizzaObject.getPizza();

        //bind data to views
        holder.itemName.setText(pizzaObject.getPizzaName());

        //NEED TO FIX LINE BELOW, HARDCODED!!!!
        holder.selectedToppings.setText("Crust: "+ pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());

        //holder.itemPrice.setText(String.format("Price: $%.2f", pizzaObject.price()));
        holder.itemImage.setImageResource(pizzaObject.getImageResId());

        //populate spinner with Toppings enum values
        ArrayAdapter<Topping> spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                Topping.values()
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.toppingSpinner.setAdapter(spinnerAdapter);

        //radioGroup size listener, may need to make this its own function outside of this function
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
            //update price based on size, NEED TO FIX LINE BELOW, HARDCODED!!!!
            holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
        });


        //spinner listener
        holder.toppingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Topping selectedTopping = (Topping) parent.getItemAtPosition(position);
                if(position == 0) return;
                // Allow adding toppings only for "Build Your Own" pizzas
                if (!(pizzaObject.getPizzaName().equals("Chicago Style Build Your Own Pizza") ||
                        pizzaObject.getPizzaName().equals("NY Style Build Your Own Pizza"))) {
                    Toast.makeText(context, "Toppings can only be added to 'Build Your Own' pizzas.", Toast.LENGTH_SHORT).show();
                    holder.toppingSpinner.setSelection(0); // Reset spinner to placeholder
                    return;
                }
                //add/remove selected toppings
                if(pizza.getToppings().contains(selectedTopping)) {
                    pizza.removeTopping(selectedTopping);
                }
                else {
                    pizza.addTopping(selectedTopping);
                }
                //NEED TO FIX LINE BELOW, HARDCODED!!!!
                holder.selectedToppings.setText("Crust: "+ pizza.getCrust() + ", Selected Toppings: " + pizza.getToppings());
                //Toast.makeText(context, "Pizza Details: " + pizza.toString(), Toast.LENGTH_SHORT).show();
                //revert to default spinner position to prevent any additional additions
                holder.toppingSpinner.setSelection(0);
                //NEED TO FIX LINE BELOW, HARDCODED!!!!
                holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //no purpose, but needed it to use onItemSelected
            }
        });

        //addToCart button listener, for now will just put up an alert dialogue that says the pizza
        //has been sucessfully added to order with pizza details, need to implement order logic (add to order)
        holder.addToCartButton.setOnClickListener(v -> {
            //checks if a size has been selected, if not can't add pizza to cart
            if (holder.sizeGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(context, "Please select a size before adding to cart.", Toast.LENGTH_SHORT).show();
                return;
            }

            //AlertDialog to confirm addition to cart, if ok is pressed Toast alert shows up and pizza will be added to order
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Add to Cart")
                    .setMessage("Pizza successfully added to the order:\n" + pizza.toString())
                    .setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(context, "Pizza added to cart.", Toast.LENGTH_SHORT).show();
                        //add pizza to order logic to be implemented
                    })
                    .setNegativeButton("Cancel", null) //will not add to cart if cancel is chosen
                    .show();
        });
    }

    //may not really need this, might delete later
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
