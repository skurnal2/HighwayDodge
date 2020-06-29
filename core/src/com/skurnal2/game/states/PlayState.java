package com.skurnal2.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.skurnal2.game.HighwayDodge;
import com.skurnal2.game.sprites.OtherCar;
import com.skurnal2.game.sprites.MovingBackground;
import com.skurnal2.game.sprites.MyCar;

public class PlayState extends State {
    private static final int CAR_SPACING = 70;
    private static final int CAR_COUNT = 100;
    public static final int CAR_HEIGHT = 76;
    public static final int CAR_WIDTH = 32;


    public Music roadEffect; //for bigger sound files
    public Music drivingEffect;

    public Sound crashSound; //for smaller sound files

    private MyCar myCar;

    private Array<MovingBackground> movingBackgrounds;

    private Array<OtherCar> enemyCars;

    BitmapFont counterFont;
    private float deltaTime = 0;


    protected PlayState(GameStateManager gsm) {
        super(gsm);
        myCar = new MyCar(50, 70);
        cam.setToOrtho(false, HighwayDodge.WIDTH/2, HighwayDodge.HEIGHT/2);

        //Traffic Sounds
        roadEffect = Gdx.audio.newMusic(Gdx.files.internal("road_effect.wav"));
        roadEffect.setLooping(true);
        roadEffect.setVolume(0.2f);
        roadEffect.play();

        //Engine Sound
        drivingEffect = Gdx.audio.newMusic(Gdx.files.internal("driving_effect.wav"));
        drivingEffect.setLooping(true);
        drivingEffect.setVolume(0.1f);
        drivingEffect.play();

        //Crash Sound initialization
        crashSound = Gdx.audio.newSound(Gdx.files.internal("car_crash.wav"));

        enemyCars = new Array<OtherCar>();
        for(int i = 1; i <= CAR_COUNT; i++)
        {
            enemyCars.add(new OtherCar(i * (CAR_SPACING + CAR_HEIGHT)));//32 is height of car
        }

        movingBackgrounds = new Array<MovingBackground>();
        movingBackgrounds.add(new MovingBackground( 0));
        movingBackgrounds.add(new MovingBackground( 400));

        counterFont = new BitmapFont();


    }

    @Override
    protected void handleInput() {
        float accX = Gdx.input.getAccelerometerX();
        if(accX > 1 && accX <= 2)
        {
            myCar.moveRight();
        }
        else if(accX > 2)
        {
            myCar.moveRightFast();
        }
        else if(accX < -1 && accX >= -2) {
            myCar.moveLeft();
        }
        else if(accX < -2) {
            myCar.moveLeftFast();
        }
        else
        {
            myCar.moveStraight();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        myCar.update(dt);
        cam.position.y = myCar.getPosition().y + 150;

        for(OtherCar otherCar : enemyCars) {
            if(cam.position.y - (cam.viewportHeight/2) > otherCar.getOtherCarPosition().y + CAR_HEIGHT*2) {
                otherCar.reposition((otherCar.getOtherCarPosition().y + ((CAR_HEIGHT + CAR_SPACING) * CAR_COUNT)));
            }
        }

        for(MovingBackground movingBackground : movingBackgrounds) {
            if(cam.position.y - (cam.viewportHeight/2) > movingBackground.getBackgroundPosition().y + 400) {
                movingBackground.reposition((movingBackground.getBackgroundPosition().y + 400 * 2));
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); //tells where the viewport is, so it only draws that stuff
        sb.begin();

        for(MovingBackground movingBackground : movingBackgrounds) {
            sb.draw(movingBackground.getMovingBackground(deltaTime), movingBackground.getBackgroundPosition().x, movingBackground.getBackgroundPosition().y, 240, 400);
            movingBackground.update();
        }

        sb.draw(myCar.getMyCar(), myCar.getPosition().x, myCar.getPosition().y, CAR_WIDTH, CAR_HEIGHT);

        //Timer
        deltaTime += Gdx.graphics.getDeltaTime();
        counterFont.draw(sb, String.format("%.2f", deltaTime), myCar.getPosition().x, myCar.getPosition().y-15);

        for(OtherCar otherCar : enemyCars) {
            sb.draw(otherCar.getOtherCar(), otherCar.getOtherCarPosition().x, otherCar.getOtherCarPosition().y+5, CAR_WIDTH, CAR_HEIGHT);
            if(otherCar.getOtherCarPosition().x + (CAR_WIDTH-5) > myCar.getPosition().x &&
                    otherCar.getOtherCarPosition().x < myCar.getPosition().x + CAR_WIDTH &&
                    otherCar.getOtherCarPosition().y + (CAR_WIDTH-5) > myCar.getPosition().y &&
                    otherCar.getOtherCarPosition().y + (CAR_WIDTH-5) < myCar.getPosition().y + CAR_HEIGHT) {
                crashSound.play(0.2f);
                roadEffect.stop();
                drivingEffect.stop();
                gsm.set(new CrashState(gsm));}
        }

        HighwayDodge.prefs.putString("CurrentScore", String.format("%.2f", deltaTime));
        HighwayDodge.prefs.flush();

        sb.end();
    }

    @Override
    public void dispose() {
        roadEffect.dispose();
        crashSound.dispose();
        drivingEffect.dispose();
    }
}
