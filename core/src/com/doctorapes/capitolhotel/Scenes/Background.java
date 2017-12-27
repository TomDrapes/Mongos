package com.doctorapes.capitolhotel.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doctorapes.capitolhotel.MongosX;

import box2dLight.Light;
import box2dLight.RayHandler;

/**
 * Created by Apez on 8/03/2017.
 */

public class Background implements Disposable {
    public Stage stage;
    //Create new camera and new viewport for Hud so that when game world moves the hud stays the same.
    private Viewport viewport;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthographicCamera gamecam2;
    //private OrthogonalTiledMapRenderer renderer;
    private Image image;

    private Texture texture;
    private RayHandler rayHandler;
    private Light light;


    public Background(SpriteBatch sb, World world){

        gamecam2 = new OrthographicCamera();

        viewport = new FitViewport(MongosX.V_WIDTH, MongosX.V_HEIGHT, gamecam2);




        stage = new Stage(viewport, sb);
        //mapLoader = new TmxMapLoader();
       // map = mapLoader.load("testbgxa2.tmx");
        //renderer = new OrthogonalTiledMapRenderer(map, 1 / MongosX.PPM);
        texture = new Texture("spacebg4.png");

        image = new Image(texture);


        gamecam2.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        //rayHandler = new RayHandler(world);

        //light = new box2dLight.PointLight(rayHandler, 1000, Color.FIREBRICK, 20f, 8f, 5f);
        //light.setXray(true);


        stage.addActor(image);

    }
    public void update(float dt){

    }


    @Override
    public void dispose() {
        stage.dispose();
        //map.dispose();
        texture.dispose();

    }
}
