package com.touhou.game.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDScreen {
    public Stage stage;

    Label lives, score,stagelevel;
    private Skin skin;

    public HUDScreen(SpriteBatch batch,Viewport viewport){
        skin = new Skin(Gdx.files.internal("skin/skin.json"));
        float fontsize = 1f;

        stage = new Stage(viewport,batch);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lives = new Label("Lives x 3", skin);
        stagelevel = new Label("Stage 1", skin);
        score = new Label("Score 0", skin);
        table.add(lives).expandX();
        table.add(stagelevel).expandX();
        table.add(score).expandX();

        lives.setFontScale(fontsize);
        stagelevel.setFontScale(fontsize);
        score.setFontScale(fontsize);
        stage.addActor(table);
    }

    public void update(int currentScore,int livesLeft){
        lives.setText("Lives x"+livesLeft);
        score.setText("Score "+ currentScore);
    }
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}
