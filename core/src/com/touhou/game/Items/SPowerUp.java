package com.touhou.game.Items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.LinkedList;
import java.util.ListIterator;

public class SPowerUp extends ItemBuff {
    public static final int value = 1;
    public static LinkedList<SPowerUp> sPowerUpLinkedList;

    public SPowerUp(float xPox, float yPox, float width, float height, TextureAtlas itemAtlas) {
        super(xPox, yPox, width, height, itemAtlas);
        item = itemAtlas.findRegion("powerup");
        type = ItemBuff.SPOWERUP;
    }

    public static void renderPowerup(SpriteBatch batch, float deltaTime, float gameWidth, float gameHeight){
        ListIterator<SPowerUp> iterator = sPowerUpLinkedList.listIterator();
        while (iterator.hasNext()){
            SPowerUp sPowerUp = iterator.next();
            sPowerUp.draw(batch);
            if (sPowerUp.boundingBox.y > gameHeight+20 || sPowerUp.boundingBox.y < -20) {
                iterator.remove();
            }
            else if (sPowerUp.boundingBox.x > gameWidth+20 || sPowerUp.boundingBox.x < -20){
                iterator.remove();
            }
            sPowerUp.update(deltaTime);
        }
    }
}
