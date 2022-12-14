package com.sainfe.todowithspot.viewmodel.all;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.sainfe.todowithspot.model.Todo;

import java.util.ArrayList;
import java.util.Objects;

public class AllViewModel extends BaseObservable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid = "uid-test1";

    MutableLiveData<ArrayList<Todo>> todoList = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<ArrayList<Todo>> getTodoList() {
        return todoList;
    }

    public void reload() {
        db.collection("users").document(uid).collection("todos").orderBy("date").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value == null) {
                    todoList = new MutableLiveData<>();
                } else {
                    ArrayList<Todo> temp = new ArrayList<>();
                    Boolean check = false;
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            check = true;
                            Todo item = new Todo(doc.getDocument().get("content").toString(), (Timestamp) doc.getDocument().get("date"), (Boolean) doc.getDocument().get("done"), (Boolean) doc.getDocument().get("alarm"), (GeoPoint) doc.getDocument().get("place"), Integer.parseInt(Objects.requireNonNull(doc.getDocument().get("placeType")).toString()), null);
                            temp.add(item);
                        } else {
                            check = false;
                        }
                    }
                    if(check) todoList.setValue(temp);
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
