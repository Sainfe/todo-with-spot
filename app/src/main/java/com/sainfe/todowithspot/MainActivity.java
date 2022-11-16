package com.sainfe.todowithspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sainfe.todowithspot.create.CreateView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility") // performClick()을 override 하라는 경고 음소거
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Item> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(new Item("text " + i));
            // TODO : 데이테베이스에서 전체 조회로 구현 예정
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(list);
        recyclerView.setAdapter(adapter);

        //createButton 클릭 시 , create view 전환
        View createButton = findViewById(R.id.create_todo_list_button);
        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent createViewIntent = new Intent(getApplicationContext(), CreateView.class);
                startActivity(createViewIntent);
            }
        });

        LinearLayout main_layout = findViewById(R.id.main_layout);
        main_layout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                // All 화면으로 넘어가는 Intent 추가
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Intent swipeRightIntent = new Intent(getApplicationContext(), CreateView.class);
                startActivity(swipeRightIntent);
            }
        });
    }
}