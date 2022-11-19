package com.sainfe.todowithspot.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

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

        ToggleButton toggle = findViewById(R.id.toggle);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton leaveButton = findViewById(R.id.leave_button);
        RadioButton arriveButton = findViewById(R.id.arrive_button);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    leaveButton.setEnabled(true);
                    arriveButton.setEnabled(true);
                } else {
                    leaveButton.setEnabled(false);
                    arriveButton.setEnabled(false);
                }
            }
        });
    }
}