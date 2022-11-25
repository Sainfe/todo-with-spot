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

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.sainfe.todowithspot.databinding.ActivityCreateBinding;
import com.sainfe.todowithspot.model.Todo;
import com.sainfe.todowithspot.view.today.OnSwipeTouchListener;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.view.today.TodayActivity;
import com.sainfe.todowithspot.viewmodel.create.CreateViewModel;

import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

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

        View createListButton = findViewById(R.id.create_todo_list_button); // 완료버튼

        String contentField = binding.content.getText().toString();
        int year = datePickerDialog.getDatePicker().getYear();
        int month = datePickerDialog.getDatePicker().getMonth();
        int date = datePickerDialog.getDatePicker().getDayOfMonth();

        String isAlarm = binding.toggle.getText().toString();
        System.out.println(isAlarm);
        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo(contentField,  new Timestamp(new Date(year, month, date)), false, Boolean.parseBoolean(isAlarm), new GeoPoint(20L, 20L), 0, Timestamp.now());
                binding.getViewModel().createTodo(todo);
                System.out.println("onClick");
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
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            try {
                Time time = new Time(hourOfDay, minute, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}