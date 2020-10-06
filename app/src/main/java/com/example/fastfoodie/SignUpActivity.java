package com.example.fastfoodie;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Pair;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    private TextView mSignInText;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getIds();

        // Navigate to Sign In activity
        mSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(emailLayout, "trans_email");
                pairs[1] = new Pair<View,String>(passwordLayout, "trans_password");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

    }

    void getIds(){
        mSignInText = (TextView) findViewById(R.id.nav_sign_in);
        emailLayout = findViewById(R.id.email_id_layout_field);
        passwordLayout = findViewById(R.id.password_layout_field);
    }
}