package org.hrabosch.heroduck;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.hrabosch.heroduck.HeroDuckGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1900,1200);
		config.setForegroundFPS(60);
		config.setTitle("HeroDuck");
		new Lwjgl3Application(new HeroDuckGame(), config);
	}
}
