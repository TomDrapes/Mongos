package com.doctorapes.capitolhotel.Scenes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 13/02/2017.
 */

public class PlayPause extends Sprite {
    private World world;
    public Body pausebuttonbody;
    public Boolean paused;
    private TextureAtlas atlas;




    public PlayPause(World world, PlayScreen screen, TextureAtlas atlas){
        super(screen.getAtlas().findRegion("pausebtn"));
        this.world = world;
        this.atlas = atlas;


        //definePlayer();
        setBounds(128, 0, 128/ MongosX.PPM, 128/ MongosX.PPM);

        paused = false;

    }

    public void update(float dt){
        setPosition(11, 5.8f);
        if (paused){
            setRegion(atlas.findRegion("playbtn"));
        }else{
            setRegion(atlas.findRegion("pausebtn"));
        }
    }
    public void pressPause(){
        if(!paused){
            paused = true;
        }else {
            paused = false;
        }
    }


}
