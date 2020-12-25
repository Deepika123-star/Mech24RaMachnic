package com.smartwebarts.mech24ra.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.utils.AccessToken;

import java.util.Objects;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2500l;
    private static final String TAG = Splash.class.getSimpleName();
    private ImageView mAppLogoView;
    private TextView mAppNameView;
    private Handler mDelayHandler = new Handler();
    private SharedPreferences prefs;
    String user;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            AppSharedPreferences preferences = new AppSharedPreferences();

            user = preferences.getLoginDetails(Splash.this);
            if (user == null || user.isEmpty()) {
                Intent intent = new Intent(Splash.this, LogInActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        new AccessToken().setAccess_token(Splash.this,token);
                        Log.e(TAG, token);
                    }
                });

        init();

        mDelayHandler.postDelayed(runnable, SPLASH_DELAY);

        startAnimation();
    }

    private void init() {
        mAppLogoView = findViewById(R.id.iv_app_icon);
//        prefs = getSharedPreferences(ApplicationConstants.SHARED_PREF_NAME, MODE_PRIVATE);

    }

    private void startAnimation() {

        mAppLogoView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mAppLogoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        AnimatorSet mAnimatorSet = new AnimatorSet();
                        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(mAppLogoView, "alpha", 0, 1, 1, 1),
                                ObjectAnimator.ofFloat(mAppLogoView, "scaleX", 0.3f, 1.05f, 0.9f, 1),
                                ObjectAnimator.ofFloat(mAppLogoView, "scaleY", 0.3f, 1.05f, 0.9f, 1));
                        mAnimatorSet.setDuration(1500);
                        mAnimatorSet.start();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(runnable);
    }
}

