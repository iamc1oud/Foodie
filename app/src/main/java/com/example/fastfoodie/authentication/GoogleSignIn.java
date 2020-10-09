package com.example.fastfoodie.authentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import android.content.Intent;
import com.example.fastfoodie.LocationSignUpActivity;
import com.example.fastfoodie.database.Database;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleSignIn {
    private static GoogleSignIn INSTANCE;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private Activity mainActivity;

    /**
     * @param activity The instance of MainActivity
     */
    public GoogleSignIn(@NonNull Activity activity) {
        this.mainActivity = activity;
    }

    /**
     * @return GoogleSignClient object
     */
    public GoogleSignInClient GoogleSignIn() {
        // If you need to request additional scopes to access Google APIs, specify them with requestScopes
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(mainActivity, gso);
        return mGoogleSignInClient;
    }

    /**
     * @param completedTask The asynchronous task to handle the result authentication
     */
    @SuppressLint("LogNotTimber")
    public boolean handleSignInResult(Task<GoogleSignInAccount> completedTask, Activity activity) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            assert account != null;

            // Registering the user after successful google sign
            Database _db = Database.getDatabaseInstance();
            _db.registerUser(account);
            return true;
        }
        catch (ApiException e){
            Log.w("STATUS_LOGIN_GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            return false;
        }
    }

    /**
     * Sign out the authenticated user
     */
    public void signOut(){
        mGoogleSignInClient.signOut();
    }

}
