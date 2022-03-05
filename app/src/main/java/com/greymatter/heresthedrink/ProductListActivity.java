package com.greymatter.heresthedrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.greymatter.heresthedrink.adapter.CategoryAdapter;
import com.greymatter.heresthedrink.adapter.DrinkAdapter;
import com.greymatter.heresthedrink.model.Category;
import com.greymatter.heresthedrink.model.Drink;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView product_recycler;
    List<Drink> drinkList = new ArrayList<>();
    DrinkAdapter drinkAdapter;
    Category category;
    TextView category_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        product_recycler = findViewById(R.id.product_recycler);
        category_name = findViewById(R.id.category_name);

        category = new Gson().fromJson(getIntent().getStringExtra("category"),Category.class);

        category_name.setText(category.getName());

        drinkAdapter = new DrinkAdapter(this, drinkList);
        product_recycler.setAdapter(drinkAdapter);

        getDrinks();

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

        ref.orderByChild("category_id").equalTo(category.getId()).addValueEventListener(new ValueEventListener() {

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
}