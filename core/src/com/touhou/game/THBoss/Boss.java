package com.touhou.game.THBoss;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.touhou.game.THUltilities.Bullet;

import java.util.LinkedList;

public abstract class Boss {

    public static final int IS_SPAWNED = 0,IS_DESTROY = 1,NOT_SPAWN = 2,JUST_SPAWN = 3;
    public float speed;
    public float immortalFrame = 0;
    public Rectangle boundingBox;
    public float timeSinceLastShoot=0;
    public int currentState;
    public float maxHP,timeTillSpawn,tll,waitTime,hp;
    public Vector2 centerBossVector;
    public LinkedList<Bullet> bossBulletList = new LinkedList<>();
    public float timePerMovePhase = 0,movePhaseTimer = 0;
    //graphics
    TextureRegion textureRegion;
    TextureAtlas textureAtlas,bulletAtlas;

    public Boss(float speed, TextureAtlas textureAtlas, TextureAtlas bulletAtlas) {
        this.speed = speed;
        this.textureAtlas = textureAtlas;
        this.bulletAtlas = bulletAtlas;
        currentState = NOT_SPAWN;
    }

    public void update(float deltaTime){
        centerBossVector.x = boundingBox.x + boundingBox.width/2;
        centerBossVector.y = boundingBox.y + boundingBox.height/2;
        timeSinceLastShoot += deltaTime;
    }

    public abstract boolean canFire();
    public abstract Bullet[] fire();

    public boolean intersects(Rectangle otherRect){
        return boundingBox.overlaps(otherRect);
    }

    public void draw(SpriteBatch batch){
        if(currentState != IS_DESTROY){
            batch.draw(textureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }
    }
}
