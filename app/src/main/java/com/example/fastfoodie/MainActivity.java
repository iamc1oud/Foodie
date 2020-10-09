package com.example.fastfoodie;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodie.authentication.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
  private TextView mSignUpText;
  private TextInputLayout emailLayout;
  private TextInputLayout passwordLayout;
  private GoogleSignIn mGoogleSignIn;

  // Buttons
  private MaterialButton btn;

  // Google Sign In request code
  private static final int RC_SIGN_IN = 1;

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_fullscreen);
    mGoogleSignIn = new GoogleSignIn(MainActivity.this);
    // Get ids of view
    getIds();

    // Set google button style
    setStyles();

    // Navigate to sign up activity
    mSignUpText.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(emailLayout, "trans_email");
            pairs[1] = new Pair<View, String>(passwordLayout, "trans_password");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
              ActivityOptions options =
                  ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
              startActivity(intent, options.toBundle());
            }
          }
        });

    // Google sign in button
    btn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            GoogleSignInClient client = mGoogleSignIn.GoogleSignIn();
            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
          }
        });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      Task<GoogleSignInAccount> task =
          com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
      boolean result = mGoogleSignIn.handleSignInResult(task, MainActivity.this);
      handleSignInResult(result);
    }
  }

  private void handleSignInResult(boolean result) {
    if(result){
      setupLocationActivity();
    }
    else {
      Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_LONG).show();
    }
  }

  private void setupLocationActivity(){
    Intent intent = new Intent(MainActivity.this, LocationSignUpActivity.class);
    startActivity(intent);
  }


  void setStyles() {
    btn.setBackgroundColor(Color.parseColor("#ffffff"));
    TextView app_name = findViewById(R.id.app_name);
    Shader textShader =
        new LinearGradient(
            0,
            0,
            0,
            app_name.getTextSize(),
            new int[] {
              Color.parseColor("#ffd52a7c"), Color.parseColor("#ffcd4d7c"),
            },
            null,
            Shader.TileMode.CLAMP);

    app_name.getPaint().setShader(textShader);
  }

  void getIds() {
    btn = findViewById(R.id.google_sign_in_btn);
    emailLayout = findViewById(R.id.email_id_layout_field);
    passwordLayout = findViewById(R.id.password_layout_field);
    mSignUpText = (TextView) findViewById(R.id.nav_sign_up);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("activity_status", "onDestroy() called");
    mGoogleSignIn.signOut();
  }
}
