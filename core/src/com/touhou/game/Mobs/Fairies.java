package com.touhou.game.Mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.touhou.game.Stages.Stage1;
import com.touhou.game.THUltilities.Bullet;

public abstract class Fairies {
    public float speed;
    public static final int IS_SPAWNED = 0;
    public static final int IS_DESTROY = 1;
    public static final int NOT_SPAWN = 2;
    public static final int DESPAWN = 3;
    public int currentState = NOT_SPAWN;
    //position & dimension
    public Rectangle boundingBox;
    public float maxbullet;
    public Vector2 directionVector;
    public Vector2 centerFairesVector;

    //graphic
    TextureRegion fairyRegion;
    TextureAtlas fairyAtlas,bulletAtlas;
    public float ttl;

    //bullet
    public float timeSinceLastShoot = 0,timeBetweenShoot = .3f;

    public Fairies(float speed,TextureAtlas fairyAtlas,TextureAtlas bulletAtlas,float maxbullet) {
        this.speed = speed;
        this.fairyAtlas = fairyAtlas;
        this.bulletAtlas = bulletAtlas;
        this.maxbullet = maxbullet;
    }

    public void update(float deltaTime){
        centerFairesVector.x = boundingBox.x + boundingBox.width/2;
        centerFairesVector.y = boundingBox.y + boundingBox.height/2;
        timeSinceLastShoot += deltaTime;
    }

    public void randomizeDirectionVector(){
        double bearing = Stage1.generator.nextDouble()*2*Math.PI;
        directionVector.x = (float)Math.sin(bearing);
        directionVector.y = (float)Math.cos(bearing);
    }

    public abstract boolean canFire();
    public abstract Bullet[] fire();

    public boolean intersects(Rectangle otherRect){
        return boundingBox.overlaps(otherRect);
    }

    public void draw(SpriteBatch batch){
        batch.draw(fairyRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
}
