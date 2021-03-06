package com.touhou.game.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.touhou.game.R;

public class StageSelectActivity extends Activity implements View.OnClickListener{
    Button button;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_select);
        button = findViewById(R.id.ReturnStage);
        button.setOnClickListener(this);

        button = findViewById(R.id.stage1);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ReturnStage:
                this.finish();
                break;
            case R.id.stage1:
                intent = new Intent(StageSelectActivity.this,AndroidLauncher.class);
                StageSelectActivity.this.startActivity(intent);
                this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
