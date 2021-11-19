package com.touhou.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.touhou.game.Core;
import com.touhou.game.THUltilities.Bullet;
import com.touhou.game.THUltilities.FireStyle;

import java.util.ArrayList;
import java.util.List;

public class Reimu extends Player{
//    lv0: 1 line 0 chase
//    lv1: 1 line 2 chase .5f
//    lv2: 2 line 2 chase .5f
//    lv3: 2 line 2 chase .3f
//    lv4: 3 line 2 chase .2f
//    lv5: 3 line 2 chase .1f
//    lv6: 4 line 2 chase .1f
//    lv7: 5 line 2 chase .1f

    // side effect textures
    private TextureRegion normalAmuletTexture,
            chaseAmuletTexture,
            spellC;
//    reimuHB;
//    public ShapeRenderer shapeRenderer;
    private Bullet normalAmulet;
    private Bullet chaseAmulet;

    public Reimu(float xPos, float yPos) {
        playerAtlas = new TextureAtlas("Atlas/Reimu.atlas");
        playerTexture = new TextureRegion[8];
        playerTexture[0] = playerAtlas.findRegion("reimu_idle01");
        playerTexture[1] = playerAtlas.findRegion("reimu_idle02");
        playerTexture[2] = playerAtlas.findRegion("reimu_idle03");
        playerTexture[3] = playerAtlas.findRegion("reimu_idle04");
        playerTexture[4] = playerAtlas.findRegion("reimu_idle05");
        playerTexture[5] = playerAtlas.findRegion("reimu_idle06");
        playerTexture[6] = playerAtlas.findRegion("reimu_idle07");
        playerTexture[7] = playerAtlas.findRegion("reimu_idle08");
        idle = new Animation(1f/8f, playerTexture);
        boundingBox = new Rectangle(xPos,yPos,
                playerTexture[0].getRegionWidth()* Gdx.graphics.getDensity(),
                playerTexture[0].getRegionHeight()*Gdx.graphics.getDensity());
//        bulletInfo = LoadXmlDoc.characterBulletInfo("gameData/ReimuBullet.xml");
        playerBulletAtlas = new TextureAtlas("Atlas/ReimuSpell.atlas");
        spellC = playerBulletAtlas.findRegion("ReimuSpell");
//        reimuHB = playerBulletAtlas.findRegion("Effectelse");
        // create origin bullet data
        normalAmuletTexture = playerBulletAtlas.findRegion("atk_card");
        chaseAmuletTexture = playerBulletAtlas.findRegion("auto_card");
        centerPlayerVector = new Vector2(
                boundingBox.x+boundingBox.width/2,
                boundingBox.y+boundingBox.height/2
        );
        speed = 1000f;
        bSpeed = speed * 2f;
    }

    @Override
    public boolean canFire() {
        return (timeSinceLastShoot - timeBetweenShoot) > 0;
    }

    @Override
    public boolean canFireSpecial() {
        switch (powerLevel){
            case 1:
            case 2:
                return (timeSinceLastSpecial - .5) > 0;
            case 3:
            case 4:
                return (timeSinceLastSpecial - .3) > 0;
            case 5:
                return (timeSinceLastSpecial - .2) > 0;
            case 6:
            case 7:
            case 8:
                return (timeSinceLastSpecial - .1) > 0;
            default: return false;
        }
    }

    @Override
    public Bullet[] fire() {
        // play sound effect on shoot
        final long shootId = Core.shootSE.play(1);
        // free memory of last sound effect
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Core.shootSE.stop(shootId);
            }
        },1);
        timeSinceLastShoot=0;
        List<Bullet> normalAmuletList = new ArrayList<>();
        Bullet[] bullets;
        if(power > 80)
            power = 80;
        switch (powerLevel){
            case 2:
            case 3:
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*.6f),
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x,
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);
                break;
            case 4:
            case 5:
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*1.2f),
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x - (boundingBox.width*0.6f),
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*.3f),
                        boundingBox.y+boundingBox.height*.7f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);
                break;
            case 6:
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*.6f),
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();

                normalAmuletList.add(bullets[0]);
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*1.2f),
                        boundingBox.y+boundingBox.height*.3f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x,
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x-(boundingBox.width*.6f),
                        boundingBox.y+boundingBox.height*.3f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);
                break;
            case 7:
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.width/2, boundingBox.width*0.7f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x - (boundingBox.width*0.6f),
                        boundingBox.y+boundingBox.height*.5f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*.3f),
                        boundingBox.y+boundingBox.height*.7f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*1.8f),
                        boundingBox.y+boundingBox.height*.3f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);

                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x - (boundingBox.width*1.2f),
                        boundingBox.y+boundingBox.height*.3f,
                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                normalAmuletList.add(bullets[0]);
                break;
            default:
                normalAmulet = new Bullet(bSpeed,
                        boundingBox.x+(boundingBox.width*.3f),
                        boundingBox.y+boundingBox.height*.5f,

                        normalAmuletTexture, -90,90);
                fireStyle = new FireStyle(normalAmulet);
                bullets = fireStyle.singleShoot();
                for (Bullet bullet: bullets)
                    normalAmuletList.add(bullet);
                break;
        }
        return normalAmuletList.toArray(new Bullet[0]);
    }

    @Override
    public Bullet[] specialFire() {
        List<Bullet> bulletList = new ArrayList<>();
        timeSinceLastSpecial = 0;
        if(power >= 10){
            chaseAmulet = new Bullet(bSpeed,
                    boundingBox.x+(boundingBox.width*1.3f),
                    boundingBox.y+boundingBox.height*.4f,
                    chaseAmuletTexture, -30,30);
            fireStyle = new FireStyle(chaseAmulet);
            Bullet[] bullets = fireStyle.chaseShoot(1,30);
            for(Bullet bullet : bullets){
                bulletList.add(bullet);
            }
            chaseAmulet = new Bullet(bSpeed,
                    boundingBox.x-(boundingBox.width*1.6f),
                    boundingBox.y+boundingBox.height*.4f,
                    chaseAmuletTexture, -150,150);
            fireStyle = new FireStyle(chaseAmulet);
            bullets = fireStyle.chaseShoot(1,30);
            for(Bullet bullet : bullets){
                bulletList.add(bullet);
            }
        }
        if(bulletList.size() > 0)
            return bulletList.toArray(new Bullet[0]);
        return new Bullet[0];
    }

    public void draw(SpriteBatch batch, float deltaTime) {
//      draw hitbox
//        batch.draw(reimuHB, boundingBox.x - boundingBox.width*.6f, boundingBox.y,
//                boundingBox.height / 2f, boundingBox.height / 2f,
//                boundingBox.height, boundingBox.height,
//                2, 2, 30 * deltaTime);
        //draw spell circle
        if (power >= 10) {
            batch.draw(spellC,
                    boundingBox.x+(boundingBox.width*1.3f),
                    boundingBox.y+boundingBox.height*.4f,
                    boundingBox.width/2,boundingBox.width/2,
                    boundingBox.width,boundingBox.width,
                    1,1,90*deltaTime);
            batch.draw(spellC,
                    boundingBox.x-(boundingBox.width*1.6f),
                    boundingBox.y+boundingBox.height*.4f,
                    boundingBox.width/2,boundingBox.width/2,
                    boundingBox.width,boundingBox.width,
                    1,1,90*deltaTime);
        }
    }
    public void addPower(int value){
        power += value;
        if(power >= 60){
            power = 59;
        }
    }
}
