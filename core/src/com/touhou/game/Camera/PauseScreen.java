package com.touhou.game.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.touhou.game.Core;

public class PauseScreen {
    public Stage stage;
    public Skin skin;

    public PauseScreen(SpriteBatch batch) {
        skin = new Skin(Gdx.files.internal("skin/skin.json"));
        Viewport viewport = new FitViewport(Core.gameWidth, Core.gameHeight, new OrthographicCamera());
        stage = new Stage(viewport,batch);
        Gdx.input.setInputProcessor(stage);
        float fontsize = Core.pixelToDP(1f);

        Table table = new Table();
        table.setBackground(skin.getDrawable("Menu02"));
        table.setFillParent(true);

        Table table1 = new Table();

        Label label = new Label("Resume", skin);
        label.setTouchable(Touchable.enabled);
        label.setFontScale(fontsize);
        label.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Core.setStageStatus(Core.GAME_RESUME);
                System.out.println("click resume");
                super.touchUp(event,x,y,pointer,button);
            }
        });
        table1.add(label);

        table1.row();
        label = new Label("Quit", skin);
        label.setFontScale(fontsize);
        label.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Core.setStageStatus(Core.GAME_OVER);
                System.out.println("click out");
                super.touchUp(event,x,y,pointer,button);
            }
        });
        table1.add(label);
        table.add(table1);
        stage.addActor(table);
    }

    public void setVisible(boolean visible) {
        Array<Actor> actors =  stage.getActors();
        for (Actor actor : actors){
            actor.setVisible(visible);
        }
    }

    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}
