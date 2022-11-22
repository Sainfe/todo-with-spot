package com.sainfe.todowithspot.view.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.sainfe.todowithspot.databinding.ActivityCreateBinding;
import com.sainfe.todowithspot.view.today.TodayActivity;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.viewmodel.create.CreateViewModel;
import com.sainfe.todowithspot.viewmodel.today.TodayViewModel;

public class CreateActivity extends AppCompatActivity {

    ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        binding.setViewmodel(new CreateViewModel());
        binding.executePendingBindings();

        View backwardButton = findViewById(R.id.backward_button);
        backwardButton.setOnClickListener(view -> {
            finish();
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