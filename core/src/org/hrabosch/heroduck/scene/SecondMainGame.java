package org.hrabosch.heroduck.scene;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.hrabosch.heroduck.elements.DirtBox;
import org.hrabosch.heroduck.isometric.IsometricInputProcessor;

import java.security.SecureRandom;
import java.util.Random;

public class SecondMainGame implements Screen {
    private ScreenManager screenManager;
//    private PerspectiveCamera camera;
    private OrthographicCamera camera;
    private ModelBatch modelBatch;
    private Environment environment;
    private Array<ModelInstance> mapTiles;
    private ModelBuilder modelBuilder;

    private IsometricInputProcessor inputProcessor;

    public SecondMainGame(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void show() {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

//        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.position.set(10f, 10f, 10f); // Set the camera above and to the side
//        camera.lookAt(0f, 0f, 0f);  // Point camera to center of the map
//        camera.near = 1f;
//        camera.far = 300f;
//        camera.update();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.setToOrtho(false, 20, 20);
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0f, 0f, 0f);
        camera.update();

        mapTiles = new Array<>();
        modelBuilder = new ModelBuilder();

//        Material whiteMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE));
//        Model tileModel = modelBuilder.createBox(1f, 1f, 1f, whiteMaterial,
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


        Random random = new SecureRandom();
        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                DirtBox box = new DirtBox();
                ModelInstance tileInstance = new ModelInstance(box.getModel());
                tileInstance.transform.setToTranslation(x, random.nextFloat(0f,1f), z);
                mapTiles.add(tileInstance);
            }
        }

        inputProcessor = new IsometricInputProcessor(camera, mapTiles);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);
        for (ModelInstance tile : mapTiles) {
            modelBatch.render(tile, environment);
        }
        modelBatch.end();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 32f;
        camera.viewportHeight = height / 32f;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
