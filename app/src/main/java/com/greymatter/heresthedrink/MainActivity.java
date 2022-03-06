package com.greymatter.heresthedrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greymatter.heresthedrink.adapter.CategoryAdapter;
import com.greymatter.heresthedrink.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView category_recycler;
    List<Category> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_recycler = findViewById(R.id.category_recycler);
        search = findViewById(R.id.search_bar_et);

        categoryAdapter = new CategoryAdapter(this,categoryList);
        category_recycler.setAdapter(categoryAdapter);

        getCategory();

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
    }

    private void searchFilter(String key) {
        List<Category> temp = new ArrayList();
        for(Category d: categoryList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(key)){
                temp.add(d);
            }
        }
        //update recyclerview
        categoryAdapter.updateList(temp);
    }

    private void getCategory() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("category");
        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    categoryList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Category category = snapshot.getValue(Category.class);
                        categoryList.add(category);
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}