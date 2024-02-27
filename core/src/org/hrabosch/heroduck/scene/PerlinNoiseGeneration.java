package org.hrabosch.heroduck.scene;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PerlinNoiseGeneration implements Screen {

    private ScreenManager screenManager;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private Stage stage;
    private Random random = new Random();

    int p[] = new int [30];
    int permutation[] = new int[256];

    private HashMap<Integer, TextureRegion> mapTextures = new HashMap<>(4);

    private static final int MAP_DIMENSION = 50;

    public PerlinNoiseGeneration(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.texture = new Texture(Gdx.files.internal("map/aaa_map_background.png"));
        this.permutation = generatePermutation();
        TextureRegion[][] tmp = TextureRegion.split(texture, 16,16);
        mapTextures.put(0, tmp[0][0]);
        mapTextures.put(1, tmp[0][1]);
        mapTextures.put(2, tmp[0][2]);
        mapTextures.put(3, tmp[0][3]);
    }

    private int[] generatePermutation() {
        int[] tmp = new int[256];
        for (int i = 0; i < 256; i++) {
            tmp[i] = random.nextInt(256);
        }
        return tmp;
    }

    private int[][] generateMapArray() {
        int[][] array = new int[MAP_DIMENSION][MAP_DIMENSION];
        for (int x = 0; x < MAP_DIMENSION; x++) {
            for (int y = 0; y < MAP_DIMENSION; y++) {
                array[x][y] = random.nextInt(4);
            }
        }
        return array;
    }

    @Override public void show() {
        stage = new Stage(new ScreenViewport());
        this.spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float v) {
        handleInput();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        spriteBatch.begin();
        for (int x = 0; x < MAP_DIMENSION; x++) {
            for (int y = 0; y < MAP_DIMENSION; y++) {
                spriteBatch.draw(mapTextures.get(mapArray[x][y]), x*16, y*16);
            }
        }
        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            screenManager.showScreen(ScreenEnum.MAIN_MENU);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mapArray = generateMapArray();
        }
    }

    @Override public void resize(int i, int i1) {

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void hide() {

    }

    @Override public void dispose() {

    }


    private double noise ( double x , double y , double z ) {
        int X = (int) Math . floor ( x ) & 255 , // Fine unit cube that
            Y = (int) Math . floor ( y ) & 255 , // contains point
            Z = (int) Math . floor ( z ) & 255;
        x -= Math . floor ( x ) ; // Find relative x,y,z
        y -= Math . floor ( y ) ; // of point in cube
        z -= Math . floor ( z ) ;
        double u = fade ( x ) , // Compute fade curves
               v = fade ( y ) , // for each of x,y,z
               w = fade ( z ) ;
        int A = p [ X ]+ Y , AA = p [ A ]+ Z , AB = p [ A +1]+ Z ,// Hash coordinates of
            B = p [ X +1]+ Y , BA = p [ B ]+ Z , BB = p [ B +1]+ Z ;// the 8 cube corners
        // ... and add blended results from 8 corners of cube
        return lerp (w , lerp (v , lerp (u , grad ( p [ AA ] , x , y , z ) ,
                                grad ( p [ BA ] , x -1 , y , z ) ) ,
                        lerp (u , grad ( p [ AB ] , x , y -1 , z ) ,
                                grad ( p [ BB ] , x -1 , y -1 , z ) ) ) ,
                lerp (v , lerp (u , grad ( p [ AA +1] , x , y , z -1 ) ,
                                grad ( p [ BA +1] , x -1 , y , z -1 ) ) ,
                        lerp (u , grad ( p [ AB +1] , x , y -1 , z -1 ) ,
                                grad ( p [ BB +1] , x -1 , y -1 , z -1 ) ) ) ) ;
    }

    private double fade ( double t ) { return t * t * t *( t *( t *6 - 15) + 10) ; }
    private double lerp ( double t , double a , double b )
    { return a + t *( b - a ) ; }
    private double grad (int hash , double x , double y , double z ) {
        int h = hash & 15; // Convert low 4 bits of hash code
        double u = h <8 ? x : y , // into 12 gradient directions
                v = h <4 ? y : h ==12|| h ==14 ? x : z ;
        return (( h &1) == 0 ? u : -u ) + (( h &2) == 0 ? v : -v ) ;
    }
}
