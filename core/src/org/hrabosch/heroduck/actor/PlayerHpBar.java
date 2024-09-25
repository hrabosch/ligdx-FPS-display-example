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

        float radius = getWidth() / 2;
        float centerX = getX() + radius;
        float centerY = getY() + radius;

        float percent = (currentHp/maxHp)*100;

        System.out.println(percent);


        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i <= 360 * percent; i++) {
            float angle = (float) Math.toRadians(i);
            shapeRenderer.triangle(
                    centerX, centerY,
                    centerX + radius * (float)Math.cos(angle),
                    centerY + radius * (float)Math.sin(angle),
                    centerX + radius * (float)Math.cos(angle + Math.toRadians(1)),
                    centerY + radius * (float)Math.sin(angle + Math.toRadians(1))
            );
        }

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        batch.begin();
        String hpText = (int) currentHp + " / " + (int) maxHp;
        layout.setText(font, hpText);
        font.draw(batch, layout, centerX - (layout.width / 2), getY() - 10);
    }
}
