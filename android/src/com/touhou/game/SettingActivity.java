package com.touhou.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity implements View.OnClickListener{
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        button = findViewById(R.id.ReturnMenu);
        button.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ReturnMenu:
                this.finish();
                break;
        }
    }
}
