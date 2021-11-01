package com.touhou.game.THUltilities;


import com.badlogic.gdx.math.Rectangle;

public class MoveStyle {
    public static void moveDown(float deltaTime, Rectangle boundingBox,float speed) {
        double dirX = xDir(90);
        double dirY = yDir(90);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void moveUp(float deltaTime,Rectangle boundingBox,float speed){
        double dirX = xDir(90);
        double dirY = yDir(-90);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void moveToRight(float deltaTime,Rectangle boundingBox,float speed) {
        double dirX = xDir(0);
        double dirY = yDir(0);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void moveToLeft(float deltaTime,Rectangle boundingBox, float speed) {
        double dirX = xDir(180);
        double dirY = yDir(0);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void upLeft(float deltaTime,Rectangle boundingBox,float speed){
        double dirX = xDir(180);
        double dirY = yDir(-90);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }
    public static void upRight(float deltaTime,Rectangle boundingBox, float speed){
        double dirX = xDir(0);
        double dirY = yDir(-90);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void downLeft(float deltaTime, Rectangle boundingBox, float speed) {
        double dirX = xDir(180);
        double dirY = yDir(90);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
    }

    public static void downRight(float deltaTime, Rectangle boundingBox, float speed) {
        double dirX = xDir(0);
        double dirY = yDir(100);
        boundingBox.setPosition((float) (boundingBox.x + dirX * speed * deltaTime),
                (float) (boundingBox.y + dirY * speed * deltaTime));
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
