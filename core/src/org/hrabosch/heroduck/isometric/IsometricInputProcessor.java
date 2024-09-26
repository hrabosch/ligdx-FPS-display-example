package org.hrabosch.heroduck.isometric;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class IsometricInputProcessor implements InputProcessor {
    private OrthographicCamera camera;
    private Vector3 initialTouch;
    private boolean isDragging = false;
    public IsometricInputProcessor(OrthographicCamera camera, Array<ModelInstance> mapTiles) {
        this.camera = camera;
        initialTouch = new Vector3();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            initialTouch.set(screenX, screenY, 0);
            camera.unproject(initialTouch);
            isDragging = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isDragging = false;
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            Vector3 currentTouch = new Vector3(screenX, screenY, 0);
            camera.unproject(currentTouch);
            float deltaX = initialTouch.x - currentTouch.x;
            float deltaY = initialTouch.y - currentTouch.y;
            camera.position.add(deltaX, deltaY, 0);
            camera.update();
            initialTouch.set(screenX, screenY, 0);
            camera.unproject(initialTouch);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camera.rotateAround(new Vector3(5f, 0f, 5f), new Vector3(0, 1, 0), amountY * 10f);  // Rotate around the Y-axis
        } else {
            camera.zoom += amountY * 0.1f;
            camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 2f);
        }
        camera.update();
        return true;
    }
}
