package com.touhou.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.touhou.game.R;

public class MainMenuActivity extends Activity implements View.OnClickListener{
    Button button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        HwAds.init(this);

        button = findViewById(R.id.Button1);
        button.setOnClickListener(this);
        button = findViewById(R.id.Button2);
        button.setOnClickListener(this);
        button = findViewById(R.id.Button3);
        button.setOnClickListener(this);
        button = findViewById(R.id.Button4);
        button.setOnClickListener(this);
        BannerView bannerView = findViewById(R.id.hw_banner_view);
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);
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
                intent = new Intent(MainMenuActivity.this,LeaderboardActivity.class);
                MainMenuActivity.this.startActivity(intent);
                break;
            case R.id.Button4:
                finish();
                System.exit(-1);
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