package com.Widget.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button pos, neg;
    TextView count;
    int n = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pos = findViewById(R.id.pos);
        neg = findViewById(R.id.neg);
        count = findViewById(R.id.count);
        count.setText(String.valueOf(n));

        pos.setOnClickListener(view -> {
            n++;
            count.setText(String.valueOf(n));
        });

        neg.setOnClickListener(view -> {
            n--;
            count.setText(String.valueOf(n));
        });
    }
}