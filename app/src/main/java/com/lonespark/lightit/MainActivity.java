package com.lonespark.lightit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView playButton = findViewById(R.id.playIcon);
        playButton.setClickable(true);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = this.getSharedPreferences("highScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("ScoreKey", 0); //0 is the default value

        TextView txtScore = findViewById(R.id.txtScore);
        txtScore.setText("Score: "+score);
      }
}
