package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {

    private float radius;

    public Enemy(float x, float y, float radius) {
        this.radius = radius;
        setBounds(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1, 1, 0, 1); // Red color (RGBA)
        shapeRenderer.circle(getX() + radius, getY() + radius, radius);
        shapeRenderer.end();

        batch.setColor(Color.WHITE);
    }
}
