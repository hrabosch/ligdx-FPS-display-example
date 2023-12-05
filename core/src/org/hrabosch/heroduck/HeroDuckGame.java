package org.hrabosch.heroduck;

import com.badlogic.gdx.Game;
import org.hrabosch.heroduck.scene.ScreenEnum;
import org.hrabosch.heroduck.scene.ScreenManager;

public class HeroDuckGame extends Game {
	private ScreenManager screenManager;
	@Override
	public void create () {
		screenManager = new ScreenManager(this);
		screenManager.showScreen(ScreenEnum.MAIN_MENU);
	}
}
