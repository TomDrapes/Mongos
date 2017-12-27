package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Screens.PlayScreen;

/**
 * Created by Apez on 4/02/2017.
 */

public class Princess extends Sprite {

    private enum State { STANDING, FREAKING}
    private Princess.State currentState;
    private Princess.State previousState;
    private float stateTimer;

    private World world;
    public Body p2body;
    public Boolean princessDead;
    private static boolean freakingOut;

    private Animation princessFreakOut, princessStanding;



    public Princess(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("princess"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        freakingOut = false;


        definePlayer();
        setBounds(40, 0, 40/ MongosX.PPM, 32/ MongosX.PPM);

        princessDead = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(),  0, 130, 40, 32));
        frames.add(new TextureRegion(getTexture(),  80, 130, 40, 32));
        frames.add(new TextureRegion(getTexture(),  0, 98, 40, 32));
        frames.add(new TextureRegion(getTexture(),  40, 98, 40, 32));
        frames.add(new TextureRegion(getTexture(),  40, 130, 40, 32));
        frames.add(new TextureRegion(getTexture(),  40, 98, 40, 32));
        frames.add(new TextureRegion(getTexture(),  0, 98, 40, 32));
        frames.add(new TextureRegion(getTexture(),  80, 130, 40, 32));
        frames.add(new TextureRegion(getTexture(),  0, 130, 40, 32));
        princessFreakOut = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 60; i++)
            frames.add(new TextureRegion(getTexture(),  0, 162, 40, 32));
        frames.add(new TextureRegion(getTexture(),  80, 97, 40, 32));
        frames.add(new TextureRegion(getTexture(),  80, 97, 40, 32));
        princessStanding = new Animation(0.1f, frames);
        frames.clear();

    }

    public void update(float dt){
        setPosition(p2body.getPosition().x - getWidth() / 2, p2body.getPosition().y - getHeight()/2);
        p2body.applyForce(new Vector2(3, 0), p2body.getWorldCenter(), true);
        setRegion(getFrame(dt));
    }
    private TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch(currentState){
            case FREAKING:
                region = princessFreakOut.getKeyFrame(stateTimer, true);
                break;
            default:
                region = princessStanding.getKeyFrame(stateTimer, true);
                break;

        }
        // TextureRegion region = playerWalk.getKeyFrame(stateTimer, true);

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    private Princess.State getState(){
        if(freakingOut) {
            return State.FREAKING;
        }else {
            return State.STANDING;
        }
    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(6.65f, 1.7f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        p2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13/ MongosX.PPM);
        fdef.filter.categoryBits = MongosX.PRINCESS_BIT;
        fdef.filter.maskBits = MongosX.AMMO_BIT
                | MongosX.MONGO_BIT
                | MongosX.BRICK_BIT;

        fdef.shape = shape;
        p2body.createFixture(fdef).setUserData(this);

    }

    public static void freakOut(Boolean freaking){
        if (freaking){
            freakingOut = true;
        }else{
            freakingOut = false;
        }
    }

    public void endGame(){
        princessDead = true;
    }
}
