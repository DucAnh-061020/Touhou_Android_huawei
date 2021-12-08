package com.touhou.game.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.touhou.game.R;
import com.touhou.game.SqlLite.ScoreModel;
import com.touhou.game.staticData.ScoreData;
import com.touhou.game.staticData.ScoreValue;

public class SaveScoreActivity extends Activity implements View.OnClickListener {
    TextView textView;
    Button button;
    private ScoreModel scoreModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        textView = findViewById(R.id.txvScoreSum);
        textView.setText("Final score: "+ ScoreData.point);
        button = findViewById(R.id.btnReturnSave);
        button.setOnClickListener(this);
        button = findViewById(R.id.btnSaveScore);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReturnSave:
                this.finish();
                break;
            case R.id.btnSaveScore:
                scoreModel = new ScoreModel(getApplicationContext());
                long res = scoreModel.insScore(getScoreInfo());
                if(res >-1){
                    Toast.makeText(SaveScoreActivity.this,"Insert " + res +
                            " Success! ", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SaveScoreActivity.this, "Insert Failed!",
                            Toast.LENGTH_SHORT).show();
                }
                this.finish();
                break;
        }
    }

    private ScoreValue getScoreInfo(){
        EditText editText = findViewById(R.id.edtPlayer);
        String Name = editText.getText().toString();
        return new ScoreValue(Name,ScoreData.point);
    }
}