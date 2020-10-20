package com.example.fastfoodie.database;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Firebase firestore initializer */
public class Database implements SignUpRepository {
  FirebaseFirestore _db;
  private String UID;
  private static Database INSTANCE = null;

  // Constructor
  private Database() {
    this._db = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();
    this._db.setFirestoreSettings(settings);
  }

  // Singleton object of DB
  public static Database getDatabaseInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Database();
      return INSTANCE;
    }
    return INSTANCE;
  }

  @SuppressLint("LogNotTimber")
  @Override
  public void registerUser(@NonNull GoogleSignInAccount account) {
    Log.d("username", account.getDisplayName());
    Log.d("uid", account.getId());
    Log.d("photo_url", String.valueOf(account.getPhotoUrl()));

    // During first time sign in temporary save uid
    UID = account.getId();

    // Mapping data
    Map<String, Object> user = new HashMap<>();
    user.put("username", Objects.requireNonNull(account.getDisplayName()));
    user.put("email", Objects.requireNonNull(account.getEmail()));
    user.put("uid", Objects.requireNonNull(account.getId()));
    user.put("photo_url", Objects.requireNonNull(account.getPhotoUrl().toString()));

    this._db
        .collection("users")
        .add(user)
        .addOnSuccessListener(
            new OnSuccessListener<DocumentReference>() {
              @Override
              public void onSuccess(DocumentReference documentReference) {
                Log.d("Registered", "DocumentSnapshot added with ID: " + documentReference.getId());
              }
            })
        .addOnFailureListener(
            new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                Log.w("Error", "Error adding document");
              }
            });
  }

  @Override
  public void saveLocationAtSignUp(String labelText, String address, String note) {
    Map<String, Object> data = new HashMap<>();
    Map<String, Object> addressAndLabelData = new HashMap<>();

    addressAndLabelData.put("address", address);
    addressAndLabelData.put("label", labelText);

    data.put("address", Arrays.asList(addressAndLabelData) );
    data.put("note", note);

    Task<QuerySnapshot> queryResult =  this._db.collection("users")
            .whereEqualTo("uid", UID)
            .get();

    queryResult.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if(task.isSuccessful()){
          DocumentSnapshot snapshot = task.getResult().getDocuments().get(0);
          DocumentReference reference = snapshot.getReference();
          reference
                  .update(data)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      Log.d("OnSuccess", "Address successfully updated!");
                    }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      Log.w("OnError", "Error updating document", e);
                    }
                  });
        }
      }
    });
  }

  @Override
  public boolean checkIfUserExists(String uid) {
    Task<QuerySnapshot> queryResult =  this._db.collection("users")
            .whereEqualTo("uid", uid)
            .get();
    return false;
  }
}
