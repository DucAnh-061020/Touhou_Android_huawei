package com.touhou.game.THUltilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    private double dirX,dirY;
    public float speed,//bullet speed
            angle,//shoot angle
            direction,//bullet pointing direction
            curve = 0,//how much the bullet will turn when fired
            ttl;//bullet time to live
    public Rectangle boundingBox;


    //graphic
    TextureRegion textureRegion;

    public Bullet(float speed,
                  float xPos, float yPos,
                  float width, float height,
                  TextureRegion textureRegion,float angle, float direction) {
        this.angle = angle;
        this.speed = speed;
        this.direction = direction;
        boundingBox = new Rectangle(xPos,yPos,width,height);
        this.textureRegion = textureRegion;
        ttl=0;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,
                boundingBox.x,boundingBox.y,
                0,0,
                boundingBox.width,
                boundingBox.height,
                1,1,
                angle+ direction);
    }

    public void bulletTransition(float deltaTime){
        this.dirX = xDir(this.angle);
        this.dirY = yDir(this.angle);
        if(this.curve != 0)
            boundingBox.setPosition(boundingBox.x + this.curve*deltaTime,
                    boundingBox.y + this.curve*deltaTime);
        if(ttl > 0){
            boundingBox.setPosition((float) (boundingBox.x + this.dirX * speed * deltaTime),
                    (float) (boundingBox.y + this.dirY * speed * deltaTime));
            ttl-= deltaTime;
            if(ttl <= 0){
                ttl = -1;
            }
            return;
        }
        if(ttl == 0){
            boundingBox.setPosition((float) (boundingBox.x + this.dirX * speed * deltaTime),
                    (float) (boundingBox.y + this.dirY * speed * deltaTime));
            return;
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(boundingBox.x,boundingBox.y,
                boundingBox.width,boundingBox.height);
    }

    static double xDir(float angle) {
        double radians = angle * Math.PI / 180;
        return Math.cos(radians);
    }

    static double yDir(float angle) {
        double radians = angle * Math.PI / 180;
        return -Math.sin(radians);
    }

}
