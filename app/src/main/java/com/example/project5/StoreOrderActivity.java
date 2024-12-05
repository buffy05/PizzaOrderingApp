package com.example.project5;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import RUPizza.*;

public class StoreOrderActivity extends AppCompatActivity {

    private Spinner orderNumberSpinner;
    private TextView orderTotalText;
    private RecyclerView storeOrdersRecyclerView;
    private Button cancelOrderButton, placeOrderButton;
    private PizzaAdapter pizzaAdapter;
    private OrderHistory orderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_order);

        // setup Toolbar
        Toolbar toolbar = findViewById(R.id.storeOrderToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Store Orders");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Views
        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        orderTotalText = findViewById(R.id.orderTotalText);
        storeOrdersRecyclerView = findViewById(R.id.storeOrdersRecyclerView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // setup RecyclerView
        storeOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize orderHistory
        orderHistory = OrderHistory.getInstance();

        // setup Spinner (Order Numbers)
        populateOrderSpinner();
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateOrderDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // cancel order Button
        cancelOrderButton.setOnClickListener(v -> cancelSelectedOrder());

        // Place Order Button
        placeOrderButton.setOnClickListener(v -> placeSelectedOrder());

    }

    private void populateOrderSpinner() {
        List<String> orderNumbers = new ArrayList<>();
        for (Order order : orderHistory.getOrders()) {
            orderNumbers.add(String.valueOf(order.getOrderNumber()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(adapter);

        if (!orderNumbers.isEmpty()) {
            orderNumberSpinner.setSelection(0);
            updateOrderDetails();
        }
    }

    private void updateOrderDetails() {
        if (orderNumberSpinner.getSelectedItem() != null) {
            int selectedOrderNumber = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
            Order selectedOrder = orderHistory.getOrderByNumber(selectedOrderNumber);

            if (selectedOrder != null) {
                orderTotalText.setText(String.format("Order Total (Tax Included): $%.2f", selectedOrder.calculateOrderTotal()));
                pizzaAdapter = new PizzaAdapter(this, selectedOrder.getPizzas());
                storeOrdersRecyclerView.setAdapter(pizzaAdapter);
                pizzaAdapter.notifyDataSetChanged();
            }
        }
    }

    private void cancelSelectedOrder() {
        if (orderNumberSpinner.getSelectedItem() != null) {
            int selectedOrderNumber = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
            Order selectedOrder = orderHistory.getOrderByNumber(selectedOrderNumber);

            if (selectedOrder != null) {
                // remove selected order from history
                orderHistory.removeOrder(selectedOrder);
                populateOrderSpinner();
                Toast.makeText(this, "Order " + selectedOrderNumber + " has been canceled successfully.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // if no order is selected
            Toast.makeText(this, "Select an order to cancel.", Toast.LENGTH_SHORT).show();
        }
    }

    private void placeSelectedOrder() {
        if (orderNumberSpinner.getSelectedItem() != null) {
            // just to acknowledge the order placement
            int selectedOrderNumber = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
            Order selectedOrder = orderHistory.getOrderByNumber(selectedOrderNumber);

            if (selectedOrder != null) {
                // remove order from orderHistory
                orderHistory.removeOrder(selectedOrder);
                // clear RecyclerView and reset the order total
                pizzaAdapter = new PizzaAdapter(this, new ArrayList<>());
                storeOrdersRecyclerView.setAdapter(pizzaAdapter);
                orderTotalText.setText("Order Total (Tax Included): $ 0.00");
                // repopulate spinner with remaining orders
                populateOrderSpinner();
                // confirmation text
                Toast.makeText(this, "Order " + selectedOrderNumber + " have been placed  successfully.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // if no order is selected
            Toast.makeText(this, "No order selected to place.", Toast.LENGTH_SHORT).show();
        }
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
