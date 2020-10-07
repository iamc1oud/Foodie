package com.example.fastfoodie;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextView mSignUpText;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;

    // Buttons
    private MaterialButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        // Get ids of view
        getIds();

        // Set google button style
        setStyles();

        // Navigate to sign up activity
        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(emailLayout, "trans_email");
                pairs[1] = new Pair<View,String>(passwordLayout, "trans_password");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    void setStyles(){
        btn.setBackgroundColor(Color.parseColor("#ffffff"));
        TextView app_name = findViewById(R.id.app_name);
        Shader textShader = new LinearGradient(0, 0, 0 , app_name.getTextSize(),
                new int[]{
                        Color.parseColor("#ffd52a7c"),
                        Color.parseColor("#ffcd4d7c"),
                }, null, Shader.TileMode.CLAMP);

        app_name.getPaint().setShader(textShader);
    }

    void getIds(){
        btn = findViewById(R.id.google_sign_in_btn);
        emailLayout = findViewById(R.id.email_id_layout_field);
        passwordLayout = findViewById(R.id.password_layout_field);
        mSignUpText = (TextView)findViewById(R.id.nav_sign_up);
    }
}