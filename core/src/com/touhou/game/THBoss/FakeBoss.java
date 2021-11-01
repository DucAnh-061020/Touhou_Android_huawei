package com.touhou.game.THBoss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.touhou.game.THUltilities.Bullet;
import com.touhou.game.THUltilities.FireStyle;
import com.touhou.game.THUltilities.MoveStyle;

import java.util.ListIterator;

public class FakeBoss extends Boss {
    private float timeBettweenShoot;
    private TextureRegion bulletRegion;

    //to control bullet fire style
    FireStyle fireStyle;

    public FakeBoss(float speed, float width, float height, float xPos, float yPos, TextureAtlas textureAtlas, TextureAtlas bulletAtlas) {
        super(speed, width, height, xPos, yPos, textureAtlas, bulletAtlas);
        textureRegion = textureAtlas.findRegion("FED1");
        bulletRegion = bulletAtlas.findRegion("BlueBullet");
        timeBettweenShoot = 0.3f;
        hp = maxHP = 1000.0f;
        tll = 0.0f;
        timeTillSpawn = 3.0f;
    }

    @Override
    public boolean canFire() {
        return timeSinceLastShoot - timeBettweenShoot > 0;
    }

    @Override
    public Bullet[] fire() {
        timeSinceLastShoot = 0;
        if(hp >= maxHP/2){
            Bullet bullet = new Bullet(speed,boundingBox.x+boundingBox.width*.55f,boundingBox.y+boundingBox.height*.5f,boundingBox.width/6,boundingBox.width/4,bulletRegion,80,90);
            fireStyle = new FireStyle(bullet);
            return fireStyle.spreedShoot(10,3);
        }
        Bullet bullet = new Bullet(speed,boundingBox.x+boundingBox.width*.55f,boundingBox.y+boundingBox.height*.5f,boundingBox.width/6,boundingBox.width/4,bulletRegion,90,90);
        fireStyle = new FireStyle(bullet);
        return fireStyle.circleShoot(30);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(currentState == Boss.IS_SPAWNED){
            if(boundingBox.y > Gdx.graphics.getHeight()* 3/4 )
                MoveStyle.moveDown(deltaTime,boundingBox, speed);
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
