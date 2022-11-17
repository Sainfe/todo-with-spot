package com.sainfe.todowithspot.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sainfe.todowithspot.MainActivity;
import com.sainfe.todowithspot.R;

public class CreateView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);

        View backwardButton = findViewById(R.id.backward_button);
        backwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent todayViewintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(todayViewintent);
            }
        });
    }
}