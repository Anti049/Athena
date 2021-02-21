package com.anti049.athena;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.anti049.athena.auth.LoginChoiceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int START_TIME = 1000;
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Continue on to Main Activity
            final Context context = this;
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endSplashscreen();
                }
            }, START_TIME);
        } else {
            // Load Login Activity
            final Activity context = this;
            final Handler handler = new Handler(Looper.getMainLooper());
            final ImageView appLogo = findViewById(R.id.splashImage);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, LoginChoiceActivity.class);
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(context, appLogo, "splashLogo");
                    startActivity(intent);
                }
            }, SPLASH_TIME);
        }
    }

    private void endSplashscreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
    }
}