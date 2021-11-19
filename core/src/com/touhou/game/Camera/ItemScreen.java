package com.touhou.game.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ItemScreen {
    public Stage stage;
    private Skin skin;
    private TextureAtlas itemAtlas;
    private Label lives,power,score;
    public ItemScreen(SpriteBatch batch, Viewport viewport){
        skin = new Skin(Gdx.files.internal("skin/skin.json"));
        stage = new Stage(viewport,batch);
        Table table = new Table();
        table.left().top();
        table.setFillParent(true);
        itemAtlas = new TextureAtlas("Atlas/Itemdrops.atlas");
        lives = new Label("x3",skin);
        Image image = new Image(itemAtlas.findRegion("lifeup"));
        image.setScaling(Scaling.fit);
        image.setScale(Gdx.graphics.getDensity());
        table.add(image).padRight(image.getWidth()*Gdx.graphics.getDensity())
                        .padTop(image.getHeight()*Gdx.graphics.getDensity());
        lives.setAlignment(Align.left);
        table.add(lives);

        table.row();
        image = new Image(itemAtlas.findRegion("powerup"));
        image.setScaling(Scaling.fit);
        image.setScale(Gdx.graphics.getDensity());
        table.add(image).padRight(image.getWidth()*Gdx.graphics.getDensity())
                .padTop(image.getHeight()*Gdx.graphics.getDensity());
        power = new Label("0",skin);
        table.add(power);

        table.row();
        image = new Image(itemAtlas.findRegion("bpoint"));
        image.setScaling(Scaling.fit);
        image.setScale(Gdx.graphics.getDensity());
        table.add(image).padRight(image.getWidth()*Gdx.graphics.getDensity())
                .padTop(image.getHeight()*Gdx.graphics.getDensity());
        score = new Label("0",skin);
        table.add(score);

        stage.addActor(table);
    }

    public void update(int livesLeft,int playerpower,int gamescore)
    {
        lives.setText("x"+livesLeft);
        power.setText(playerpower);
        score.setText(gamescore);
    }

    public void dispose(){
        stage.dispose();
        itemAtlas.dispose();
        skin.dispose();
    }
}
