package org.hrabosch.heroduck.scene;

import com.badlogic.gdx.Game;

public class ScreenManager {
    private final Game gameInstance;

    public ScreenManager(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void showScreen(ScreenEnum screenEnum) {
        switch (screenEnum) {
            case MAIN_MENU -> gameInstance.setScreen(new MainMenuScreen(this));
            case MAIN_GAME -> gameInstance.setScreen(new MainGameScreen(this));
            case NOT_THAT_MAIN_GAME -> gameInstance.setScreen(new SecondMainGame(this));
            case PERLIN_NOISE_GENERATION -> gameInstance.setScreen(new CustomNoiseGenerator(this));
        }
    }
}
