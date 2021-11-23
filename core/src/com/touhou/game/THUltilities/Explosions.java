package com.touhou.game.THUltilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosions {
    protected Animation<TextureRegion> explosion;
    protected TextureRegion[] explosionTexture;
    protected TextureAtlas textureAtlas;

    public Rectangle boundingBox;

    private float explosionTimer;

    public Explosions(TextureAtlas textureAtlas, float x, float y, float size) {
        this.textureAtlas = textureAtlas;
        explosionTimer = 0;
        boundingBox = new Rectangle(x,y,size*2,size*2);
        explosionTexture = new TextureRegion[70];
        for (int i = 0;i < explosionTexture.length;i++){
            if(i <10)
                explosionTexture[i] = textureAtlas.findRegion("frame000"+i);
            else
                explosionTexture[i] = textureAtlas.findRegion("frame00"+i);
        }
        explosion = new Animation(70f/16f,explosionTexture);
    }

    public void update(float deltaTime){
        explosionTimer+=deltaTime;
    }

    public void drawAnimation(SpriteBatch batch){
        batch.draw(explosion.getKeyFrame(explosionTimer),boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }

    public boolean isFinish(){
        return explosion.isAnimationFinished(explosionTimer);
    }
}
