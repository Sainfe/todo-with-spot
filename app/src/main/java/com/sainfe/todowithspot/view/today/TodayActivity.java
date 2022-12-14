package com.sainfe.todowithspot.view.today;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.databinding.ActivityTodayBinding;
import com.sainfe.todowithspot.model.Todo;
import com.sainfe.todowithspot.view.all.AllActivity;
import com.sainfe.todowithspot.view.create.CreateActivity;
import com.sainfe.todowithspot.viewmodel.today.TodayViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodayActivity extends AppCompatActivity {

    ActivityTodayBinding binding;
    TodayViewModel viewModel = new TodayViewModel();
    AlertDialog alertDialog;

    ArrayList<Todo> todoList;

    @SuppressLint("ClickableViewAccessibility") // performClick()을 override 하라는 경고 음소거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_today);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        viewModel.reload();
        todoList = viewModel.getTodoList().getValue();
        bindList();
        viewModel.getTodoList().observe(this, new Observer<ArrayList<Todo>>() {
            @Override
            public void onChanged(ArrayList<Todo> todos) {
                todoList = todos;
                bindList();
            }
        });

        // createButton 클릭 시 , create view 전환
        binding.createTodoListButton.setOnClickListener(view -> {
            Intent createIntent = new Intent(getApplicationContext(), CreateActivity.class);
            startActivity(createIntent);
        });

        binding.layoutToday.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Intent swipeLeftIntent = new Intent(getApplicationContext(), AllActivity.class);
                startActivity(swipeLeftIntent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Intent swipeRightIntent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(swipeRightIntent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    public void bindList() {
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        TodoAdapter adapter = new TodoAdapter(todoList);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemLongClickListener((view, position) -> {
            final Todo item = todoList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(TodayActivity.this, R.style.AppCompatAlertDialog);
            builder.setTitle(item.getContent());
            builder.setMessage("Time : " + getTimeSimpleFormat(item.getTime()) + "\n" + "Place : " + getPlaceToAddress(item.getPlace()));
            builder.setPositiveButton("확인", null);
            builder.setNegativeButton("수정", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //  TODO : EDIT 화면으로 넘어가기
                    Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                    startActivity(intent);
                }
            });

            alertDialog = builder.create();
            alertDialog.show();
        });
    }

    public String getTimeSimpleFormat(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
        return sdf.format(timestamp.toDate());
    }

    public String getPlaceToAddress(GeoPoint geoPoint) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);

        List<Address> address;
        try {
            address = geocoder.getFromLocation(geoPoint.getLatitude(), geoPoint.getLongitude(), 1);
        } catch (IOException e) {
            return String.valueOf(R.string.geocoder_error);
        }
        if (address == null || address.size() == 0) {
            return String.valueOf(R.string.no_address);
        } else {
            return address.get(0).getAddressLine(0);
        }
    }
}