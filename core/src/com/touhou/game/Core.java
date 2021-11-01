package com.touhou.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.touhou.game.Stages.Stage1;

public class Core extends Game {
	private int CURRENT_SCREEN;
	public static float gameWidth,gameHeight;
	// menu control
	public static final int GAME_PLAY = 1;
	public static final int GAME_EXIT = 0;
	//game status
	public static int GAME_STATUS;
	public static final int GAME_OVER_SCREEN = 6;
	public static final int GAME_PAUSE = 7;
	public static final int GAME_RESUME = 8;
	public static final int GAME_OVER = 9;
	public static final int END_STAGE = 10;

	public static float VOLUME = 1;
	public static Sound shootSE;
	public static Music bgm;

	public interface MyCallbackListener {
		void startActivity(int result);
		void closeActivity();
	}

	public MyCallbackListener callbackListener;

	public Core(int stageScreen,MyCallbackListener callbackListener)
	{
		this.callbackListener = callbackListener;
		CURRENT_SCREEN = stageScreen;
	}

	@Override
	public void create() {
		gameWidth = Gdx.graphics.getWidth();
		gameHeight = Gdx.graphics.getHeight();
		changeScreen(CURRENT_SCREEN);
	}

	public void changeScreen(int screen){
		switch(screen){
			case GAME_PLAY:
				setScreen(new Stage1(this));
				break;
			case GAME_EXIT:
				dispose();
				break;
		}
	}
	public static void setStageStatus(int gameStatus){
		GAME_STATUS = gameStatus;
	}

	public static float pixelToDP(float dp) {
		return dp * Gdx.graphics.getDensity();
	}

}
