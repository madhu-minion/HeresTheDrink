package com.greymatter.heresthedrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.greymatter.heresthedrink.adapter.DrinkAdapter;
import com.greymatter.heresthedrink.model.Category;
import com.greymatter.heresthedrink.model.Drink;

import java.util.ArrayList;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    RecyclerView product_recycler;
    List<Drink> drinkList = new ArrayList<>();
    DrinkAdapter drinkAdapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        product_recycler = findViewById(R.id.product_recycler);
        search = findViewById(R.id.search_bar_et);

        drinkAdapter = new DrinkAdapter(this, drinkList);
        product_recycler.setAdapter(drinkAdapter);

        getDrinks();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchFilter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void getDrinks() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("drinks");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    drinkList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Drink drink = snapshot.getValue(Drink.class);
                        drinkList.add(drink);
                    }
                    drinkAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void searchFilter(String key) {
        List<Drink> temp = new ArrayList();
        for(Drink d: drinkList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(key)){
                temp.add(d);
            }
        }
        //update recyclerview
        drinkAdapter.updateList(temp);
    }
}