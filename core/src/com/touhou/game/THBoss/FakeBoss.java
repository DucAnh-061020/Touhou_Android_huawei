package com.touhou.game.THBoss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.touhou.game.THUltilities.Bullet;
import com.touhou.game.THUltilities.FireStyle;
import com.touhou.game.THUltilities.MoveStyle;

import java.util.ListIterator;

public class FakeBoss extends Boss {
    private float timeBettweenShoot;
    private TextureRegion bulletRegion;
    private String moveStyle;

    //to control bullet fire style
    FireStyle fireStyle;

    public FakeBoss(float speed, float xPos, float yPos, TextureAtlas textureAtlas, TextureAtlas bulletAtlas) {
        super(speed, textureAtlas, bulletAtlas);
        textureRegion = textureAtlas.findRegion("FED1");
        bulletRegion = bulletAtlas.findRegion("BlueBullet");
        timeBettweenShoot = 0.3f;
        hp = maxHP = 1000.0f;
        tll = 0.0f;
        timeTillSpawn = 3.0f;
        boundingBox = new Rectangle(xPos,yPos,
                Gdx.graphics.getDensity()*textureRegion.getRegionWidth(),
                Gdx.graphics.getDensity()*textureRegion.getRegionHeight());
        centerBossVector = new Vector2(
                boundingBox.x + boundingBox.width/2,
                boundingBox.y + boundingBox.height/2
        );
    }

    @Override
    public boolean canFire() {
        return timeSinceLastShoot - timeBettweenShoot > 0;
    }

    @Override
    public Bullet[] fire() {
        timeSinceLastShoot = 0;
        if(hp >= maxHP/2){
            Bullet bullet = new Bullet(speed*2,boundingBox.x+boundingBox.width*.55f,boundingBox.y+boundingBox.height*.5f,
                    bulletRegion,80,90);
            fireStyle = new FireStyle(bullet);
            return fireStyle.spreedShoot(10,3);
        }
        Bullet bullet = new Bullet(speed*2,boundingBox.x+boundingBox.width*.55f,boundingBox.y+boundingBox.height*.5f,
                bulletRegion,90,90);
        fireStyle = new FireStyle(bullet);
        return fireStyle.circleShoot(30);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        switch (currentState){
            case Boss.IS_SPAWNED:{
                switch (moveStyle){
                    case "moveRight":
                        MoveStyle.moveToRight(deltaTime,boundingBox,speed);
                        break;
                    case "moveLeft":
                        MoveStyle.moveToLeft(deltaTime,boundingBox,speed);
                        break;
                    case "spawn":
                        moveStyle = "moveLeft";
                        break;
                    case "standCenter":
                        if(boundingBox.x > (Gdx.graphics.getWidth()/2)-boundingBox.width/2){
                            MoveStyle.moveToLeft(deltaTime,boundingBox,speed);
                        }
                        if(boundingBox.x < (Gdx.graphics.getWidth()/2)-boundingBox.width/2){
                            MoveStyle.moveToRight(deltaTime,boundingBox,speed);
                        }
                        break;
                }
                if(hp >= maxHP/2){
                    if (boundingBox.x <= boundingBox.width) {
                        moveStyle = "moveRight";
                    }
                    if (boundingBox.x >= Gdx.graphics.getWidth() - boundingBox.width) {
                        moveStyle = "moveLeft";
                    }
                }else{
                    moveStyle = "standCenter";
                }
                break;
            }
            case Boss.JUST_SPAWN:
                if(boundingBox.y > Gdx.graphics.getHeight()* 9/10){
                    MoveStyle.moveDown(deltaTime,boundingBox, speed);
                    moveStyle = "spawn";
                }else{
                    currentState = Boss.IS_SPAWNED;
                }
                break;
        }
    }

    public void renderBullet(float deltaTime, SpriteBatch batch, float gameWidth, float gameHeight) {
        //create new bullet
        if (canFire()) {
            Bullet[] bullets = fire();
            for (Bullet bullet : bullets) {
                bossBulletList.add(bullet);
            }
        }
        //draw bullet
        //remove bullet
        ListIterator<Bullet> iterator = bossBulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            if (bullet.boundingBox.y > gameHeight+20 || bullet.boundingBox.y < -20) {
                iterator.remove();
            }
            else if (bullet.boundingBox.x > gameWidth+20 || bullet.boundingBox.x < -20){
                iterator.remove();
            }
            bullet.bulletTransition(deltaTime);
        }
    }
}
