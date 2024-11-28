package com.example.project5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import RUPizza.*;

public class SelectPizzaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<PizzaObject> pizzaObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_pizza_row);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize pizza list and adapter
        pizzaObjectList = new ArrayList<>();
        populatePizzaList();
        pizzaAdapter = new PizzaAdapter(this, pizzaObjectList);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(pizzaAdapter);
    }

    private void populatePizzaList() {
        PizzaFactory ChicagoPizzaFactory = new ChicagoPizza();
        PizzaFactory NYPizzaFactory = new NYPizza();
        //BBQ Chicken Pizzas
        Pizza BBQChicagoPizza = ChicagoPizzaFactory.createBBQChicken(Size.SMALL);
        Pizza BBQNYPizza = NYPizzaFactory.createBBQChicken(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style BBQ Pizza", BBQChicagoPizza, R.drawable.bbq_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style BBQ Pizza",BBQNYPizza, R.drawable.ny_bbq));
        //Deluxe Pizzas
        Pizza DeluxeChicagoPizza = ChicagoPizzaFactory.createDeluxe(Size.SMALL);
        Pizza DeluxeNYPizza = NYPizzaFactory.createDeluxe(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Deluxe Pizza",DeluxeChicagoPizza, R.drawable.deluxe_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style Deluxe Pizza",DeluxeNYPizza, R.drawable.ny_deluxe));
        //Meatzza Pizzas
        Pizza MeatzzaChicagoPizza = ChicagoPizzaFactory.createMeatzza(Size.SMALL);
        Pizza MeatzzaNYPizza = NYPizzaFactory.createMeatzza(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Meatzza Pizza",MeatzzaChicagoPizza, R.drawable.meatzza));
        pizzaObjectList.add(new PizzaObject("NY Style Meatzza Pizza",MeatzzaNYPizza, R.drawable.ny_meatzza));
        //Build Your Own Pizzas
        Pizza BYOChicagoPizza = ChicagoPizzaFactory.createBuildYourOwn(Size.SMALL);
        Pizza BYONYPizza = NYPizzaFactory.createBuildYourOwn(Size.SMALL);
        pizzaObjectList.add(new PizzaObject("Chicago Style Build Your Own Pizza",BYOChicagoPizza, R.drawable.build_pizza));
        pizzaObjectList.add(new PizzaObject("NY Style Build Your Own Pizza",BYONYPizza, R.drawable.ny_build));
    }
}
