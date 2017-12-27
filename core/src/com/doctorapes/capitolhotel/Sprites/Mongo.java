package com.doctorapes.capitolhotel.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.doctorapes.capitolhotel.MongosX;

/**
 * Created by Apez on 17/01/2017.
 */

public class Mongo extends Sprite {

    private enum State { WALKING, WALK_RIGHT, STANDING, DEAD, ATTACK_LEFT, ATTACK_RIGHT}
    private Mongo.State currentState;
    private Mongo.State previousState;
    private World world;
    public Body m2body;
    //private TextureRegion playerStand;
    private Animation playerWalk, playerWalkRight, dead, playerAttack, playerAttackRight;

    private float stateTimer;
    private float xPosition, yPosition;

    private int popTimer;

    private boolean setToDestroy, destroyed, flipped, stop, setToPop, attacking;


    public Mongo(World world, com.doctorapes.capitolhotel.Screens.PlayScreen screen, Float xPos, Float yPos){
        super(screen.getAtlas().findRegion("mong1"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        xPosition = xPos;
        yPosition = yPos;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(getTexture(), 64, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 64, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 96, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 96, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 32, 64, 32, 32));
        frames.add(new TextureRegion(getTexture(), 32, 64, 32, 32));
        frames.add(new TextureRegion(getTexture(), 96, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 96, 32, 32, 32));
        playerWalk = new Animation(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(getTexture(), 0, 64, 32, 32));
        frames.add(new TextureRegion(getTexture(), 0, 64, 32, 32));
        frames.add(new TextureRegion(getTexture(), 0, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 0, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 17, 0, 32, 32));
        frames.add(new TextureRegion(getTexture(), 17, 0, 32, 32));
        frames.add(new TextureRegion(getTexture(), 0, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 0, 32, 32, 32));
        playerWalkRight = new Animation(0.1f, frames);
        frames.clear();
        frames.add(new TextureRegion(getTexture(), 49, 0, 32, 32));
        frames.add(new TextureRegion(getTexture(), 32, 32, 32, 32));
        frames.add(new TextureRegion(getTexture(), 81, 0, 32, 32));
        dead = new Animation(0.1f, frames);
        frames.clear();

        //playerStand = new TextureRegion(getTexture(), 64, 0, 32, 32);

        definePlayer();
        setBounds(80, 0, 32/ com.doctorapes.capitolhotel.MongosX.PPM, 32/ com.doctorapes.capitolhotel.MongosX.PPM);
        //setRegion(playerStand);
        setRegion(getFrame(stateTimer));

        setToDestroy = false;
        destroyed = false;
        flipped = false;
        stop = false;
        setToPop = false;
        attacking = false;

        popTimer = 0;

    }

    public void update(float dt){
        m2body.applyForce(new Vector2(3, 0), m2body.getWorldCenter(), true);
        if (setToPop && !destroyed) {
            setRegion(getFrame(dt));
            world.destroyBody(m2body);
            destroyed = true;
        }else if(setToPop && destroyed){
            popTimer += 1;
            if (popTimer == 6) {
                setToDestroy = true;
                setToPop = false;
            }
        /*}
        else if (setToDestroy && !destroyed) {
            //setRegion(getFrame(dt));
            world.destroyBody(m2body);
            destroyed = true;*/

        }else if(stop){
            setPosition(m2body.getPosition().x - getWidth() / 2, m2body.getPosition().y - getHeight() / 2);
            m2body.setLinearVelocity(0, -0.5f);
            setRegion(getFrame(dt));


        }else if(m2body.getPosition().x > 6.7
                && m2body.getLinearVelocity().y == 0){
            setPosition(m2body.getPosition().x - getWidth() / 2, m2body.getPosition().y - getHeight() / 2);
            m2body.setLinearVelocity(-1, 0);
            setRegion(getFrame(dt));
            flipped = false;
            //stop = false;

        }else if (m2body.getLinearVelocity().y == 0 && m2body.getPosition().x < 6.4){
            setPosition(m2body.getPosition().x - getWidth() / 2, m2body.getPosition().y - getHeight() / 2);
            m2body.setLinearVelocity(1, 0);
            flipped = true;
            //stop=false;
            setRegion(getFrame(dt));

        /*}else if(stop){
            setPosition(m2body.getPosition().x - getWidth() / 2, m2body.getPosition().y - getHeight() / 2);
            m2body.setLinearVelocity(0, -5);
            setRegion(getFrame(dt));*/

        }else{
            //System.out.println("Is this called?");
            setPosition(m2body.getPosition().x - getWidth() / 2, m2body.getPosition().y - getHeight() / 2);
            //m2body.setLinearVelocity(0, -5);
            setRegion(getFrame(dt));
        }
    }

    private TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch(currentState){
            case WALKING:
                region = playerWalk.getKeyFrame(stateTimer, true);
                break;
            case WALK_RIGHT:
                region = playerWalkRight.getKeyFrame(stateTimer, true);
                break;
            case DEAD:
                region = dead.getKeyFrame(stateTimer, true);
                break;
            case ATTACK_LEFT:
                region = playerAttack.getKeyFrame(stateTimer, true);
                break;
            case ATTACK_RIGHT:
                region = playerAttackRight.getKeyFrame(stateTimer, true);
                break;
            default:
                    region = playerWalk.getKeyFrame(stateTimer, true);
                break;

        }
       // TextureRegion region = playerWalk.getKeyFrame(stateTimer, true);

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    private State getState(){
        if(setToPop) {
            System.out.println("dead");
            return State.DEAD;
        }else if(attacking && m2body.getPosition().x > 6.7f) {
            return State.ATTACK_RIGHT;
        }else if(attacking && m2body.getPosition().x < 6.7f){
            return State.ATTACK_LEFT;
        }else if(m2body.getPosition().x > 6.7f) {
            return State.WALKING;
        }else if(m2body.getPosition().x < 6.7f) {
            return State.WALK_RIGHT;
        }else {
            return Mongo.State.STANDING;
        }
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(xPosition, yPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        m2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ com.doctorapes.capitolhotel.MongosX.PPM);
        fdef.filter.categoryBits = com.doctorapes.capitolhotel.MongosX.MONGO_BIT;
        fdef.filter.maskBits = com.doctorapes.capitolhotel.MongosX.AMMO_BIT
                | com.doctorapes.capitolhotel.MongosX.GROUND_BIT
                | com.doctorapes.capitolhotel.MongosX.MONGO_BIT
                | com.doctorapes.capitolhotel.MongosX.PRINCESS_BIT
                | com.doctorapes.capitolhotel.MongosX.BRICK_BIT;

        fdef.shape = shape;
        m2body.createFixture(fdef).setUserData(this);

        //Create the back here
        PolygonShape back = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(14, 8).scl(1/ com.doctorapes.capitolhotel.MongosX.PPM);
        vertice[1] = new Vector2(14, -8).scl(1/ com.doctorapes.capitolhotel.MongosX.PPM);
        vertice[2] = new Vector2(5, 3).scl(1/ com.doctorapes.capitolhotel.MongosX.PPM);
        vertice[3] = new Vector2(5, -3).scl(1/ com.doctorapes.capitolhotel.MongosX.PPM);
        back.set(vertice);

        /*fdef.shape = back;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = CapitolHotel.MONGO_BACK_BIT;
        m2body.createFixture(fdef).setUserData(this);*/

    }
    public void attack(Boolean isAttacking){
        if (isAttacking){
            attacking = true;
        }else{
            attacking = false;
        }
    }

    public void hit() {
        //setToDestroy = true;
        setToPop = true;
        MongosX.manager.get("sounds/popsound.wav", Sound.class).play();

        //System.out.println("HIT");
    }

    public void climb(){
        if(flipped){
            m2body.applyLinearImpulse(new Vector2(1, 0.7f), m2body.getWorldCenter(), true);
            //System.out.println("CLIMB");
        }else {
            m2body.applyLinearImpulse(new Vector2(-1, 0.7f), m2body.getWorldCenter(), true);
            //System.out.println("CLIMB");
        }
    }
    public void stopMongo(){
        stop = true;
    }
    public void startMongo(){
        stop = false;
    }
    public boolean dead(){
        if(setToDestroy || m2body.getPosition().x < -0.15f || m2body.getPosition().x > 13.1f) {
           // System.out.println("DEAD");
            return true;
        }else {
            return false;
        }
    }
    public float getStateTimer(){
        return stateTimer;
    }


}
