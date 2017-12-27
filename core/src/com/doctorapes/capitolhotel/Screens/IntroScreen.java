package com.doctorapes.capitolhotel.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Tools.AdHandler;

/**
 * Created by Apez on 14/02/2017.
 */

public class IntroScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private Boolean pausemusic;
    private Image backgroundimage;
    private  OrthographicCamera gamecam;
    private AdHandler handler;



    public IntroScreen(Game game, Boolean musicpaused/*, AdHandler handler*/){
        //this.handler = handler;
        this.game = game;
        //gamecam = new OrthographicCamera();
        viewport = new FitViewport(MongosX.V_WIDTH, MongosX.V_HEIGHT, new OrthographicCamera());
        //gamecam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
        stage = new Stage(viewport, ((MongosX) game).batch);
        pausemusic = musicpaused;
        backgroundimage = new Image(new Texture("mongosbackground.png"));



        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label startGameLabel = new Label("START GAME", font);
        startGameLabel.setFontScale(4);
        table.add(startGameLabel).expandX().padTop(600);

        stage.addActor(backgroundimage);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //handler.showAds(false);

        if(Gdx.input.justTouched()) {
            game.setScreen(new TutorialScreen(game, pausemusic/*, handler*/));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}


