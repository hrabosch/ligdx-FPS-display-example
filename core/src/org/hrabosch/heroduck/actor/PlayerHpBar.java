package org.hrabosch.heroduck.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerHpBar extends Actor  {
    private ShapeRenderer shapeRenderer;
    private float maxHp;
    private float currentHp;
    private BitmapFont font;
    private GlyphLayout layout;

    public PlayerHpBar(float maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        layout = new GlyphLayout();

        setWidth(100);
        setHeight(100);
    }

    public void setCurrentHp(float currentHp) {
        this.currentHp = currentHp;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float segments = 50;
        float radius = getWidth() / 2;
        float centerX = getX() + radius;
        float centerY = getY() + radius;

        for (int i = 0; i < segments; i++) {
            float percentage = (float) i / segments;
            float hpPercentage = currentHp / maxHp;

            Color color = new Color(1 - hpPercentage, hpPercentage, 0, 1);
            shapeRenderer.setColor(color);

            float y1 = centerY - radius + (radius * 2 * percentage);
            float y2 = centerY - radius + (radius * 2 * (percentage + 1f / segments));

            shapeRenderer.rect(centerX - radius, y1, radius * 2, y2 - y1);
        }

        shapeRenderer.end();

        batch.begin();
        String hpText = (int) currentHp + " / " + (int) maxHp;
        layout.setText(font, hpText);
        font.draw(batch, layout, centerX - (layout.width / 2), getY() - 10);
    }
}
