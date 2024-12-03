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
    private ArrayList<Pizza> selectedPizzas;
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
        selectedPizzas = new ArrayList<>();
        populatePizzaList();
        pizzaAdapter = new PizzaAdapter(this, pizzaObjectList);
        recyclerView.setAdapter(pizzaAdapter);

        // retrieve currentOrder
        AppContext appContext = (AppContext) getApplicationContext();
        currentOrder = appContext.getCurrentOrder();

        // handle back button navigation
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                passSelectedPizzasToOrder(true);
            }
        });
    }

    private void finalizeOrder() {
        passSelectedPizzasToOrder(false); // Indicate the user is finalizing the order
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
        // clear previous selections
        selectedPizzas.clear();
        // loop through pizza adapter and collect selected pizzas
        for (PizzaObject pizzaObject : pizzaObjectList) {
            if (pizzaObject.isSelected()) {
                selectedPizzas.add(pizzaObject.getPizza());
            }
        }

        // Update AppContext with selected pizzas
        Order currentOrder = AppContext.getCurrentOrder();
        currentOrder.getPizzas().clear();
        currentOrder.getPizzas().addAll(selectedPizzas);
        Log.d("SelectPizzaActivity", "Updated Current Order: " + currentOrder);


        if (!isNavigatingBack) {
            // Navigate to CurrentOrderActivity
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        } else {
            // Navigate back to MainActivity (unconditionally)
            finish();
        }

        /**
        // update currentOrder
        AppContext appContext = (AppContext) getApplicationContext();
        currentOrder.getPizzas().clear();
        currentOrder.getPizzas().addAll(selectedPizzas);

        // log and navigate to CurrentOrder
        Log.d("SelectPizzaActivity", "Passing order: " + currentOrder.toString());
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);

         // order number
         SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putInt("currentOrderNumber", currentOrder.getOrderNumber() + 1);
         editor.apply();

         // pass current order
         Intent intent = new Intent(this, CurrentOrderActivity.class);
         intent.putExtra("currentOrder", currentOrder);
         startActivity(intent);
         */
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
