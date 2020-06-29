package com.skurnal2.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class OtherCar {
    private static int ROAD_RANGE = 150; //Original Road Range is 150px in x axis
    private Texture otherCar;
    private Vector2 enemyPosition;
    private Random rand;
    private int randomCarNumber;
    int randRoadPositionX, carPosition;

    public OtherCar(float y) {
        rand = new Random();
        randRoadPositionX = rand.nextInt(ROAD_RANGE);
        if(randRoadPositionX > 0 && randRoadPositionX <= 37.5)
        {
            carPosition = 5;
        }
        else if(randRoadPositionX > 37.5 && randRoadPositionX <= 75)
        {
            carPosition = 51;
        }
        else if(randRoadPositionX > 75 && randRoadPositionX <= 112.5)
        {
            carPosition = 97;
        }
        else if(randRoadPositionX > 112.5 && randRoadPositionX <= 150)
        {
            carPosition = 142;
        }
        randomCarNumber = rand.nextInt(8);
        otherCar = new Texture("enemy_car_" + randomCarNumber + ".png");
        enemyPosition = new Vector2(30 + carPosition, y + 900); //30 padding from left side and start from 900px from bottom
    }

    public Texture getOtherCar() {
        return otherCar;
    }

    public Vector2 getOtherCarPosition() {
        return enemyPosition;
    }

    public void reposition(float y) {
        enemyPosition.set(30+rand.nextInt(ROAD_RANGE), y);

    }


}
