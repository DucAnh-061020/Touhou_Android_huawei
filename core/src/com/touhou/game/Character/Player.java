package com.touhou.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.touhou.game.THUltilities.Bullet;
import com.touhou.game.THUltilities.FireStyle;

import java.util.LinkedList;
import java.util.ListIterator;

public abstract class Player {
    public float imoFrame = 3.0f;
    // player textures
    protected TextureRegion[] playerTexture;
    protected TextureAtlas playerAtlas, playerBulletAtlas;

    //position & dimension
    public Rectangle boundingBox;
    protected float speed;
    public Vector2 centerPlayerVector;

    //graphic
    protected Animation<TextureRegion> idle;

    //bullet info
    protected float timeSinceLastShoot = 0,timeSinceLastSpecial = 0;
    protected float bSpeed;
    protected float timeBetweenShoot = 0.1f;
    public int normalAmuletDmg = 10; // damage per shoot
    public LinkedList<Bullet> characterBulletList = new LinkedList<>();
    public LinkedList<Bullet> characterSpecialBullet = new LinkedList<>();
    FireStyle fireStyle;
    // player info
    public int power = 1;
    public int powerLevel = 0;
    public int lives = 3;

    public Player() {
    }

    public void setSpeed(float speed){
        this.speed = speed;
        this.bSpeed = speed*3;
    }

    public void update(float deltaTime){
        timeSinceLastShoot += deltaTime;
        imoFrame -= deltaTime;
        //create new bullet
        if (canFire()) {
            addAmulet();
        }
        if(power%10==0){
            powerLevel = power/10;
        }
    }

    public void specialAmuletUpdate(Rectangle target,float deltaTime){
        if(powerLevel > 0)
            timeSinceLastSpecial += deltaTime;
        if(canFireSpecial()){
            addSpecialAmulet();
        }
        if(target.width != 0.0f && target.height != 0.0f){
            ListIterator<Bullet> iterator = characterSpecialBullet.listIterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                Vector2 targetCenter = new Vector2(target.x + target.width/2,target.y + target.height/2);
                Vector2 bulletCenter = new Vector2(
                        bullet.boundingBox.x + bullet.boundingBox.width/2,
                        bullet.boundingBox.y + bullet.boundingBox.height/2
                );
                double degrees = Math.atan2(
                        targetCenter.y - bulletCenter.y,
                        targetCenter.x - bulletCenter.x
                ) * 180.0d / Math.PI;
                bullet.angle = (float) -degrees;
            }
        }
    }

    public abstract boolean canFire();
    public abstract boolean canFireSpecial();
    public abstract Bullet[] fire();

    public abstract Bullet[] specialFire();

    // for chase amulet
    public void addSpecialAmulet() {
        if(power >= 10){
            Bullet[] bullets = specialFire();
            for (Bullet bullet:bullets){
                characterSpecialBullet.add(bullet);
            }
        }
    }
    // for regular bullet create
    public void addAmulet() {
        Bullet[] bullets = fire();
        for (Bullet bullet : bullets) {
            characterBulletList.add(bullet);
        }
    }

    public void drawAmulet(float deltaTime,SpriteBatch batch,float gameWidth, float gameHeight){
        //draw bullet
        //remove bullet
        ListIterator<Bullet> iterator = characterBulletList.listIterator();
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
        iterator = characterSpecialBullet.listIterator();
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

    public void translate(float xChange,float yChange){
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
        centerPlayerVector.x = boundingBox.x + boundingBox.width/2;
        centerPlayerVector.y = boundingBox.y + boundingBox.height/2;
    }
    //draw player animation
    public void drawAnimation(SpriteBatch batch,float elapseTime){
        batch.draw(idle.getKeyFrame(elapseTime,true),boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
    //check if got hit
    public boolean intersectsHitbox(Rectangle otherRect){
        return (centerPlayerVector.x+boundingBox.width/2 > otherRect.x && centerPlayerVector.x+boundingBox.width/2 < otherRect.x + otherRect.width &&
                centerPlayerVector.y > otherRect.y && centerPlayerVector.y < otherRect.y + otherRect.height);
    }
    // check if item fall into collect area
    public boolean intersectsCollect(Rectangle otherRect){
        return Intersector.overlaps(boundingBox,otherRect);
    }
    // control on screen while playing
    public void inputControl(float deltaTime, float gameWidth, float gameHeight, Viewport viewport) {
        if (Gdx.input.isTouched()) {
            float rightLimit, leftLimit, upLimit, downLimit;
            downLimit = -boundingBox.y;
            leftLimit = -boundingBox.x;
            rightLimit = gameWidth - boundingBox.x - boundingBox.width + 10;
            upLimit = gameHeight - boundingBox.y - boundingBox.height + 10;
            //get screen position of touch
            float xTouchPixel = Gdx.input.getX();
            float yTouchPixel = Gdx.input.getY();

            //world position
            Vector2 touchpoint = new Vector2(xTouchPixel, yTouchPixel);
            touchpoint = viewport.unproject(touchpoint);

            float touchDistance = touchpoint.dst(centerPlayerVector);
            float TOUCH_MOVEMENT_THRESHOLD = 15f;
            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {
                float xDiff = touchpoint.x - centerPlayerVector.x;
                float yDiff = touchpoint.y - centerPlayerVector.y;

                //scale max speed of character
                float xMove = xDiff / touchDistance * speed * deltaTime;
                float yMove = yDiff / touchDistance * speed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);
                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);
                translate(xMove, yMove);
            }
        }
    }
}
