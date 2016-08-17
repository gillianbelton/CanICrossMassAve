package com.apps.gillian.massavexing;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;

public class TimerActivity extends AppCompatActivity {

    private static final int STOPLIGHT_DURATION = 30;
    private static final long BASE_TIME = 1470729402L;

    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private Long currentTimeMillis;
    private int finalTime;
    private boolean firstTime;
    private boolean canWalk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tbTitle = (TextView) toolbar.findViewById(R.id.tb_title);
        tbTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Knile-Regular.ttf"));

        //set up timer
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        firstTime = true;
        setCurrentTime();
        resetTimer();
    }


    @Override
    protected void onDestroy() {
        currentTimeMillis = 0L;
        super.onDestroy();
    }

    private void setCurrentTime() {
        currentTimeMillis = System.currentTimeMillis();
        Log.d("CurrentTimeBugs", currentTimeMillis.toString());
        long currentTimeSecs = currentTimeMillis/1000;
        long timeDiff = currentTimeSecs - BASE_TIME;
        finalTime = (int) (timeDiff % STOPLIGHT_DURATION);
        canWalk = (((timeDiff)/STOPLIGHT_DURATION) % 2) == 0;
        setWalkingSign();
    }

    private void setWalkingSign() {
        ImageView canWalkSymbol = (ImageView) findViewById(R.id.iv_walk_symbol);
        if (!canWalk) {
            canWalkSymbol.setImageDrawable(getResources().getDrawable(R.drawable.cross_walk_false));
            canWalk = true;
        }
        else {
            canWalkSymbol.setImageDrawable(getResources().getDrawable(R.drawable.cross_walk_true));
            canWalk = false;
        }
    }

    public void resetTimer(){
        if (firstTime) {
            countDownTimer = new CountDownTimer(finalTime * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(" " + millisUntilFinished / 1000 + " ");
                }

                public void onFinish() {
                    resetTimer();
                }
            };
            countDownTimer.start();
            firstTime = false;
        }
        else {
            countDownTimer = new CountDownTimer(STOPLIGHT_DURATION * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvTimer.setText(" " + millisUntilFinished / 1000 + " ");
                }

                public void onFinish() {
                    resetTimer();
                }
            };
            setWalkingSign();
            countDownTimer.start();
        }
    }
}
