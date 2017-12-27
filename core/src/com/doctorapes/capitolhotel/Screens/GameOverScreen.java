package com.doctorapes.capitolhotel.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doctorapes.capitolhotel.Tools.AdHandler;

/**
 * Created by Apez on 4/02/2017.
 */

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private Boolean pausemusic;

    private AdHandler handler;


    public GameOverScreen(Game game, Boolean musicpaused/*, AdHandler handler*/){
        this.game = game;
        //this.handler = handler;
        viewport = new FitViewport(com.doctorapes.capitolhotel.MongosX.V_WIDTH, com.doctorapes.capitolhotel.MongosX.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((com.doctorapes.capitolhotel.MongosX) game).batch);
        pausemusic = musicpaused;

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);


        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        gameOverLabel.setFontScale(4);
        Label scoreLabel = new Label(String.format("%03d", com.doctorapes.capitolhotel.Scenes.Hud.worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(3);
        Label score = new Label("SCORE: ", font);
        score.setFontScale(3);
        Label playAgainLabel = new Label("Tap to Play Again", font);
        playAgainLabel.setFontScale(2);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(score).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.add(playAgainLabel).expandX().padTop(10);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //handler.showAds(true);
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((com.doctorapes.capitolhotel.MongosX) game, pausemusic/*, handler*/));
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
