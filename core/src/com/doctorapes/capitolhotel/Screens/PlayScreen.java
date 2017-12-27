package com.doctorapes.capitolhotel.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doctorapes.capitolhotel.Scenes.Hud;
import com.doctorapes.capitolhotel.Sprites.Player2;
import com.doctorapes.capitolhotel.Tools.AdHandler;
import com.doctorapes.capitolhotel.Tools.B2WorldCreator;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Sprites.Cloud;
import com.doctorapes.capitolhotel.Sprites.Mongo;

import java.util.ArrayList;

import box2dLight.Light;
import box2dLight.RayHandler;

/**
 * Created by Apez on 15/12/2016.
 */

public class PlayScreen extends InputAdapter implements Screen {

    private AdHandler handler;
    private ArrayList<Mongo> mong;
    //reference to game, used to set screens
    private MongosX game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    public static Viewport gameport;

    private Hud hud;
    private com.doctorapes.capitolhotel.Scenes.Background background;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //Sprites

    private Player2 player;
    private Mongo mongo;
    private com.doctorapes.capitolhotel.Sprites.Princess princess;
    private com.doctorapes.capitolhotel.Scenes.PlayPause pausebutton;
    private com.doctorapes.capitolhotel.Scenes.MusicPlay mymusic;
    private com.doctorapes.capitolhotel.Sprites.Sling mysling;
    private Cloud cloud1, cloud2, cloud3, cloud4;


    private Vector2 startVelocity;
    private Vector2 startPoint;
    private float x, y, targetX, targetY;

    private boolean released;
    private boolean touched, dragged, paused, musicpaused, buttonjustpressed;

    private ShapeRenderer shapeRenderer, shapeRenderer2;

    private FPSLogger myLog;

    public static int mongoSchedule, gameoverSchedule;

    private Music music;

    private RayHandler rayHandler;
    private Light light;




    public PlayScreen(MongosX game, Boolean musiccontinue/*, AdHandler handler*/) {


        //FPS
        //myLog = new FPSLogger();

        //Adhandler
        //this.handler = handler;


        shapeRenderer = new ShapeRenderer();
        shapeRenderer2 = new ShapeRenderer();

        atlas = new TextureAtlas("mySpriteSheet5.txt");

        //bring in game class
        this.game = game;
        //create camera to follow player through cam world
        gamecam = new OrthographicCamera();

        //create FitViewport to maintain virtual aspect ratio
        gameport = new FitViewport(MongosX.V_WIDTH / MongosX.PPM, MongosX.V_HEIGHT / MongosX.PPM, gamecam);

        //create game HUD for scores/timer etc
        hud = new Hud(game.batch);
        background = new com.doctorapes.capitolhotel.Scenes.Background(game.batch, world);

        //Load map and setup map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("testbgxa4.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MongosX.PPM);

        //initially set our gamcam to be centered correctly at the start of map
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        //GRAVITY set in world (0, 0) means no x or y gravity
        world = new World(new Vector2(-3, -5), true);

        //Debug renderer shows box2d bodies NB- Causes lag, turn off when not testing
        b2dr = new Box2DDebugRenderer();

        //Ammo sprite
        //TODO refactor player class to be called ammmo
        player = new Player2(world, this);
        //Set no gravity on ammo
        player.b2body.setGravityScale(0);

        princess = new com.doctorapes.capitolhotel.Sprites.Princess(world, this);
        mysling = new com.doctorapes.capitolhotel.Sprites.Sling(world, this);
        mysling.setSize(0.20f, 0.20f);
        mysling.setOrigin(mysling.getWidth() / 2, mysling.getHeight() / 2);

        cloud1 = new Cloud(game.batch, 1);
        cloud2 = new Cloud(game.batch, 2);
        cloud3 = new Cloud(game.batch, 3);
        cloud4 = new Cloud(game.batch, 4);

        pausebutton = new com.doctorapes.capitolhotel.Scenes.PlayPause(world, this, atlas);
        mymusic = new com.doctorapes.capitolhotel.Scenes.MusicPlay(world, this, atlas);

        Gdx.input.setInputProcessor(this);

        //Start point vector is the midpoint between each side of the slingshot for calculating velocity vector
        startPoint = new Vector2(1.3575f, 1.9488f);
        touched = false;
        dragged = false;
        paused = false;

        //Ensure that if music was paused in previous game it remains muted when re-starting game
        musicpaused = musiccontinue;
        if (musicpaused) {
            mymusic.pauseMusic();
        }

        buttonjustpressed = false;

        mong = new ArrayList<Mongo>();

        world.setContactListener(new com.doctorapes.capitolhotel.Tools.WorldContactListener());

        music = MongosX.manager.get("sounds/MarimbaBoy.ogg", Music.class);
        music.setLooping(true);
        music.play();

        new B2WorldCreator(this);

       // rayHandler = new RayHandler(world);
       // rayHandler.setAmbientLight(0.0001f,0.0001f,0.0001f,1f);

        //TODO optimise box2dlight to run on android
       // light = new box2dLight.PointLight(rayHandler, 1000, Color.FIREBRICK, 8f, 8f, 3f);
        //Turn of shadows
        //light.setXray(true);
    }



    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt) {

        //NB- turn off input handler for android, only useful for testing on desktop
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 4)
            player.b2body.setLinearVelocity(0, 5);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 4)
            player.b2body.setLinearVelocity(5, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -4)
            player.b2body.setLinearVelocity(-5, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -4)
            player.b2body.setLinearVelocity(0, -5);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pausebutton.pressPause();
        }
    }


    public void update(float dt) {
        //handle user input first
        handleInput(dt);


        if(musicpaused){
            music.pause();
        }else{
            music.play();
        }

        //takes 1 step in the physics simulation(60 times per second)
        //unless paused = true
        if(!paused) {
            world.step(1 / 60f, 6, 2);


            //Reset ammo to starting point in slingshot, reset all variables to default
            if (Player2.shouldReset()) {
                player.b2body.setLinearVelocity(0, 0);
                player.b2body.setTransform(1.3575f, 1.9488f, 0);
                player.b2body.setGravityScale(0);
                Player2.ammoDestroy = false;

            }

            //Update all mongos in Array mong
            //Check to see if any are dead - if so remove from array
            for (int i = 0; i < mong.size(); i++) {
                mongo = mong.get(i);
                if (mongo.dead()) {
                    mong.remove(mongo);
                }
                mongo.update(dt);
            }

            //update the ammo sprite
            player.update(dt, touched);

            //update hud for as long as gameOver is not true
            if(!gameOver()) {
                hud.update(dt);
            }


            princess.update(dt);
            pausebutton.update(dt);
            mymusic.update(dt);
            mysling.update(dt);
            cloud1.update(dt);
            cloud2.update(dt);
            cloud3.update(dt);
            cloud4.update(dt);


            //Schedule new mongos to be spawned every two seconds, one on either side of the screen
            //as long as there are not more than 50 currently on screen
            if (mongoSchedule == 2) {
                if (mong.size() <= 50) {
                    mong.add(new Mongo(world, this, 13f, 0.16f));
                    mong.add(new Mongo(world, this, -0.1f, 0.16f));
                }
                mongoSchedule = 0;
            }

            //attach gamecam to players x coordinate
        /*if (released && gamecam.position.x <= player.b2body.getPosition().x) {
            gamecam.position.x = player.b2body.getPosition().x;
        }
        if(released && gamecam.position.y <= player.b2body.getPosition().y){
            gamecam.position.y = player.b2body.getPosition().y;
        }
        if (released && gamecam.position.y > player.b2body.getPosition().y
                && player.b2body.getPosition().y >= gameport.getWorldHeight()/2){
            gamecam.position.y = player.b2body.getPosition().y;
        }*/


            gamecam.update();
            renderer.setView(gamecam);
        }else{
            //If paused - change pausebutton icon and pause the music
            //TODO should probably pause music instead of stopping and starting from beginning again
            pausebutton.update(dt);
            music.stop();
        }
    }

    //TODO find out why I am not using show method and whether it is needed
    //I think it might be one of the required implement methods but not sure - should check
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //Make sure adhandler does not show adds during gameplay
        //will be needed though to pass to the GameOver screen
        //handler.showAds(false);

        //Show frames per second in console
        //myLog.log();

        //Update delta time
        update(delta);

        //Colour and alpha
        Gdx.gl.glClearColor(0, 0, 0, 1);

        //clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(4);


        background.stage.draw();
//        rayHandler.setCombinedMatrix(gamecam.combined, 8f, 5f, 10, 10);
       // rayHandler.updateAndRender();

        //TODO Clouds go here
        cloud1.stage.draw();
        cloud2.stage.draw();
        cloud3.stage.draw();
        cloud4.stage.draw();

        renderer.render();


        //Show Box2D bodies
        //b2dr.render(world, gamecam.combined);

        shapeRenderer.setProjectionMatrix(gamecam.combined);
        shapeRenderer2.setProjectionMatrix(gamecam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.WHITE);
        if (!released && player.b2body.getPosition().x < 1.4975f && !paused) {
            shapeRenderer.line(1.1975f, 1.9488f, player.b2body.getPosition().x, player.b2body.getPosition().y);
            shapeRenderer.line(1.4975f, 1.9488f, player.b2body.getPosition().x, player.b2body.getPosition().y);
        }else if (released && player.b2body.getPosition().x < 1.4975f && !paused) {
            shapeRenderer.line(1.1975f, 1.9488f, player.b2body.getPosition().x, player.b2body.getPosition().y);
            shapeRenderer.line(1.4975f, 1.9488f, player.b2body.getPosition().x, player.b2body.getPosition().y);
        }else{
            shapeRenderer.line(1.1975f, 1.9488f, 1.3575f, 1.9488f);
            shapeRenderer.line(1.4975f, 1.9488f, 1.3575f, 1.9488f);
        }

        //shapeRenderer.circle((targetX-0.075f), (targetY-0.075f), 0.15f, 80);
        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);

        //Tell game batch where to recognise the camera is in the gameworld and only render
        //what the camera can see.
        game.batch.setProjectionMatrix(gamecam.combined);


        //load texture to screen
        //first open batch file
        game.batch.begin();




        //draw texture
        princess.draw(game.batch);

        for (int i = 0; i < mong.size(); i++) {
            mongo = mong.get(i);

            mongo.draw(game.batch);
        }
        player.draw(game.batch);
        pausebutton.draw(game.batch);
        mymusic.draw(game.batch);



        //Draw the sling piece with its rotation in relation to the degree of the angle from the starting point
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(1.9488f - player.b2body.getPosition().y, 1.3575f - player.b2body.getPosition().x);
        if (!released) {
            mysling.setRotation(angle);
        }
        mysling.draw(game.batch);

        //close and draw to screen
        game.batch.end();

        //Set batch to draw what Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



        if(gameOver()){
            //Zoom camera in towards centre of screen where the princess is
            gamecam.zoom -= 0.003f;
            if(gamecam.position.y > 1.7f) {
                gamecam.position.y -= 0.01f;
            }

            //Schedule the gameOver screen to wait three seconds before starting
            //so that there is time to zoom in at the end to verify gameover to user
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    music.stop();
                    Timer.instance().clear();
                    game.setScreen(new GameOverScreen(game, musicpaused/*, handler*/));
                    dispose();
                }
            },3);
        }
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        shapeRenderer.dispose();
        music.dispose();
        background.dispose();
        //rayHandler.dispose();
       // light.dispose();

    }

    private Vector3 tmp = new Vector3();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        gameport.unproject(tmp.set(screenX, screenY, 0));
        targetX = 1.9488f + (1.9488f - tmp.x);
        targetY = 1.9488f + (1.9488f - tmp.y);
        if( tmp.x > 11 && tmp.x < 12 && tmp.y > 5.8f
                && tmp.y < 7){
            pausebutton.pressPause();
            buttonjustpressed = true;
            if(paused){
                paused = false;
            }else{
                paused = true;
            }
        }else if(tmp.x > 9.5f && tmp.x < 10.5f
                && tmp.y > 5.8f && tmp.y < 7){
            buttonjustpressed = true;
            if(musicpaused){
                musicpaused = false;
                mymusic.startMusic();
            }else{
                musicpaused = true;
                mymusic.pauseMusic();
            }
        }
        else if(!gameOver() && tmp.x <= 1.4975f) {
            touched = true;
            player.b2body.setLinearVelocity(0, 0);
            if(tmp.x <= 1.4975f) {
                player.b2body.setTransform(tmp.x, tmp.y, 0);
                mysling.s2body.setTransform(tmp.x, tmp.y, 0);
            }
            player.b2body.setGravityScale(0);
            gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
            released = false;
        }
            return true;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(!gameOver() && !paused && !buttonjustpressed) {
            dragged = true;
            gameport.unproject(tmp.set(screenX, screenY, 0));
            targetX = 1.3575f + (1.3575f - tmp.x);
            //System.out.println("tmp.x:" + tmp.x);
           // System.out.println("body:" + player.b2body.getPosition().x);
            targetY = 1.9488f + (1.9488f - tmp.y);
           // System.out.println("body:" + player.b2body.getPosition().y);
            System.out.println("tmp.y:" + tmp.y);
            if(tmp.x <= 1.4975f && tmp.x > 0.2 && tmp.y < startPoint.y + 1 && tmp.y > 0.6) {
                player.b2body.setTransform(tmp.x, tmp.y, 0);
                startVelocity = new Vector2(startPoint.x - tmp.x, startPoint.y - tmp.y);
                mysling.s2body.setTransform(tmp.x, tmp.y, 0);
            }/*else {
                player.b2body.setTransform(1.0375f, 1.9488f, 0);
            }*/
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(!gameOver() && dragged && !paused && !buttonjustpressed) {
            gameport.unproject(tmp.set(screenX, screenY, 0));
            if(tmp.x <= 1.4975f) {
                touched = false;
                player.b2body.setGravityScale(1);
                player.b2body.applyLinearImpulse(new Vector2(startVelocity.x * 8, startVelocity.y * 8
                ), player.b2body.getWorldCenter(), true);
                released = true;
                dragged = false;
                mysling.s2body.setTransform(1.3575f, 1.9488f, 0);
                mysling.setRotation(0);

            }
        }else if(!buttonjustpressed && tmp.x <= 1.4975f){
            player.b2body.setTransform(1.3575f, 1.9488f, 0);
            mysling.s2body.setTransform(1.3575f, 1.9488f, 0);
        }
        buttonjustpressed = false;
        return true;
    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
    public boolean gameOver(){
        if(princess.princessDead){
            return true;
        }
        return false;
    }


}
