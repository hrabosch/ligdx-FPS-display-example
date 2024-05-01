package org.hrabosch.heroduck.scene;

import java.util.HashMap;
import java.util.Random;

import org.hrabosch.heroduck.OrthoCamController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SecondMainGame implements Screen {

    private ScreenManager screenManager;
    private Stage stage;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;
    private OrthoCamController cameraController;
    private AssetManager assetManager;
    private Texture tiles;
    private Texture texture;
    private BitmapFont font;
    private SpriteBatch batch;

    private static final int MAP_DIMENSION = 50;

    public SecondMainGame(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.update();

        cameraController = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(cameraController);

        font = new BitmapFont();
        batch = new SpriteBatch();

        {
            //tiles = new Texture(Gdx.files.internal("source/map_bg.png"));
            tiles = new Texture(Gdx.files.internal("source/ice_berg.png"));
            TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
            map = new TiledMap();
            MapLayers layers = map.getLayers();
                TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, 32, 32);
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                        if (x == 0 && y == 0) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[2][0]));
                        } else if (x == 0 && y > 0 && y < 9) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[1][0]));
                        } else if (x == 0 && y == 9 ) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
                        } else if (x > 0 && x < 9 && y == 0) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[2][1]));
                        } else if (x == 9 && y == 0 ) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[2][2]));
                        } else if (x == 9 && y > 0 && y < 9) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[1][2]));
                        } else if (x > 0 && x < 9 && y == 9) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][1]));
                        } else if (x == 9 && y == 9) {
                            cell.setTile(new StaticTiledMapTile(splitTiles[0][2]));
                        } else {
                            cell.setTile(new StaticTiledMapTile(splitTiles[1][1]));
                        }
                        layer.setCell(x, y, cell);
                    }
                }
                layers.add(layer);
        }

        renderer = new OrthogonalTiledMapRenderer(map);
//
//        stage = new Stage(new ScreenViewport());
//        this.spriteBatch = new SpriteBatch();
//        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float v) {
        ScreenUtils.clear(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
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
