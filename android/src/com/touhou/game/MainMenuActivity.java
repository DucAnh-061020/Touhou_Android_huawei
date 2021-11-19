package com.touhou.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity implements View.OnClickListener{
    Button button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        button = findViewById(R.id.Button1);
        button.setOnClickListener(this);
        button = findViewById(R.id.Button2);
        button.setOnClickListener(this);
        button = findViewById(R.id.Button3);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button1:
                intent = new Intent(MainMenuActivity.this,StageSelectActivity.class);
                MainMenuActivity.this.startActivity(intent);
                break;
            case R.id.Button2:
                intent = new Intent(MainMenuActivity.this,SettingActivity.class);
                MainMenuActivity.this.startActivity(intent);
                break;
            case R.id.Button3:
                finish();
                System.exit(0);
                break;
        }
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}