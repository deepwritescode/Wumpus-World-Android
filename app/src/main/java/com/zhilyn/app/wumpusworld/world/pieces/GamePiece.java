package com.zhilyn.app.wumpusworld.world.pieces;

/**
 * Created by Deep on 4/27/16.
 * This abstract class represents pieces on the game map
 */
public abstract class GamePiece {

    public abstract Type getType();

    /**
     * @return true if this class is a Wumpus, Gold, or Pit
     */
    public boolean isWGP() {
        return getType() == Type.GOLD ||
                getType() == Type.PIT ||
                getType() == Type.WUMPUS;
    }

    public enum Type{
        PLAYER,
        PIT,
        BREEZE,
        WUMPUS,
        STENCH,
        GLITTER,
        GOLD
    }

    @Override
    public String toString() {
        return getType().toString();
    }
}
