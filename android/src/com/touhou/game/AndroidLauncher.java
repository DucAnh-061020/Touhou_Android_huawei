package com.touhou.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication implements Core.MyCallbackListener {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Core(1,this), config);
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
	}

	@Override
	public void closeActivity(){
		finish();
	}
}
