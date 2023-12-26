package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SimplePlayer extends Actor {
    private float radius;
    private Color currentColor = Color.BLUE;

    public SimplePlayer(float x, float y, float radius) {
        this.radius = radius;
        setBounds(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(currentColor);
        shapeRenderer.circle(getX() + radius, getY() + radius, radius);
        shapeRenderer.end();
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public float getCenterX() {
        return this.getX() + radius/2;
    }

    public float getCenterY() {
        return this.getY() + radius/2;
    }
}
