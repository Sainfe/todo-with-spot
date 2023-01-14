package com.sainfe.todowithspot.viewmodel.create;

import  androidx.databinding.BaseObservable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sainfe.todowithspot.model.Todo;

public class CreateViewModel extends BaseObservable {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid = "uid-test1";

   public void createTodo(Todo todo) {
<<<<<<< HEAD
       System.out.println("createTodo");
        db.collection("users").document().collection("todos").document().set(todo);
=======
        db.collection("users").document(uid).collection("todos").document().set(todo);
>>>>>>> 7c43d33a2b2b74554d761867bb2bd79c1d325184
    }

    public void patchInfoTodo(DocumentSnapshot todo, Todo newTodo) {
        db.collection("users").document(uid).collection("todos").document(todo.getId()).set(newTodo);
    }
}
