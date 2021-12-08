package com.touhou.game.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.touhou.game.R;
import com.touhou.game.ScoreAdapter;
import com.touhou.game.SqlLite.ScoreModel;
import com.touhou.game.staticData.ScoreValue;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends Activity implements View.OnClickListener {
    private Button button;
    private List<ScoreValue> scoreLst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        button = findViewById(R.id.btn_return_leaderboard);
        button.setOnClickListener(this);
        scoreLst = new ArrayList<>();
        getListData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_return_leaderboard:
                this.finish();
                break;
        }
    }

    public void getListData() {
        //lay ra duoc toan bo du lieu trong database
        ListView listView = findViewById(R.id.lv_leaderboard);
        ScoreModel scoreModel = new ScoreModel(getApplicationContext());
        scoreLst = scoreModel.getAllScore();
        ScoreAdapter adapter = new ScoreAdapter(this,R.layout.recycle_leaderboard,scoreLst);
        listView.setAdapter(adapter);
    }
}
