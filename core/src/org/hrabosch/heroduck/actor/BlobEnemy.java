package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlobEnemy extends Actor {

    private static final int FRAME_COLS = 4, FRAME_ROWS = 2;
    private float deltaX, deltaY;

    Texture texture;
    Animation<TextureRegion> animationLeft, animationRight;
    float animationTime;

    TextureRegion currentFrame;

    AnimatedPlayer player;

    private HpBar hpBar;
    private float hp = 100;

    public BlobEnemy(float x, float y, AnimatedPlayer player) {
        this.hpBar = new HpBar(hp);
        this.player = player;
        texture = new Texture(Gdx.files.internal("player/enemy.png"));

        this.deltaX = (texture.getWidth()/FRAME_COLS)/2;
        this.deltaY = (texture.getHeight()/FRAME_ROWS)/2;

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);


        TextureRegion[] leftMovement = new TextureRegion[FRAME_COLS];
        TextureRegion[] rightMovement = new TextureRegion[FRAME_COLS];
        int index = 0;
            for (int j = 0; j < FRAME_COLS; j++) {
                leftMovement[index] = tmp[0][j];
                rightMovement[index++] = tmp[1][j];
        }

        animationLeft = new Animation(0.05f, leftMovement);
        animationRight = new Animation(0.05f, rightMovement);

        animationTime = 0f;

        setBounds(x, y, deltaX*2, 2 * deltaY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(currentFrame,this.getX(),this.getY());
    }

    private boolean isLeft() {
        return player.getCenterX() > this.getCenterX();
    }

    @Override
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        animationTime += deltaTime;
        currentFrame = isLeft() ? animationLeft.getKeyFrame(animationTime, true) : animationRight.getKeyFrame(animationTime, true);
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
}
