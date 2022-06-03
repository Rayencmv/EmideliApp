package com.martinez.emideliapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags (WindowManager.LayoutParams
                .FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Animation animacion1 = AnimationUtils.loadAnimation
                ( this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation
                ( this, R.anim.desplazamiento_arriba);

        TextView ByTextView = findViewById(R.id.ByTextView);
        TextView NameTextView = findViewById( R.id.NameTextView);
        ImageView logoImageView = findViewById(R.id.LogoImageView);

        ByTextView.setAnimation(animacion2);
        NameTextView.setAnimation(animacion2);
        logoImageView.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);



    }

}