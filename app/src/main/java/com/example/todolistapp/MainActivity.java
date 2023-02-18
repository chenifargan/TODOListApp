package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.AnnotationFormatError;

public class MainActivity extends AppCompatActivity {
private static final int SPLASH =3300;
    Animation topAnimation, bottomAnimation;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imageView = findViewById(R.id.noteImage);
        textView = findViewById(R.id.text);
        imageView.setAnimation(topAnimation);
        textView.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,loginActivity.class));
                finish();
            }
        },SPLASH);

    }
    //חד פעמי
    //רב פעמי
    ///מנוי
}