package com.sainfe.todowithspot.view.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.sainfe.todowithspot.databinding.ActivityCreateBinding;
import com.sainfe.todowithspot.view.today.OnSwipeTouchListener;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.view.today.TodayActivity;
import com.sainfe.todowithspot.viewmodel.create.CreateViewModel;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    ActivityCreateBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        binding.setViewModel(new CreateViewModel());
        binding.executePendingBindings();

        binding.backwardButton.setOnClickListener(view -> {
            finish();
        });

        binding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.leaveButton.setEnabled(true);
                    binding.arriveButton.setEnabled(true);
                } else {
                    binding.leaveButton.setEnabled(false);
                    binding.arriveButton.setEnabled(false);
                }
            }
        });

//        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timePickerListener, 15, 24, false);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding.datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        binding.createLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Intent swipeLeftIntent = new Intent(getApplicationContext(), TodayActivity.class);
                startActivity(swipeLeftIntent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

        }
    };
}