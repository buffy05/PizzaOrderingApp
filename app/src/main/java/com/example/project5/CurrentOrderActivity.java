package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Objects;

import RUPizza.Order;
import RUPizza.OrderHistory;
import RUPizza.Pizza;

public class CurrentOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCurrentOrder;
    private TextView textViewOrderNumber, textViewSubtotal, textViewSalesTax, textViewOrderTotal;
    private Button buttonRemovePizza, buttonClearOrder, buttonPlaceOrder;
    private Order currentOrder;
    private PizzaAdapter pizzaAdapter;
    private static final String TAG = "CurrentOrderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Current Order");
            }
        }

        // Initialize Views
        recyclerViewCurrentOrder = findViewById(R.id.recyclerViewCurrentOrder);
        textViewOrderNumber = findViewById(R.id.textViewOrderNumber);
        textViewSubtotal = findViewById(R.id.subtotalTextView);
        textViewSalesTax = findViewById(R.id.taxTextView);
        textViewOrderTotal = findViewById(R.id.totalTextView);
        buttonRemovePizza = findViewById(R.id.removeButton);
        buttonClearOrder = findViewById(R.id.clearOrderButton);
        buttonPlaceOrder = findViewById(R.id.placeOrderButton);

        // Initialize RecyclerView
        recyclerViewCurrentOrder.setLayoutManager(new LinearLayoutManager(this));

        // retrieve currentOrder
        currentOrder = AppContext.getCurrentOrder();
        Log.d("CurrentOrderActivity", "Loaded Current Order: " + currentOrder);

        // initialize PizzaAdapter with current order pizzas
        pizzaAdapter = new PizzaAdapter(this, currentOrder.getPizzas());
        recyclerViewCurrentOrder.setAdapter(pizzaAdapter);

        // Display initial order details
        updateOrderDetails();

        // Set up button listeners
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Remove Pizza Button
        buttonRemovePizza.setOnClickListener(v -> removeSelectedPizza());

        // Clear Order Button
        buttonClearOrder.setOnClickListener(v -> {
            clearOrder();
            Log.d(TAG, "Order cleared");
        });

        // Place Order Button
        buttonPlaceOrder.setOnClickListener(v -> {
            placeOrder();
            Log.d(TAG, "Order placed: " + currentOrder.getOrderNumber());
        });
    }

    private void updateOrderDetails() {
        // Update order number and price summary
        textViewOrderNumber.setText("Order Number: " + currentOrder.getOrderNumber());
        textViewSubtotal.setText("Subtotal: $" + String.format("%.2f", currentOrder.calculateTotal()));
        textViewSalesTax.setText("Tax: $" + String.format("%.2f", currentOrder.calculateSalesTax()));
        textViewOrderTotal.setText("Total: $" + String.format("%.2f", currentOrder.calculateOrderTotal()));

        if (currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Your current order is empty.", Toast.LENGTH_SHORT).show();
        }
    }


    private void removeSelectedPizza() {
        // Remove the selected pizza
        int selectedPosition = pizzaAdapter.getSelectedPosition(); // Assume adapter tracks selection
        if (selectedPosition == -1) {
            Toast.makeText(this, "Please select a pizza to remove.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentOrder.getPizzas().remove(selectedPosition);
        pizzaAdapter.notifyItemRemoved(selectedPosition);
        updateOrderDetails();
    }

    private void clearOrder() {
        // Clear the current order
        currentOrder.getPizzas().clear();
        pizzaAdapter.notifyDataSetChanged();
        updateOrderDetails();
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }

    private void placeOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Order is empty! Add pizzas to place an order.", Toast.LENGTH_SHORT).show();
            return;
        }

        // adding order to OrderHistory
        AppContext appContext = (AppContext) getApplicationContext();
        appContext.getOrderHistory().addOrder(currentOrder);

        // reset
        appContext.resetCurrentOrder();

        // Show confirmation
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        // Navigate back to the main menu
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
