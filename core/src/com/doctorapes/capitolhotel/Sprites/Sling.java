package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 7/03/2017.
 */

public class Sling  extends Sprite {
    public enum State { STANDING };
    public State currentState;
    public World world;
    public Body s2body;






    public Sling(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("mysling"));

        this.world = world;
        currentState = State.STANDING;

        definePlayer();
        setBounds(0, 0, 32/ MongosX.PPM, 32/ MongosX.PPM);

    }

    public void update(float dt){
        setPosition(s2body.getPosition().x - getWidth() / 2, s2body.getPosition().y - getHeight()/2);
        //s2body.applyForce(new Vector2(3, 0), s2body.getWorldCenter(), true);
        //s2body.setGravityScale(0);
        s2body.setLinearVelocity(0,0);
    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(1.3575f, 1.9488f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        s2body = world.createBody(bdef);
        s2body.setGravityScale(0);

        /*FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ com.doctorapes.mongos.CapitolHotel.PPM);
        fdef.shape = shape;
        s2body.createFixture(fdef);*/

    }

}
