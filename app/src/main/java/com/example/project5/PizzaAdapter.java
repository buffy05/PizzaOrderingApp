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
import java.util.List;

import RUPizza.*;

/**
 * Adapter class for managing and displaying pizza items in a RecyclerView.
 * Supports displaying both {@link Pizza} objects and {@link PizzaObject} objects.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<?> pizzaList;
    private Context context;
    private int selectedPosition = -1;

    /**
     * Constructs a new PizzaAdapter.
     *
     * @param context the context in which the adapter operates.
     * @param pizzaList the list of pizzas or pizza objects to display.
     */
    public PizzaAdapter(Context context, List<?> pizzaList) {
        this.context = context;
        this.pizzaList = pizzaList;
    }

    // private methods

    /**
     * Configures the ViewHolder for {@link Pizza} objects.
     *
     * @param holder   the ViewHolder to configure
     * @param pizza    the pizza object
     * @param position the position in the RecyclerView
     */
    private void configurePizzaView(PizzaViewHolder holder, Pizza pizza, int position) {
        holder.itemName.setText(pizza.toString());
        holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
        holder.selectedToppings.setText(String.format("%s%s, Selected Toppings: %s", context.getString(R.string.crust1), pizza.getCrust(), pizza.getToppings()));

        // Highlight the selected pizza
        holder.itemView.setBackgroundColor(selectedPosition == position ? 0xFFE0E0E0 : 0xFFFFFFFF);
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

        // Hide unused UI elements
        holder.toppingSpinner.setVisibility(View.GONE);
        holder.sizeGroup.setVisibility(View.GONE);
        holder.addToCartButton.setVisibility(View.GONE);
    }

    /**
     * Configures the ViewHolder for {@link PizzaObject} objects.
     *
     * @param holder      the ViewHolder to configure
     * @param pizzaObject the pizza object
     */
    private void configurePizzaObjectView(PizzaViewHolder holder, PizzaObject pizzaObject) {
        Pizza pizza = pizzaObject.getPizza();

        holder.itemName.setText(pizzaObject.getPizzaName());
        holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
        holder.selectedToppings.setText(String.format("%s%s, Selected Toppings: %s", context.getString(R.string.crust1), pizza.getCrust(), pizza.getToppings()));
        holder.itemImage.setImageResource(pizzaObject.getImageResId());

        setupToppingSpinner(holder, pizzaObject);
        setupSizeSelector(holder, pizza);
        setupAddToCartButton(holder, pizzaObject);
    }

    /**
     * Sets up the topping spinner for build-your-own pizzas.
     *
     * @param holder      the ViewHolder to configure
     * @param pizzaObject the pizza object
     */
    private void setupToppingSpinner(PizzaViewHolder holder, PizzaObject pizzaObject) {
        ArrayAdapter<Topping> spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                Topping.values()
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.toppingSpinner.setAdapter(spinnerAdapter);
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
                Pizza pizza = pizzaObject.getPizza();
                if (pizza.getToppings().contains(selectedTopping)) {
                    pizza.removeTopping(selectedTopping);
                } else {
                    pizza.addTopping(selectedTopping);
                }
                holder.selectedToppings.setText(String.format("%s%s, Selected Toppings: %s", context.getString(R.string.crustDetails1), pizza.getCrust(), pizza.getToppings()));
                holder.itemPrice.setText(String.format("Price: $%.2f", pizza.price()));
                holder.toppingSpinner.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Sets up the size selector for the pizza.
     *
     * @param holder the ViewHolder to configure
     * @param pizza  the pizza object
     */
    private void setupSizeSelector(PizzaViewHolder holder, Pizza pizza) {
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
    }

    /**
     * Sets up the "Add to Cart" button.
     *
     * @param holder      the ViewHolder to configure
     * @param pizzaObject the pizza object
     */
    private void setupAddToCartButton(PizzaViewHolder holder, PizzaObject pizzaObject) {
        holder.addToCartButton.setOnClickListener(v -> {
            if (holder.sizeGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(context, "Please select a size before adding to cart.", Toast.LENGTH_SHORT).show();
                return;
            }

            Pizza pizza = pizzaObject.getPizza();
            Order currentOrder = OrderHistory.getInstance().getCurrentOrder();
            currentOrder.addPizza(pizza);

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

    // public methods

    /**
     * Creates and returns a new {@link PizzaViewHolder} for displaying pizza data.
     * This method inflates the layout for a single pizza item and initializes
     * the ViewHolder with its views.
     *
     * @param parent   the parent ViewGroup into which the new View will be added
     * @param viewType the view type of the new View 
     * @return a new instance of {@link PizzaViewHolder}
     */
    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pizza_row_item, parent, false);
        return new PizzaViewHolder(view);
    }

    /**
     * Binds the data for the current item in the pizza list to the specified ViewHolder.
     * This method determines whether the item is a {@link Pizza} or a {@link PizzaObject}
     * and configures the ViewHolder accordingly.
     *
     * @param holder   the ViewHolder to bind the data to
     * @param position the position of the item in the pizza list
     */
    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Object item = pizzaList.get(position);

        if (item instanceof Pizza) {
            configurePizzaView(holder, (Pizza) item, position);
        } else if (item instanceof PizzaObject) {
            configurePizzaObjectView(holder, (PizzaObject) item);
        }
    }

    /**
     * Gets the selected position in the RecyclerView.
     *
     * @return the selected position
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Returns the total number of items in the pizza list.
     *
     * @return the size of the pizza list.
     */
    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for managing individual pizza items in the RecyclerView.
     */
    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, selectedToppings;
        Spinner toppingSpinner;
        RadioGroup sizeGroup;
        Button addToCartButton;
        /**
         * Constructs a new PizzaViewHolder for managing pizza item views.
         *
         * @param itemView the view representing a single pizza item.
         */
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
