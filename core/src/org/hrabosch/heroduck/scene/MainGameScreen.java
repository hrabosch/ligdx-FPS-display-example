package org.hrabosch.heroduck.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.hrabosch.heroduck.actor.Enemy;
import org.hrabosch.heroduck.actor.SimplePlayer;

public class MainGameScreen implements Screen {
    private Stage stage;
    private SimplePlayer simplePlayer;
    private OrthographicCamera hudCamera;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    private final ScreenManager screenManager;
    private float mouseX, mouseY;

    float speed = 500f;

    private Enemy enemy;
    public MainGameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        spriteBatch = new SpriteBatch();

        simplePlayer = new SimplePlayer(100, 100, 30);
        enemy = new Enemy(Gdx.graphics.getWidth() + 100, Gdx.graphics.getHeight() + 100, 5);
        stage.addActor(enemy);
        stage.addActor(simplePlayer);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        handleEnemyMove();
        handleCollision(
                simplePlayer.getCenterX(),
                enemy.getX(),
                simplePlayer.getCenterY(),
                enemy.getY()
        );
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.begin();
        float textLinePosition = hudCamera.viewportHeight;
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Player X=" + simplePlayer.getX(), 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Player Y=" + simplePlayer.getY(), 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Mouse X=" + mouseX, 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Mouse Y=" + mouseY, 0, textLinePosition);
        spriteBatch.end();
    }

    private void handleCollision(double x1, double x2, double y1, double y2) {
        // 2D: d = √[(x2 − x1)2 + (y2 − y1)2]
        // 3D: d = √[(x2 − x1)2 + (y2 − y1)2 + (z2 − z1)2]
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        if (distance < 100) {
            simplePlayer.setCurrentColor(Color.RED);
        } else {
            simplePlayer.setCurrentColor(Color.WHITE);
        }
    }

    private void handleEnemyMove() {
        float enemySpeed = speed/4;
        float xDiff = simplePlayer.getCenterX() - enemy.getX();
        float yDiff = simplePlayer.getCenterY() - enemy.getY();
        if (xDiff < 0) {
            enemy.moveBy(-enemySpeed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (xDiff > 0) {
            enemy.moveBy(enemySpeed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (yDiff < 0) {
            enemy.moveBy(0, -enemySpeed * Gdx.graphics.getDeltaTime());
        }
        if (yDiff > 0) {
            enemy.moveBy(0, enemySpeed * Gdx.graphics.getDeltaTime());
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            simplePlayer.moveBy(-speed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            simplePlayer.moveBy(speed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            simplePlayer.moveBy(0, speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            simplePlayer.moveBy(0, -speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            screenManager.showScreen(ScreenEnum.MAIN_MENU);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            mouseX = Gdx.input.getX();
            mouseY = Gdx.app.getGraphics().getHeight() - Gdx.input.getY();
            float diffX = mouseX - simplePlayer.getX();
            float diffY = simplePlayer.getY() - mouseY;

            if (diffX > 0)
                simplePlayer.moveBy(speed * Gdx.graphics.getDeltaTime(), 0);
            if (diffX < 0)
                simplePlayer.moveBy(-speed * Gdx.graphics.getDeltaTime(), 0);
            if (diffY > 0)
                simplePlayer.moveBy(0, -speed * Gdx.graphics.getDeltaTime());
            if (diffY < 0)
                simplePlayer.moveBy(0, speed * Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
