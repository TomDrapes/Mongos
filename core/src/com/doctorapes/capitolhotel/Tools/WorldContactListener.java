package com.doctorapes.capitolhotel.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.doctorapes.capitolhotel.Sprites.Mongo;
import com.doctorapes.capitolhotel.Sprites.Player2;
import com.doctorapes.capitolhotel.MongosX;
import com.doctorapes.capitolhotel.Sprites.InteractiveTileObject;
import com.doctorapes.capitolhotel.Sprites.Princess;

/**
 * Created by Apez on 21/01/2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //cDef - collision definition
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case MongosX.MONGO_BIT | MongosX.AMMO_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.MONGO_BIT) {
                    ((Mongo) fixA.getUserData()).hit();
                    ((Player2) fixB.getUserData()).reset();
                    ((Player2) fixB.getUserData()).cancelGravity();
                    //System.out.println("error1");
                }else {
                    ((Mongo) fixB.getUserData()).hit();
                    ((Player2) fixA.getUserData()).reset();
                    ((Player2) fixA.getUserData()).cancelGravity();
                    //System.out.println("error1");
                }
                break;

            case MongosX.MONGO_BIT:
                    ((Mongo) fixA.getUserData()).climb();
                    ((Mongo) fixB.getUserData()).climb();
                break;
            case MongosX.PRINCESS_BIT | MongosX.MONGO_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.PRINCESS_BIT) {
                    ((Princess) fixA.getUserData()).endGame();
                    System.out.println("GameOver");
                }else {
                    ((Princess) fixB.getUserData()).endGame();
                    System.out.println("GameOver");

                }
                break;
            case MongosX.AMMO_BIT | MongosX.BRICK_BIT:
            case MongosX.AMMO_BIT | MongosX.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.AMMO_BIT) {
                    ((Player2) fixA.getUserData()).cancelGravity();
                    //System.out.println("error2");
                }else {
                    ((Player2) fixB.getUserData()).cancelGravity();
                    //System.out.println("error2");
                }break;
            case MongosX.MONGO_BIT | MongosX.BRICK_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.MONGO_BIT) {
                    //((Mongo) fixA.getUserData()).attack(true);
                    //System.out.println("Hit");
                } else {
                    //((Mongo) fixB.getUserData()).attack(true);
                    //System.out.println("Hit");
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MongosX.MONGO_BIT | MongosX.BRICK_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.MONGO_BIT) {
                    Princess.freakOut(false);
                } else {
                    Princess.freakOut(false);
                }
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MongosX.MONGO_BIT | MongosX.BRICK_BIT:
                if (fixA.getFilterData().categoryBits == MongosX.MONGO_BIT) {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                    Princess.freakOut(true);
                    //System.out.println("Hit");
                } else {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                    Princess.freakOut(true);
                    //System.out.println("Hit");
                }
                break;
        }

    }
}


