package com.doctorapes.capitolhotel.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 16/12/2016.
 */

public class B2WorldCreator {

    public static Body body;
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        /*//BodyDef - Before you can create a body you need to define it.
        //BodyDef defines what a body consists of.
        BodyDef bdef = new BodyDef();
        //Polygonshape is for fixture
        PolygonShape shape = new PolygonShape();
        //Before you create a fixture you need to define it.
        //FixtureDef defines the fixture before you can add it to the body.
        FixtureDef fdef = new FixtureDef();
        //Body body;*/

        //Create wall bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() /2) / MongosX.PPM, (rect.getY() + rect.getHeight() /2) / MongosX.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ MongosX.PPM, rect.getHeight()/2/ MongosX.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create bricks bodies/fixtures --NB: .get(4) is a reference to the position in Tiled app
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            //System.out.println("new brick");

            new com.doctorapes.capitolhotel.Sprites.Brick(screen, object);
        }
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() /2) / MongosX.PPM, (rect.getY() + rect.getHeight() /2) / MongosX.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ MongosX.PPM, rect.getHeight()/2/ MongosX.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }




    }
}
