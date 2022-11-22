package com.sainfe.todowithspot.create;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.sainfe.todowithspot.MainActivity;
import com.sainfe.todowithspot.OnSwipeTouchListener;
import com.sainfe.todowithspot.R;

public class CreateView extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);

        View backwardButton = findViewById(R.id.backward_button);
        backwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent todayViewIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(todayViewIntent);
            }
        });

        LinearLayout create_layout = findViewById(R.id.create_layout);
        create_layout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Intent swipeLeftIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(swipeLeftIntent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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