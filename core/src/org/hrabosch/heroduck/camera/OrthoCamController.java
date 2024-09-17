package org.hrabosch.heroduck.camera;

import org.hrabosch.heroduck.actor.PlayerMovementStateEnum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public OrthoCamController (OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override public boolean scrolled(float amountX, float amountY) {
        if ((amountX > 0 || amountY > 0) && camera.zoom < 5.0)
            camera.zoom += 0.05;
        if ((amountX > 0 || amountY > 0) && camera.zoom > 0.5)
            camera.zoom -= 0.05;
        return super.scrolled(amountX, amountY);
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }
}
