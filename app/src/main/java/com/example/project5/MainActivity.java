package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity serves as the entry point of the application.
 * Displays a menu with options to navigate to different activities.
 * Implements {@link AdapterView.OnItemClickListener} to handle menu items clicks.
 *
 * @author Syona Bhandari
 * @author Rhemie Patiak
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // constants
    private static final String[] MENU_OPTIONS = {
            "Select Pizza",
            "View Current Order",
            "Store Orders"
    };

    /**
     * Called when the activity is created.
     * Sets up the ListView and attaches a click listener to its items.
     *
     * @param savedInstanceState Saved instance state for activity restoration.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // links listview from layout
        ListView listView = findViewById(R.id.mainListView);
        // to populate the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                MENU_OPTIONS
        );
        listView.setAdapter(adapter);
        // set up click listener
        listView.setOnItemClickListener(this);
    }

    /**
     * Handles item clicks in the ListView.
     * Navigates to the corresponding activity based on the selected menu option.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     *            will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // handles item clicks based on position
        Intent intent = null;
        switch (position) {
            case 0: // Select Pizza
                intent = new Intent(MainActivity.this, SelectPizzaActivity.class);
                Toast.makeText(this, "Clicked: " + MENU_OPTIONS[position], Toast.LENGTH_SHORT).show();
                break;
            case 1: // View Current Order
                intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
                Toast.makeText(this, "Clicked: " + MENU_OPTIONS[position], Toast.LENGTH_SHORT).show();
                break;
            case 2: // Store Orders
                intent = new Intent(MainActivity.this, StoreOrderActivity.class);
                Toast.makeText(this, "Clicked: " + MENU_OPTIONS[position], Toast.LENGTH_SHORT).show();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
