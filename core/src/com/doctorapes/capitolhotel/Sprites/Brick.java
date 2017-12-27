package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 28/01/2017.
 */

public class Brick extends InteractiveTileObject {

    private int life = 2000;

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MongosX.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        //if (mario.isBig()) {
       // System.out.println("yup");
        life -= 1;
        if (life == 0){
            //Gdx.app.log("brick", "collision");
            setCategoryFilter(MongosX.DESTROYED_BIT);
            getCell().setTile(null);
            //Hud.addScore(200);
            //MyGdxGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        //MyGdxGame.manager.get("audio/sounds/bump.wav", Sound.class).play();

    }
}
