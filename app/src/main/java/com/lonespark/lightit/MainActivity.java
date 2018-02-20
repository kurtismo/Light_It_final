package com.lonespark.lightit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("I did it!");

        for(int x = 0; x < 1000; x++) {
            System.out.println("Billy sucks " + x + " dick(s)");
        }

    }
}
