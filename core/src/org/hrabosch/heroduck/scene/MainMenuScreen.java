package org.hrabosch.heroduck.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private ScreenManager screenManager;
    private TextButton startBtn,exitBtn, startSecBtn, startPerlinNoiseGen;

    public MainMenuScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void show() {
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage(new ScreenViewport());

        this.startBtn = new TextButton("Start", skin, "default");
        startBtn.setWidth(300);
        startBtn.setHeight(50);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.showScreen(ScreenEnum.MAIN_GAME);
            }
        });

        this.startSecBtn = new TextButton("Start #2", skin, "default");
        startSecBtn.setWidth(300);
        startSecBtn.setHeight(50);
        startSecBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.showScreen(ScreenEnum.NOT_THAT_MAIN_GAME);
            }
        });

        this.startPerlinNoiseGen = new TextButton("Perlin Noise Gen", skin, "default");
        startPerlinNoiseGen.setWidth(300);
        startPerlinNoiseGen.setHeight(50);
        startPerlinNoiseGen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.showScreen(ScreenEnum.PERLIN_NOISE_GENERATION);
            }
        });

        this.exitBtn = new TextButton("Quit", skin, "default");
        exitBtn.setWidth(300);
        exitBtn.setHeight(50);
        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();

        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.padTop(30);
        table.add(startBtn).padBottom(30);
        table.row();
        table.padTop(30);
        table.add(startSecBtn).padBottom(30);
        table.row();
        table.padTop(30);
        table.add(startPerlinNoiseGen).padBottom(30);
        table.row();
        table.add(exitBtn);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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

    }
}
