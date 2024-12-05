package com.example.project5;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import java.util.Objects;
import RUPizza.*;

/**
 * This class manages the Current Order screen.
 * Displays the current order details and allows the user to manage pizzas.
 *
 * @author Rhemie Patiak
 * @author Syona Bhandari
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private static final String TAG = "CurrentOrderActivity";
    private RecyclerView recyclerViewCurrentOrder;
    private TextView textViewOrderNumber, textViewSubtotal, textViewSalesTax, textViewOrderTotal;
    private Button buttonRemovePizza, buttonClearOrder, buttonPlaceOrder;
    private Order currentOrder;
    private PizzaAdapter pizzaAdapter;

    /**
     * Called when the activity is created.
     * Initializes views, set up listeners, and populate data.
     *
     * @param savedInstanceState Saved instance state for activity restoration.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Current Order");
        // initialize views
        recyclerViewCurrentOrder = findViewById(R.id.recyclerViewCurrentOrder);
        textViewOrderNumber = findViewById(R.id.textViewOrderNumber);
        textViewSubtotal = findViewById(R.id.subtotalTextView);
        textViewSalesTax = findViewById(R.id.taxTextView);
        textViewOrderTotal = findViewById(R.id.totalTextView);
        buttonRemovePizza = findViewById(R.id.removeButton);
        buttonClearOrder = findViewById(R.id.clearOrderButton);
        buttonPlaceOrder = findViewById(R.id.placeOrderButton);
        // initialize RecyclerView
        recyclerViewCurrentOrder.setLayoutManager(new LinearLayoutManager(this));
        // retrieve currentOrder
        currentOrder = OrderHistory.getInstance().getCurrentOrder();
        Log.d("CurrentOrderActivity", "Loaded Current Order: " + currentOrder);
        Log.d("CurrentOrderActivity", "Pizzas in Current Order: " + currentOrder.getPizzas());
        // initialize PizzaAdapter
        pizzaAdapter = new PizzaAdapter(this, currentOrder.getPizzas());
        recyclerViewCurrentOrder.setAdapter(pizzaAdapter);
        pizzaAdapter.notifyDataSetChanged();
        // display initial order details
        updateOrderDetails();
        setupButtonListeners();
    }

    // private methods

    /**
     * Sets up listeners for all buttons on the activity.
     */
    private void setupButtonListeners() {
        // remove pizza button
        buttonRemovePizza.setOnClickListener(v -> removeSelectedPizza());

        // clear order button
        buttonClearOrder.setOnClickListener(v -> {
            clearOrder();
            Log.d(TAG, "Order cleared");
        });

        // place order button
        buttonPlaceOrder.setOnClickListener(v -> {
            placeOrder();
            Log.d(TAG, "Order placed: " + currentOrder.getOrderNumber());
        });
    }

    /**
     * Updates the order details displayed on the screen.
     */
    private void updateOrderDetails() {
        // update order number and price summary
        textViewOrderNumber.setText("Order Number: " + currentOrder.getOrderNumber());
        textViewSubtotal.setText("Subtotal: $" + String.format("%.2f", currentOrder.calculateTotal()));
        textViewSalesTax.setText("Tax: $" + String.format("%.2f", currentOrder.calculateSalesTax()));
        textViewOrderTotal.setText("Total: $" + String.format("%.2f", currentOrder.calculateOrderTotal()));

        if (currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Your current order is empty.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Removes the selected pizza from the current order.
     */
    private void removeSelectedPizza() {
        // remove the selected pizza
        int selectedPosition = pizzaAdapter.getSelectedPosition();
        if (selectedPosition == -1) {
            Toast.makeText(this, "Please select a pizza to remove.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentOrder.getPizzas().remove(selectedPosition);
        pizzaAdapter.notifyItemRemoved(selectedPosition);
        updateOrderDetails();
    }

    /**
     * Clears the current order and updates the UI.
     */
    private void clearOrder() {
        // clear the current order
        currentOrder.getPizzas().clear();
        pizzaAdapter.notifyDataSetChanged();
        updateOrderDetails();
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Places the current order and resets for a new order.
     */
    private void placeOrder() {
        if (currentOrder.getPizzas().isEmpty()) {
            Toast.makeText(this, "Order is empty! Add pizzas to place an order.", Toast.LENGTH_SHORT).show();
            return;
        }
        // adding order to OrderHistory
        OrderHistory orderHistory = OrderHistory.getInstance();
        orderHistory.addOrder(currentOrder);

        // confirmation text
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        // reset
        orderHistory.resetCurrentOrder();
        finish();
    }

    // public methods

    /**
     * Handles toolbar back button clicks.
     *
     * @param item The menu item that was selected.
     * @return true if the back button is clicked; otherwise false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
