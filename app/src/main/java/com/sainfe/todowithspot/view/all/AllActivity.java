package com.sainfe.todowithspot.view.all;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.databinding.ActivityAllBinding;
import com.sainfe.todowithspot.viewmodel.all.AllViewModel;
import com.sainfe.todowithspot.viewmodel.today.TodayViewModel;

public class AllActivity extends AppCompatActivity {

    ActivityAllBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all);
        binding.setViewmodel(new AllViewModel());
        binding.executePendingBindings();
    }
}