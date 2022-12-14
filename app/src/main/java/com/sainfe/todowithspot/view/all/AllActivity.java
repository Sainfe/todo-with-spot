package com.sainfe.todowithspot.view.all;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.databinding.ActivityAllBinding;
import com.sainfe.todowithspot.model.Todo;
import com.sainfe.todowithspot.view.create.CreateActivity;
import com.sainfe.todowithspot.view.today.OnSwipeTouchListener;
import com.sainfe.todowithspot.view.today.TodayActivity;
import com.sainfe.todowithspot.view.today.TodoAdapter;
import com.sainfe.todowithspot.viewmodel.all.AllViewModel;

import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {

    ActivityAllBinding binding;
    AllViewModel viewModel = new AllViewModel();
    ArrayList<Todo> todoList;
    AlertDialog alertDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.layoutAll.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Intent swipeRightIntent = new Intent(getApplicationContext(), TodayActivity.class);
                startActivity(swipeRightIntent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

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
    }

    public void bindList() {
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        AllAdapter adapter = new AllAdapter(todoList);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemLongClickListener((view, position) -> {
            final Todo item = todoList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(AllActivity.this, R.style.AppCompatAlertDialog);
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
        });
    }
}