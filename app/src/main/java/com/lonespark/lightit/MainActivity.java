package com.lonespark.lightit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-3935766831192873~7787540008");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        ImageView playButton = findViewById(R.id.playIcon);
        playButton.setClickable(true);

        ImageView star = findViewById(R.id.starLogo);
        star.setClickable(true);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    if (rand.nextInt(5) == 3) {
                        mInterstitialAd.show();
                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdClosed() {
                                Intent intent = new Intent(MainActivity.this, adScreen.class);
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, adScreen.class);
                        startActivity(intent);
                    }
                }
                else {
                    Intent intent = new Intent(MainActivity.this, adScreen.class);
                    startActivity(intent);
                }
            }
        });



        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                   if (rand.nextInt(5) == 3) {
                        mInterstitialAd.show();
                        mInterstitialAd.setAdListener(new AdListener() {
                            public void onAdClosed() {
                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                       Intent intent = new Intent(MainActivity.this, GameActivity.class);
                       startActivity(intent);
                   }
                }
                else {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            }
        });

        SharedPreferences prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("ScoreKey", 0); //0 is the default value

        TextView txtScore = findViewById(R.id.txtScore);
        txtScore.setText("Score: "+score);
      }
}
