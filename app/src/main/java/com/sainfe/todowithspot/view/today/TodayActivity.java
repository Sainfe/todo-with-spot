package com.sainfe.todowithspot.view.today;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.Timestamp;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.databinding.ActivityTodayBinding;
import com.sainfe.todowithspot.model.Todo;
import com.sainfe.todowithspot.view.all.AllActivity;
import com.sainfe.todowithspot.view.create.CreateActivity;
import com.sainfe.todowithspot.viewmodel.today.TodayViewModel;

import java.util.ArrayList;

public class TodayActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    ActivityTodayBinding binding;

    @SuppressLint("ClickableViewAccessibility") // performClick()을 override 하라는 경고 음소거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_today);
        binding.setViewModel(new TodayViewModel());
        binding.executePendingBindings();

        bindList();

        binding.createTodoListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent createViewIntent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(createViewIntent);
            }
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
        final ArrayList<Todo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Todo("text " + i, new Timestamp(10L, 10), false, false, null, 0, null));
            // TODO : 데이테베이스에서 전체 조회로 구현 예정
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        binding.recycler1.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        TodayAdapter adapter = new TodayAdapter(list);
        binding.recycler1.setAdapter(adapter);

        adapter.setOnItemLongClickListener(new TodayAdapter.OnItemLongClickEventListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                final Todo item = list.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(TodayActivity.this, R.style.AppCompatAlertDialog);
                builder.setTitle(item.getContent()); // TODO : 뷰에 저장된 데이터 조회
                builder.setMessage("TEST_MSG");
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
            }
        });
    }
}