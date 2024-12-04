package com.example.project5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import RUPizza.*;

public class SelectPizzaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<PizzaObject> pizzaObjectList;
    private Order currentOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_pizza_row);

        // toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Select Pizza");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize pizzaList and adapter
        pizzaObjectList = new ArrayList<>();
        populatePizzaList();
        pizzaAdapter = new PizzaAdapter(this, pizzaObjectList);
        recyclerView.setAdapter(pizzaAdapter);

        // retrieve currentOrder
        currentOrder = OrderHistory.getInstance().getCurrentOrder();
        Log.d("SelectPizzaActivity", "Loaded Current Order: " + currentOrder);

        // handle back button navigation
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                passSelectedPizzasToOrder(true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            passSelectedPizzasToOrder(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void passSelectedPizzasToOrder(boolean isNavigatingBack) {
        // retrieve current order from orderHistory
        Log.d("SelectPizzaActivity", "Current Order before update: " + currentOrder);

        // Loop through selected pizzas and add them to the current order
        for (PizzaObject pizzaObject : pizzaObjectList) {
            if (pizzaObject.isSelected()) {
                Pizza pizza = pizzaObject.getPizza();
                OrderHistory.getInstance().getCurrentOrder().addPizza(pizza);
                Log.d("SelectPizzaActivity", "Added pizza: " + pizza);
            }
        }
        Log.d("SelectPizzaActivity", "Updated Current Order: " + OrderHistory.getInstance().getCurrentOrder());

        if (!isNavigatingBack) {
            // Navigate to CurrentOrderActivity
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        } else {
            // Navigate back to MainActivity (unconditionally)
            finish();
        }
    }

    private void populatePizzaList() {
        PizzaFactory ChicagoPizzaFactory = new ChicagoPizza();
        PizzaFactory NYPizzaFactory = new NYPizza();
        //BBQ Chicken pizzas
        Pizza BBQChicagoPizza = ChicagoPizzaFactory.createBBQChicken(Size.SMALL);
        Pizza BBQNYPizza = NYPizzaFactory.createBBQChicken(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style BBQ Pizza", BBQChicagoPizza, R.drawable.bbq_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style BBQ Pizza",BBQNYPizza, R.drawable.ny_bbq));
        //Deluxe pizzas
        Pizza DeluxeChicagoPizza = ChicagoPizzaFactory.createDeluxe(Size.SMALL);
        Pizza DeluxeNYPizza = NYPizzaFactory.createDeluxe(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Deluxe Pizza",DeluxeChicagoPizza, R.drawable.deluxe_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style Deluxe Pizza",DeluxeNYPizza, R.drawable.ny_deluxe));
        //Meatzza pizzas
        Pizza MeatzzaChicagoPizza = ChicagoPizzaFactory.createMeatzza(Size.SMALL);
        Pizza MeatzzaNYPizza = NYPizzaFactory.createMeatzza(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Meatzza Pizza",MeatzzaChicagoPizza, R.drawable.meatzza));
        pizzaObjectList.add(new PizzaObject("NY Style Meatzza Pizza",MeatzzaNYPizza, R.drawable.ny_meatzza));
        //Build Your Own pizzas
        Pizza BYOChicagoPizza = ChicagoPizzaFactory.createBuildYourOwn(Size.SMALL);
        Pizza BYONYPizza = NYPizzaFactory.createBuildYourOwn(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Build Your Own Pizza",BYOChicagoPizza, R.drawable.build_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style Build Your Own Pizza",BYONYPizza, R.drawable.ny_build));
    }

}
