package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedPlayer extends Actor {

    private static final int FRAME_COLS = 2, FRAME_ROWS = 5;
    private static final float ANIMATION_FRAME = 0.5f;
    private final float deltaX;
    private final float deltaY;
    Texture texture;
    Animation<TextureRegion> animationIdle, animationLeft, animationRight, animationUp, animationDown;
    float animationTime;
    TextureRegion currentFrame;
    private PlayerMovementStateEnum playerMovementState = PlayerMovementStateEnum.IDLE;

    private HpBar hpBar;
    private float hp = 100;

    public AnimatedPlayer(float x, float y) {
        this.hpBar = new HpBar(hp);
        texture = new Texture(Gdx.files.internal("player/player.png"));

        this.deltaX = (texture.getWidth() / FRAME_COLS) / 2;
        this.deltaY = (texture.getHeight() / FRAME_ROWS) / 2;

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);


        TextureRegion[] idleFrames = new TextureRegion[FRAME_COLS];
        TextureRegion[] leftFrames = new TextureRegion[FRAME_COLS];
        TextureRegion[] rightFrames = new TextureRegion[FRAME_COLS];
        TextureRegion[] upFrames = new TextureRegion[FRAME_COLS];
        TextureRegion[] downFrames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int j = 0; j < FRAME_COLS; j++) {
            idleFrames[index] = tmp[0][j];
            downFrames[index] = tmp[1][j];
            upFrames[index] = tmp[2][j];
            rightFrames[index] = tmp[3][j];
            leftFrames[index++] = tmp[4][j];
        }

        animationIdle = new Animation(ANIMATION_FRAME, idleFrames);
        animationDown = new Animation<>(ANIMATION_FRAME, downFrames);
        animationUp = new Animation<>(ANIMATION_FRAME, upFrames);
        animationLeft = new Animation<>(ANIMATION_FRAME, leftFrames);
        animationRight = new Animation<>(ANIMATION_FRAME, rightFrames);

        animationTime = 0f;

        setBounds(x, y, deltaX * 2, 2 * deltaY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)
        ) {
            switch (playerMovementState) {
                case UP -> currentFrame = animationUp.getKeyFrame(animationTime, true);
                case DOWN -> currentFrame = animationDown.getKeyFrame(animationTime, true);
                case LEFT -> currentFrame = animationLeft.getKeyFrame(animationTime, true);
                case RIGHT -> currentFrame = animationRight.getKeyFrame(animationTime, true);
            }
        } else {
            currentFrame = animationIdle.getKeyFrame(animationTime, true);
        }
        batch.draw(currentFrame, this.getX(), this.getY());
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        animationTime += deltaTime;
        if (getStage() != null && hpBar.getStage() == null) {
            getStage().addActor(hpBar);
        }
        hpBar.setPosition(getX(), getY() - 5);
        hpBar.setCurrentHp(hp);
    }

    public float getCenterX() {
        return this.getX() + deltaX;
    }

    public float getCenterY() {
        return this.getY() + deltaY;
    }

    public void setPlayerMovementState(PlayerMovementStateEnum playerMovementState) {
        this.playerMovementState = playerMovementState;
    }

    public void takeDamage(double distance) {
        this.hp = --hp;
    }
}
