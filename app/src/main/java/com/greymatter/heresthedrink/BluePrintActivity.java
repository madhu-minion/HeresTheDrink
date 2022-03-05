package com.greymatter.heresthedrink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BluePrintActivity extends AppCompatActivity {

    ImageView blueprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_print);

        blueprint = findViewById(R.id.blueprint);

        Picasso.get().load(getIntent().getStringExtra("url")).into(blueprint);
    }
}