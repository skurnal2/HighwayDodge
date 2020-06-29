package com.skurnal2.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skurnal2.game.HighwayDodge;

public class MenuState extends State {
    private Texture logo;
    private Texture background;
    private Texture playBtn;

    public Music backMusic; //for bigger sound files

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, HighwayDodge.WIDTH/2, HighwayDodge.HEIGHT/2);
        logo = new Texture("logo.png");
        background = new Texture("road_1.png");
        playBtn= new Texture("play_button.png");

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("back_music.mp3"));
        backMusic.setLooping(true);
        backMusic.setVolume(0.1f);
        backMusic.play();
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
        sb.draw(background, 0,0, 240, 400);
        sb.draw(playBtn, cam.position.x-100, (cam.position.y-(playBtn.getHeight()/2))-75, 200, 140);
        sb.draw(logo, cam.position.x-100, cam.position.y+50, 200, 110);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        backMusic.dispose();
    }
}
