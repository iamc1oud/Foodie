package com.example.fastfoodie.database;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface SignUpRepository {

    /**
     * Register the user and store information to Firestore
     */
    void registerUser(@NonNull GoogleSignInAccount account);


    /**
     * Store the location and other information to firestore
     */

    void saveLocationAtSignUp(String label, String address, String note);

  /**
   * Checking whether the user already exists or not
   * @param uid The UID of user on signing with Google*/
  boolean checkIfUserExists(String uid);
}
