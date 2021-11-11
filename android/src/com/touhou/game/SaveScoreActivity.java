package com.touhou.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SaveScoreActivity extends Activity implements View.OnClickListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        textView = findViewById(R.id.txvScoreSum);
        textView.setText("Final score: "+ScoreData.point);
    }

    @Override
    public void onClick(View v) {

    }
}