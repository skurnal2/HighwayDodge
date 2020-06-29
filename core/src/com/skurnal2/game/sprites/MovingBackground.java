package com.skurnal2.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class MovingBackground {
    private Texture movingBackground, movingBackground2, movingBackground3;
    private Vector2 backgroundPosition;

    public static int x = 0;

    public MovingBackground(float y) {
        movingBackground= new Texture("road_1.png");
        movingBackground2 = new Texture("road_2.png");
        movingBackground3 = new Texture("road_3.png");
        backgroundPosition = new Vector2(0, y);
    }

    public Texture getMovingBackground(float deltaTime) {
        if(deltaTime < 20)
        {
            return movingBackground;
        }
        else if(deltaTime < 40 && deltaTime >= 20)
        {
            return movingBackground2;
        }
        else if(deltaTime < 60 && deltaTime >= 40)
        {
            return movingBackground3;
        }
        else
        {
            return movingBackground;
        }
    }

    public void update() {
        backgroundPosition.add(0, -4 );

    }

    public Vector2 getBackgroundPosition() {
        return backgroundPosition;
    }

    public void reposition(float y) {
        backgroundPosition.set(0, y);

    }

}
