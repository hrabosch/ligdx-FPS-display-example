package org.hrabosch.heroduck.scene;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SecondMainGame implements Screen {

    private ScreenManager screenManager;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private Stage stage;
    private Random random = new Random();

    private int[][] mapArray = {};

    private HashMap<Integer, TextureRegion> mapTextures = new HashMap<>(4);

    private static final int MAP_DIMENSION = 50;

    public SecondMainGame(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.mapArray = generateMapArray();
        this.texture = new Texture(Gdx.files.internal("map/aaa_map_background.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture, 16,16);
        mapTextures.put(0, tmp[0][0]);
        mapTextures.put(1, tmp[0][1]);
        mapTextures.put(2, tmp[0][2]);
        mapTextures.put(3, tmp[0][3]);
    }

    private int[][] generateMapArray() {
        int[][] array = new int[MAP_DIMENSION][MAP_DIMENSION];
        for (int x = 0; x < MAP_DIMENSION; x++) {
            for (int y = 0; y < MAP_DIMENSION; y++) {
                array[x][y] = random.nextInt(4);
            }
        }
        return array;
    }

    @Override public void show() {
        stage = new Stage(new ScreenViewport());
        this.spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float v) {
        handleInput();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        spriteBatch.begin();
        for (int x = 0; x < MAP_DIMENSION; x++) {
            for (int y = 0; y < MAP_DIMENSION; y++) {
                spriteBatch.draw(mapTextures.get(mapArray[x][y]), x*16, y*16);
            }
        }
        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            screenManager.showScreen(ScreenEnum.MAIN_MENU);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mapArray = generateMapArray();
        }
    }

    @Override public void resize(int i, int i1) {

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void hide() {

    }

    @Override public void dispose() {

    }
}
