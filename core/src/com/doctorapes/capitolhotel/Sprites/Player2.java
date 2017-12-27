package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 18/12/2016.
 */

public class Player2  extends Sprite {
    //public enum State { STANDING };
    //public State currentState;
    public World world;
    public Body b2body;
    public static Boolean ammoDestroy, hardReset;
    private static Boolean xGravity, justReset;


    public static int life;


    public Player2(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("ammo2"));


        this.world = world;
        //currentState = State.STANDING;


        definePlayer();
        setBounds(0, 0, 16/ MongosX.PPM, 16/ MongosX.PPM);

        ammoDestroy = false;
        life = 4;
        xGravity = false;
        hardReset = false;
        justReset = false;

    }

    public void update(float dt, boolean touched){
        //System.out.println("Gravity: " + xGravity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);


        if(justReset){
            b2body.setLinearVelocity(0,0);
            justReset = false;
        }

        //System.out.println("Ypos: " + b2body.getPosition().y);
        if(touched){
            //System.out.println(touched);
            xGravity = false;
            b2body.setLinearVelocity(0, 0);

        } else if(xGravity){
            b2body.applyForce(new Vector2(3, 0), b2body.getWorldCenter(), true);
        }

        if(b2body.getPosition().y < 0 || b2body.getPosition().x > MongosX.V_WIDTH/MongosX.PPM){
            xGravity = false;
            b2body.setLinearVelocity(0,0);
            b2body.setGravityScale(0);
            b2body.setTransform(1.3575f, 1.9488f, 0);
            justReset = true;

           /* System.out.println("Width: " + MongosX.V_WIDTH);
            System.out.println("Xpos: " + b2body.getPosition().x);
            System.out.println("Height: " + MongosX.V_HEIGHT);
            System.out.println("Ypos: " + b2body.getPosition().y);*/
        }
    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(1.3575f, 1.9488f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MongosX.PPM);
        fdef.filter.categoryBits = MongosX.AMMO_BIT;
        fdef.filter.maskBits = MongosX.MONGO_BIT
                | MongosX.GROUND_BIT
                | MongosX.BRICK_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.2f;
        fdef.friction = 0.2f;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void reset(){
       // System.out.println("reset");
        //life -= 1;
        if(life == 0) {
            ammoDestroy = true;
        }

    }

    public static boolean shouldReset(){
        if(ammoDestroy ){
            xGravity = false;
            life = 4;
            return true;
        }else if (!ammoDestroy){
            return false;
        }else{
            return false;
        }
    }
    public void cancelGravity(){
        System.out.println("Gravity cancelled");
        xGravity = true;
    }
    public static void startGravity(){
        System.out.println(xGravity);
        xGravity = false;
    }
    public void makeReset(){
        hardReset = true;
    }
}
