package com.touhou.game.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.touhou.game.THUltilities.MoveStyle;

public abstract class ItemBuff {
    public static final int SPOWERUP = 0,SPOINT = 1,
                            BPOWERUP = 2,BPOINT = 3,
                            LIFEUP = 4,BOMB = 5;
    public float speed;
    public int type;

    public Rectangle boundingBox;

    TextureRegion item;
    TextureAtlas itemAtlas;

    public ItemBuff(float xPox, float yPox, float width, float height, TextureAtlas itemAtlas){
        this.itemAtlas = itemAtlas;
    }

    public void update(float deltaTime){
        MoveStyle.moveDown(deltaTime,boundingBox,speed);
    }

    public boolean intersects(Circle collectBox){
        return Intersector.overlaps(collectBox,boundingBox);
    }
    public void draw(SpriteBatch batch){
        batch.draw(item,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
}
