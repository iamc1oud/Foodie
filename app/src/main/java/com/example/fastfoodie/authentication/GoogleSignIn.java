package com.example.fastfoodie.authentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleSignIn {
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private Activity mainActivity;

    public GoogleSignIn(@NonNull Activity activity) {
        this.mainActivity = activity;
    }

    public GoogleSignInClient GoogleSignIn() {
        // If you need to request additional scopes to access Google APIs, specify them with requestScopes
        gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(mainActivity, gso);
        return mGoogleSignInClient;
    }

    @SuppressLint("LogNotTimber")
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            assert account != null;
            Log.d("Username", account.getDisplayName());
        }
        catch (ApiException e){
            Log.w("STATUS_LOGIN_GOOGLE", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void signOut(){
        mGoogleSignInClient.signOut();
    }

}
