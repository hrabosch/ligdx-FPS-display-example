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

public class CustomNoiseGenerator implements Screen {

    private ScreenManager screenManager;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private Stage stage;
    private Random random = new Random();

    private int[][] mapArray;

    private final int MAP_DIMENSION_X = 75;
    private final int MAP_DIMENSION_Y = 50;

    private HashMap<Integer, TextureRegion> mapTextures = new HashMap<>(4);


    public CustomNoiseGenerator(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.texture = new Texture(Gdx.files.internal("map/aaa_map_background.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture, 16,16);
        mapTextures.put(0, tmp[0][0]); // Grass
        mapTextures.put(1, tmp[0][1]); // Vomits
        mapTextures.put(2, tmp[0][2]); // Water
        mapTextures.put(3, tmp[0][3]); // Stone
        mapArray = generate2Darray(MAP_DIMENSION_X,MAP_DIMENSION_Y, 2331);
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
        for (int x = 0; x < MAP_DIMENSION_X; x++) {
            for (int y = 0; y < MAP_DIMENSION_Y; y++) {
                    System.out.println(mapArray[x][y]);

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
            mapArray = generate2Darray(MAP_DIMENSION_X, MAP_DIMENSION_Y, 0);
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

    private int[][] generate2Darray(int x, int y, int randomSeedNumber) {
        int[][] mapArray = new int[x][y];
        for (int xi = 0 ; xi < x ; xi++) {
            for (int yi = 0; yi < y; yi++) {
                int prevValueLeft = xi > 0 ? mapArray[xi-1][yi] : 1;
                int prevValueTop = yi > 0 ? mapArray[xi][yi-1] : 1;
                int currentValue = random.nextInt(0, 3);
                float randomized = currentValue - prevValueLeft/random.nextFloat(0, randomSeedNumber) + prevValueTop/random.nextFloat(0, randomSeedNumber);
                int randomizedIntRaw = Math.abs((int) randomized);
                while (randomizedIntRaw>3) {
                    randomizedIntRaw = randomizedIntRaw-random.nextInt(0,randomizedIntRaw);
                }
                mapArray[xi][yi] = randomizedIntRaw;
            }
        }

        return mapArray;
    }
}
