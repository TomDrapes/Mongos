package com.doctorapes.capitolhotel.Scenes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 13/02/2017.
 */

public class MusicPlay extends Sprite {
    private World world;
    public Boolean stopmusic;
    private TextureAtlas atlas;




    public MusicPlay(World world, PlayScreen screen, TextureAtlas atlas){
        super(screen.getAtlas().findRegion("soundon"));
        this.world = world;
        this.atlas = atlas;


        //definePlayer();
        setBounds(128, 0, 128/ MongosX.PPM, 128/ MongosX.PPM);

        stopmusic = false;

    }

    public void update(float dt){
        setPosition(9.5f, 5.8f);
        if (stopmusic){
            setRegion(atlas.findRegion("soundoff"));
        }else{
            setRegion(atlas.findRegion("soundon"));
        }
    }
    public void pauseMusic(){
         stopmusic = true;
    }
    public void startMusic(){
        stopmusic = false;
    }


}
