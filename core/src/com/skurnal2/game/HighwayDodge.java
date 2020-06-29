package com.skurnal2.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skurnal2.game.states.GameStateManager;
import com.skurnal2.game.states.MenuState;

public class HighwayDodge extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Highway Dodge";
	private GameStateManager gsm;
	private SpriteBatch batch;

	public static Preferences prefs;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));

		prefs = Gdx.app.getPreferences("scores");
	}

	@Override
	public void render () {
		//Clear Screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Using Delta Time
		gsm.update(Gdx.graphics.getRawDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
