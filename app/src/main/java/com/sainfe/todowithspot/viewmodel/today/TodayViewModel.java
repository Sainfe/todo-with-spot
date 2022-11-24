package com.sainfe.todowithspot.viewmodel.today;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sainfe.todowithspot.model.Todo;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodayViewModel extends BaseObservable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: sharedPreference 데이터 저장, 현재는 임시
    String uid = "uid-test1";

    MutableLiveData<ArrayList<Todo>> todoList = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<ArrayList<Todo>> getTodoList() {
        return todoList;
    }

    public void reload() {
        db.collection("users").document(uid).collection("todos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if(value == null) {
                    todoList = new MutableLiveData<>();
                } else {
                    ArrayList<Todo> temp = new ArrayList<>();
                    for(DocumentChange doc : value.getDocumentChanges()) {
                        if(doc.getType() == DocumentChange.Type.ADDED) {
                            Todo item = new Todo(doc.getDocument().get("content").toString(), null, false, false, null, 0, null);
                            temp.add(item);
                        }
                    }
                    // firestore 상에서 온라인으로 데이터를 바꾸면 데이터가 삭제되는 문제 존재
                    todoList.setValue(temp);
                }
            }
        });
    }

    void patchDoneTodo(DocumentSnapshot todo) {
        boolean isDone = Boolean.TRUE.equals(todo.getBoolean("isDone"));
        db.collection("users").document(uid).collection("todos").document(todo.getId()).update("isDone", !isDone);
    }

    void deleteTodo(DocumentSnapshot todo) {
        db.collection("users").document(uid).collection("todos").document(todo.getId()).delete();
    }
}
