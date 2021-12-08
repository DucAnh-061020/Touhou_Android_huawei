package com.touhou.game.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.touhou.game.Core;
import com.touhou.game.staticData.AppSetting;
import com.touhou.game.staticData.ScoreData;

public class AndroidLauncher extends AndroidApplication implements Core.MyCallbackListener {
	Intent intent;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Core(1,this), config);

		if(!AppSetting.SFX){
			Core.VOLUMESFX = 0;
		}
		Core.VOLUMESFX = 1;
		if(!AppSetting.BGM){
			Core.VOLUMEBGM = 0;
		}
		Core.VOLUMEBGM = 1;
	}

	@Override
	public void onBackPressed() {
		if(Core.GAME_STATUS == Core.GAME_RESUME || Core.GAME_STATUS == Core.GAME_PAUSE)
			moveTaskToBack(false);
		else
			super.onBackPressed();
	}

	@Override
	public void startActivity(int result) {
		ScoreData.point = result;
		intent = new Intent(AndroidLauncher.this,SaveScoreActivity.class);
		AndroidLauncher.this.startActivity(intent);
		finish();
	}

	@Override
	public void closeActivity(){
		intent = new Intent(AndroidLauncher.this,SaveScoreActivity.class);
		AndroidLauncher.this.startActivity(intent);
		finish();
	}
}
