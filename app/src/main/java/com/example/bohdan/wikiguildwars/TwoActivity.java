package com.example.bohdan.wikiguildwars;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class TwoActivity extends AppCompatActivity implements Serializable {

    TextView name;
    TextView type;
    TextView description;
    TextView id;
    ImageView imageView;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        name = findViewById(R.id.textView58);
        type = findViewById(R.id.textView69);
        description = findViewById(R.id.textView25);
        imageView = findViewById(R.id.imageView2);
        id = findViewById(R.id.textView5);

        model = (Model) getIntent().getSerializableExtra("item");

        name.setText("Name: "+model.getName());
        type.setText("Type: "+model.getType());
        description.setText("Description: "+model.getDescription());
        id.setText("Id: " + model.getId());
        Picasso.get().load(model.getIcon()).into(imageView);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

    }

    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();  // цей метод працює
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
