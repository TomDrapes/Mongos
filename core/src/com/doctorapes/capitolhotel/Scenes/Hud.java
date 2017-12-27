package com.doctorapes.capitolhotel.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 15/12/2016.
 */

public class Hud implements Disposable{
    public Stage stage;
    //Create new camera and new viewport for Hud so that when game world moves the hud stays the same.
    private Viewport viewport;

    public static Integer worldTimer;
    private float timeCount;
    private Integer score;

    //Create Scene2d widgets
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
   // Label worldLabel;
    //Label playerLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 0;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MongosX.V_WIDTH, MongosX.V_HEIGHT, new OrthographicCamera());
        //set stage NB: A stage is basically just an empty box which can take a table so that it is easier to
        //organise all the widgets/labels in specific positions.
        stage = new Stage(viewport, sb);

        Table table = new Table();
        //place table at top of stage as opposed to the default placement in center of screen
        table.top();
        //make table fill the size of the stage
        table.setFillParent(true);
        // %03d means there will be 3 digits and d means integer
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel.setFontScale(3);
        //scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
       // worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
       // playerLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //populate the table
       // table.add(playerLabel).expandX().padTop(10);
       // table.add(worldLabel).expandX().padTop(10);
       // table.add(timeLabel).expandX().padTop(10);
        //create new row everything below this will be in a new row.
       // table.row();
       // table.add(scoreLabel).expandX();
       // table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //add table to stage
        stage.addActor(table);
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer++;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
            PlayScreen.mongoSchedule++;
            PlayScreen.gameoverSchedule++;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
