package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Apez on 20/06/2017.
 */

public class Cloud extends Sprite {

    public Stage stage;
    //Create new camera and new viewport for Hud so that when game world moves the hud stays the same.
    private Viewport viewport;

    private OrthographicCamera gamecam2;


    private Texture texture;
    private Image image;
    private float xPos, yPos;
    private Vector2 cloud1, cloud2, cloud3, cloud4;
    private float moveSpeed;


    public Cloud(SpriteBatch sb, int cloudNumber){

        cloud1 =  new Vector2(120, 300);
        cloud2 = new Vector2(640, 360);
        cloud3 = new Vector2(1200, 260);
        cloud4 = new Vector2(1600, 340);

        moveSpeed = MathUtils.random(0.05f, 0.15f);

        gamecam2 = new OrthographicCamera();

        viewport = new FitViewport(com.doctorapes.capitolhotel.MongosX.V_WIDTH, com.doctorapes.capitolhotel.MongosX.V_HEIGHT, gamecam2);

        stage = new Stage(viewport, sb);

        //texture = new Texture("cloud.png");
        //image = new Image(texture);

        switch (cloudNumber){
            case 1:
                xPos = cloud1.x;
                yPos = cloud1.y;
                texture = new Texture("cloud2.png");
                image = new Image(texture);
                image.setPosition(cloud1.x, cloud1.y);
                break;
            case 2:
                xPos = cloud2.x;
                yPos = cloud2.y;
                texture = new Texture("cloud.png");
                image = new Image(texture);
                image.setPosition(cloud2.x, cloud2.y);
                break;
            case 3:
                xPos = cloud3.x;
                yPos = cloud3.y;
                texture = new Texture("cloud2.png");
                image = new Image(texture);
                image.setPosition(cloud3.x, cloud3.y);
                break;
            case 4:
                xPos = cloud4.x;
                yPos = cloud4.y;
                texture = new Texture("cloud.png");
                image = new Image(texture);
                image.setPosition(cloud4.x, cloud4.y);
                break;
        }

        image.scaleBy(MathUtils.random(-0.25f, 0));
        //float greyScale = MathUtils.random(0, 50);
        //image.setColor(70,10,10, 1);

        gamecam2.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage.addActor(image);


    }

    public void update(float deltaTime){

        xPos -= moveSpeed;

        image.setPosition(xPos, yPos);

        if(xPos + image.getWidth() < 0){
            image.setPosition(1700, yPos);
            xPos = 1700;


        }
    }
}
