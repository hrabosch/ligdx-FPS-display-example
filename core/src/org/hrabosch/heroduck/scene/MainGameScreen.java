package org.hrabosch.heroduck.scene;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.hrabosch.heroduck.actor.*;

public class MainGameScreen implements Screen {
    private Stage stage;
    private AnimatedPlayer animatedPlayer;
    private OrthographicCamera hudCamera;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private final ScreenManager screenManager;
    private float mouseX, mouseY;
    private final static Integer TIMER_SPAWN_ENEMY_INIT=10,TIMER_SPAWN_ENEMY_DELAY=20;
    float speed = 200f;
    private List<BlobEnemy> enemies = new ArrayList<>();
    private BlobEnemy blobEnemy;


    public MainGameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        Timer timer=new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                spawnEnemy();
            }
        },TIMER_SPAWN_ENEMY_INIT,TIMER_SPAWN_ENEMY_DELAY);
    }

    private void spawnEnemy() {
        // TODO Randomize this
        BlobEnemy generatedEnemy = new BlobEnemy(Gdx.graphics.getWidth()+5,Gdx.graphics.getHeight()+5, animatedPlayer);
        enemies.add(generatedEnemy);
        stage.addActor(generatedEnemy);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        spriteBatch = new SpriteBatch();

        //simplePlayer = new SimplePlayer(100, 100, 30);
        animatedPlayer = new AnimatedPlayer(50,50);
        //blobEnemy = new BlobEnemy(1000,1000, animatedPlayer);
        //enemy = new Enemy(Gdx.graphics.getWidth() + 100, Gdx.graphics.getHeight() + 100, 5);
//        stage.addActor(enemy);
        //stage.addActor(simplePlayer);
        stage.addActor(animatedPlayer);
        //stage.addActor(blobEnemy);
        Gdx.input.setInputProcessor(stage);
        //Gdx.input.setInputProcessor(new AnimatedPlayerInputProcessor(animatedPlayer, speed));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        enemies.forEach(this::handleEnemyMove);
//        handleCollision(
//                //simplePlayer.getCenterX(),
//                animatedPlayer.getCenterX(),
//                enemy.getX(),
//                //simplePlayer.getCenterY(),
//                animatedPlayer.getCenterY(),
//                enemy.getY()
//        );
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.begin();
        float textLinePosition = hudCamera.viewportHeight;
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Player X=" + animatedPlayer.getX(), 0, textLinePosition);
        textLinePosition -= font.getLineHeight();
        font.draw(spriteBatch, "Player Y=" + animatedPlayer.getY(), 0, textLinePosition);
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
            //simplePlayer.setCurrentColor(Color.RED);
        } else {
            //simplePlayer.setCurrentColor(Color.WHITE);
        }
    }

    private void handleEnemyMove(BlobEnemy enemy) {
        float enemySpeed = speed/4;
        float xDiff = animatedPlayer.getCenterX() - enemy.getCenterX();
        float yDiff = animatedPlayer.getCenterY() - enemy.getCenterY();
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
            animatedPlayer.setPlayerMovementState(PlayerMovementStateEnum.LEFT);
            animatedPlayer.moveBy(-speed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            animatedPlayer.setPlayerMovementState(PlayerMovementStateEnum.RIGHT);
            animatedPlayer.moveBy(speed * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            animatedPlayer.setPlayerMovementState(PlayerMovementStateEnum.UP);
            animatedPlayer.moveBy(0, speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            animatedPlayer.setPlayerMovementState(PlayerMovementStateEnum.DOWN);
            animatedPlayer.moveBy(0, -speed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            screenManager.showScreen(ScreenEnum.MAIN_MENU);
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
