package com.sainfe.todowithspot.viewmodel.create;

import androidx.databinding.BaseObservable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sainfe.todowithspot.model.Todo;

public class CreateViewModel extends BaseObservable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: sharedPreference 데이터 저장
    String uid = "";

    void createTodo(Todo todo) {
        db.collection("users").document().collection("todos").document().set(todo);
    }

    void patchInfoTodo(DocumentSnapshot todo, Todo newTodo) {
        db.collection("users").document(uid).collection("todos").document(todo.getId()).set(newTodo);
    }
}