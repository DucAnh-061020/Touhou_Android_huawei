package com.touhou.game.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;
import java.util.ListIterator;

public class BPoint extends ItemBuff{
    public static final int value = 10000;
    public static LinkedList<BPoint> bPointLinkedList = new LinkedList<>();

    public BPoint(float xPox, float yPox, float width, float height, TextureAtlas itemAtlas) {
        super(xPox, yPox, width, height, itemAtlas);
        item = itemAtlas.findRegion("bpoint");
        type = ItemBuff.BPOINT;
        boundingBox = new Rectangle(xPox,yPox,
                item.getRegionWidth()* Gdx.graphics.getDensity(),
                item.getRegionHeight()* Gdx.graphics.getDensity());
    }

    public static void renderBPoint(SpriteBatch batch, float deltaTime, float gameWidth, float gameHeight){
        ListIterator<BPoint> iterator = bPointLinkedList.listIterator();
        while (iterator.hasNext()){
            BPoint bPoint = iterator.next();
            bPoint.draw(batch);
            if (bPoint.boundingBox.y > gameHeight+20 || bPoint.boundingBox.y < -20) {
                iterator.remove();
            }
            else if (bPoint.boundingBox.x > gameWidth+20 || bPoint.boundingBox.x < -20){
                iterator.remove();
            }
            bPoint.update(deltaTime);
        }
    }
}
