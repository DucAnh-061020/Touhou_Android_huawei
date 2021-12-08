package com.touhou.game.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.touhou.game.R;
import com.touhou.game.staticData.AppSetting;

public class SettingActivity extends Activity implements View.OnClickListener{

    Button button;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        button = findViewById(R.id.ReturnMenu);
        button.setOnClickListener(this);

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnClickListener(this);
        aSwitch = findViewById(R.id.switch2);
        aSwitch.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch1:
                Switch s = findViewById(R.id.switch1);
                AppSetting.BGM = s.isChecked();
                break;
            case R.id.switch2:
                s = findViewById(R.id.switch2);
                AppSetting.SFX = s.isChecked();
                break;
            case R.id.ReturnMenu:
                this.finish();
                break;
        }
    }
}
