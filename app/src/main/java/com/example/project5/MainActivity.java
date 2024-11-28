package com.example.project5; // Adjust the package name according to your project

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] menuOptions = {
            "Select Pizza",
            "View Current Order",
            "Store Orders"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the ListView from the layout
        ListView listView = findViewById(R.id.mainListView);

        // Set up an ArrayAdapter to populate the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menuOptions
        );
        listView.setAdapter(adapter);

        // Set the click listener for the ListView
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Handle item clicks based on position
        Intent intent = null;

        switch (position) {
            case 0: // Choose Pizza
                intent = new Intent(MainActivity.this, SelectPizzaActivity.class);
                Toast.makeText(this, "Clicked: " + menuOptions[position], Toast.LENGTH_SHORT).show();
                break;
            case 1: // Build Your Own Pizza
                //intent = new Intent(MainActivity.this, BuildYourOwnPizzaActivity.class);
                Toast.makeText(this, "Clicked: " + menuOptions[position], Toast.LENGTH_SHORT).show();
                break;
            case 2: // View Current Order
                //intent = new Intent(MainActivity.this, ViewCurrentOrderActivity.class);
                Toast.makeText(this, "Clicked: " + menuOptions[position], Toast.LENGTH_SHORT).show();
                break;
            case 3: // All Past Orders
                //intent = new Intent(MainActivity.this, AllPastOrdersActivity.class);
                Toast.makeText(this, "Clicked: " + menuOptions[position], Toast.LENGTH_SHORT).show();
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
