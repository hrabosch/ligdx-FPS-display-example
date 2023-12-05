# Static position text within game
![FPS_display.gif](docs%2FFPS_display.gif)

Player graphic has own Viewport where we are drawing stage and text is in batch for separated Camera:
```java
@Override
public void show() {
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
    font = new BitmapFont(Gdx.files.internal("font.fnt"));

    spriteBatch = new SpriteBatch();

    simplePlayer = new SimplePlayer(100, 100, 30);
    stage.addActor(simplePlayer);
    Gdx.input.setInputProcessor(stage);
}
```

In rende method, we are updating them as two separated views:
```java
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
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
```