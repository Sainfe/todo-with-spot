package com.sainfe.todowithspot.viewmodel.today;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TodayViewModel extends BaseObservable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: sharedPreference 데이터 저장
    String uid = "";

    void reload() {
        db.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // TODO: Error
                    return;
                }
                if (value != null && value.exists()) {
                    // TODO: Success && exist value
                } else {
                    // TODO: Success && none value
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
