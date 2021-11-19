package com.touhou.game.THUltilities;

public class FireStyle {
    Bullet originBullet;
    public FireStyle(Bullet bulletData){
        originBullet = bulletData;
    }

    public Bullet[] circleShoot(float spreed){
        int totalShoot = (int) Math.floor(360/spreed);
        Bullet[] bullets = new Bullet[totalShoot];
        for (int i = 0; i< totalShoot; i++){
            bullets[i] = new Bullet(originBullet.speed,originBullet.boundingBox.x,originBullet.boundingBox.y,
                    originBullet.textureRegion,
                    originBullet.angle+spreed*i,originBullet.direction+(spreed+originBullet.angle)*i);
        }
        return bullets;
    }

    public Bullet[] spreedShoot(float spreed, int bulletPerShoot){
        Bullet[] bullets = new Bullet[bulletPerShoot];
        for(int i =0;i < bulletPerShoot; i++){
            bullets[i] = new Bullet(originBullet.speed,originBullet.boundingBox.x,originBullet.boundingBox.y,
                    originBullet.textureRegion,
                    originBullet.angle+spreed*i,originBullet.direction);
        }
        return bullets;
    }

    public Bullet[] straightShoot(int bulletPerShoot,float spacing){
        Bullet[] bullets = new Bullet[bulletPerShoot];
        for(int i =0;i < bulletPerShoot; i++){
            bullets[i] = new Bullet(originBullet.speed*(i+1),originBullet.boundingBox.x,originBullet.boundingBox.y,
                    originBullet.textureRegion,
                    originBullet.angle,originBullet.direction);
        }
        return bullets;
    }

    public Bullet[] singleShoot(){
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(originBullet.speed,originBullet.boundingBox.x,originBullet.boundingBox.y,
                originBullet.textureRegion,
                originBullet.angle,originBullet.direction);
        return bullets;
    }

    public Bullet[] chaseShoot(int rayCount,float spreedAngle) {
        Bullet[] bullets = new Bullet[rayCount];
        for (int i = 0; i < rayCount; i++){
            bullets[i] = new Bullet(originBullet.speed,originBullet.boundingBox.x,originBullet.boundingBox.y,
                    originBullet.textureRegion,
                    originBullet.angle+spreedAngle*i,originBullet.direction+spreedAngle*i);
        }
        return bullets;
    }
}
