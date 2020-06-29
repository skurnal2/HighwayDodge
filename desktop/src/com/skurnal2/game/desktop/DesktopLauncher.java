package com.skurnal2.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skurnal2.game.HighwayDodge;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = HighwayDodge.WIDTH;
		config.height = HighwayDodge.HEIGHT;
		config.title = HighwayDodge.TITLE;
		new LwjglApplication(new HighwayDodge(), config);
	}
}
