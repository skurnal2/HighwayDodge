package com.skurnal2.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyCar {
    private static final int CAR_MOVEMENT = 250;
    private Vector2 position;
    private Vector2 velocity;



    private Texture myCar;
    private Texture myCarLeft;
    private Texture myCarRight;
    private Texture myCarStraight;

    public MyCar(int x, int y) {
        position = new Vector2(x, y);
        myCarStraight = new Texture("my_car.png");
        myCar = myCarStraight;
        myCarRight = new Texture("my_car_right.png");
        myCarLeft = new Texture("my_car_left.png");
    }

    public void update(float dt) {
        position.add(0, CAR_MOVEMENT * dt);

    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getMyCar() {
        return myCar;
    }

    public void moveLeft() {
        if(position.x < 180) {
            position.x += 1.2;
            myCar = myCarLeft;
        }
    }

    public void moveLeftFast() {
        if(position.x < 180) {
            position.x += 2;
            myCar = myCarLeft;
        }
    }

    public void moveRight() {
        if(position.x > 30) {
            position.x -= 1.2;
            myCar = myCarRight;
        }
    }

    public void moveRightFast() {
        if(position.x > 30) {
            position.x -= 2;
            myCar = myCarRight;
        }
    }

    public void moveStraight() {
        myCar = myCarStraight;
    }

}
