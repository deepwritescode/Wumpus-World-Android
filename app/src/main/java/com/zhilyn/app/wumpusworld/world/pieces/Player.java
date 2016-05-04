package com.zhilyn.app.wumpusworld.world.pieces;

/**
 * Created by Deep on 4/27/16.
 * this represents the model for the player
 */
public class Player extends GamePiece{

    private Position position = Position.UP;

    public enum Position{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public interface PlayerAction{
        void onPercept();
        void onGrab();
    }

    PlayerAction action;

    private int arrow = 1;

    @Override
    public Type getType() {
        return Type.PLAYER;
    }

    public void shoot(){
        arrow = arrow - 1;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void grab(){
        if(action != null) {
            action.onGrab();
        }
    }

    public void faceUp(){
        this.position = Position.UP;
    }

    public void faceDown(){
        this.position = Position.DOWN;
    }

    public void faceLeft(){
        this.position = Position.LEFT;
    }

    public void faceRight(){
        this.position = Position.RIGHT;
    }

}
