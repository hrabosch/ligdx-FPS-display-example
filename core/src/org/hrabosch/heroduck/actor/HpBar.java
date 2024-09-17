package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HpBar extends Actor {

    private ShapeRenderer renderer;
    private float maxHp;
    private float currentHp;

    public HpBar(float maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        renderer = new ShapeRenderer();
        setWidth(35);
        setHeight(4);
    }

    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GRAY);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), (currentHp / maxHp) * getWidth(), getHeight());
        renderer.end();
        batch.begin();
    }
}
