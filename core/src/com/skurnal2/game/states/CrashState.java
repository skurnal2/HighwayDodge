package com.skurnal2.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.skurnal2.game.HighwayDodge;

public class CrashState extends State
{
    private Texture game_over_text;
    private Texture background;
    private Texture playBtn;

    public Music backMusic; //for bigger sound files

    private String currentScore;
    private String highScore;
    BitmapFont currentScoreFont, currentScoreFontHeading, highScoreFont, highScoreFontHeading;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameterHeading, parameterValue;

    public CrashState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, HighwayDodge.WIDTH/2, HighwayDodge.HEIGHT/2);
        game_over_text = new Texture("game_over.png");
        background = new Texture("road_crash.png");
        playBtn= new Texture("play_again_button.png");

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("back_music.mp3"));
        backMusic.setLooping(true);
        backMusic.setVolume(0.1f);
        backMusic.play();

        //Initializing parameters and generator
        generator  = new FreeTypeFontGenerator(Gdx.files.internal("GameFont.ttf"));
        parameterHeading = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterValue = new FreeTypeFontGenerator.FreeTypeFontParameter();

        //Parameter Formatting
        parameterHeading.size = 20;
        parameterValue.size = 23;
        parameterValue.color = Color.valueOf("f6ff00");
        parameterValue.borderColor = Color.valueOf("000000");
        parameterValue.borderWidth = 2;


        //Score Headings - Initializing Bitmap Font
        currentScoreFontHeading = generator.generateFont(parameterHeading);
        highScoreFontHeading = currentScoreFontHeading; //Headings have same formatting

        //Scores - Initializing Bitmap Font
        currentScoreFont = generator.generateFont(parameterValue);
        highScoreFont = currentScoreFont; //Scores have same formatting

        //disposing generator
        generator.dispose();

        //Getting Current and High Scores from prefs
        currentScore = HighwayDodge.prefs.getString("CurrentScore", "0");
        highScore = HighwayDodge.prefs.getString("HighScore", "0");
        if(highScore.equals("0") || Float.parseFloat(currentScore) > Float.parseFloat(highScore))
        {
            HighwayDodge.prefs.putString("HighScore", currentScore);
            highScore = currentScore;
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //Background Road
        sb.draw(background, 0,0, 240, 400);

        //Play again Button
        sb.draw(playBtn, 26, 45, 185, 80);

        //Game Over text
        sb.draw(game_over_text, cam.position.x-100, 270, 200, 120);

        //Current Score
        currentScoreFontHeading.draw(sb, "Current Time", 70, 260);
        currentScoreFont.draw(sb, currentScore + " seconds", 35, 235);

        //High Score
        currentScoreFontHeading.draw(sb, "High Score", 70, 200);
        currentScoreFont.draw(sb, highScore + " seconds", 35, 175);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        backMusic.dispose();


    }
}
