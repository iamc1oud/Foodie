package com.example.fastfoodie;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        setStyles();

    }

    void setStyles(){
        MaterialButton btn = findViewById(R.id.google_sign_in_btn);
        btn.setBackgroundColor(Color.parseColor("#ffffff"));
        TextView app_name = findViewById(R.id.app_name);
        Shader textShader = new LinearGradient(0, 0, 0 , app_name.getTextSize(),
                new int[]{
                        Color.parseColor("#ffd52a7c"),
                        Color.parseColor("#ffcd4d7c"),
                }, null, Shader.TileMode.CLAMP);

        app_name.getPaint().setShader(textShader);
    }
}