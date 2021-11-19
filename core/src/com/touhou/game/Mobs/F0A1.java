package com.touhou.game.Mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.touhou.game.THUltilities.Bullet;
import com.touhou.game.THUltilities.MoveStyle;

import java.util.LinkedList;
import java.util.ListIterator;

public class F0A1 extends Fairies {

    TextureRegion bulletRegion;
    public int hp = 100;
    public LinkedList<Bullet> F0A1BulletList = new LinkedList<>();
    public String exitRoute="up";
    ListIterator<Bullet> iterator;

    public final static String DOWNLEFT = "downleft",DOWNRIGHT = "downright",
                    UPLEFT = "upleft",UPRIGHT = "upright",
                    UP="up",DOWN="down",LEFT="left",RIGHT="right";

    public F0A1(float speed, float xPos, float yPos, TextureAtlas fairyAtlas, TextureAtlas bulletAtlas, float maxbullet) {
        super(speed,fairyAtlas,bulletAtlas,maxbullet);
        fairyRegion = fairyAtlas.findRegion("F0A1");
        boundingBox = new Rectangle(
                xPos,yPos,
                fairyRegion.getRegionWidth()* Gdx.graphics.getDensity(),
                fairyRegion.getRegionHeight()* Gdx.graphics.getDensity());
        bulletRegion = bulletAtlas.findRegion("BlueBullet");
        directionVector = new Vector2(0,-1);
        centerFairesVector = new Vector2(
                boundingBox.x+boundingBox.width/2,
                boundingBox.y+boundingBox.height/2
        );
    }

    @Override
    public boolean canFire() {
        if(this.maxbullet == 0)
            return false;
        return (timeSinceLastShoot - timeBetweenShoot) >= 0;
    }

    @Override
    public Bullet[] fire() {
        timeSinceLastShoot = 0;
        Bullet[] bullets = new Bullet[3];
        bullets[0] = new Bullet(1000,boundingBox.x+boundingBox.width*.6f,boundingBox.y+10,bulletRegion,90,0);
        bullets[1] = new Bullet(1000,boundingBox.x+boundingBox.width*.6f,boundingBox.y+10,bulletRegion,95,0);
        bullets[2] = new Bullet(1000,boundingBox.x+boundingBox.width*.6f,boundingBox.y+10,bulletRegion,85,0);
        return bullets;
    }

    public void renderF0A1Bullet(SpriteBatch batch, float deltaTime, float gameHeight, float gameWidth) {
        //check state to add bullet
        if(currentState == IS_SPAWNED || currentState == DESPAWN)
            if (canFire()) {
                Bullet[] bullets = fire();
                if(maxbullet != -1)
                    maxbullet--;
                for (Bullet bullet : bullets) {
                    F0A1BulletList.add(bullet);
                }
            }
        iterator = F0A1BulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            if (bullet.boundingBox.y > gameHeight || bullet.boundingBox.y < 0) {
                iterator.remove();
            }
            else if (bullet.boundingBox.x > gameWidth || bullet.boundingBox.x < 0){
                iterator.remove();
            }
            bullet.bulletTransition(deltaTime);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if((ttl -=deltaTime) <= 0){
            switch (exitRoute){
                case "downleft":
                    MoveStyle.downLeft(deltaTime,boundingBox,speed);break;
                case "downright":
                    MoveStyle.downRight(deltaTime,boundingBox,speed);break;
                case "upleft":
                    MoveStyle.upLeft(deltaTime,boundingBox,speed);break;
                case "upright":
                    MoveStyle.upRight(deltaTime,boundingBox,speed);break;
                case "up":
                    MoveStyle.moveUp(deltaTime,boundingBox,speed);break;
                case "down":
                    MoveStyle.moveDown(deltaTime,boundingBox,speed);break;
                case "left":
                    MoveStyle.moveToLeft(deltaTime,boundingBox,speed);break;
                case "right":
                    MoveStyle.moveToRight(deltaTime,boundingBox,speed);break;
                default:
                    MoveStyle.moveUp(deltaTime,boundingBox,speed);
                    break;
            }
            return;
        }
        MoveStyle.moveDown(deltaTime,boundingBox,speed);
    }
}