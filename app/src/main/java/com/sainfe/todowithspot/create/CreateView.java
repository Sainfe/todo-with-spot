package com.sainfe.todowithspot.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.sainfe.todowithspot.MainActivity;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.databinding.ActivityCreateViewBinding;

public class CreateView extends AppCompatActivity {
    private ActivityCreateViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_view);

        binding.backwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent todayViewintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(todayViewintent);
            }
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
    }
}