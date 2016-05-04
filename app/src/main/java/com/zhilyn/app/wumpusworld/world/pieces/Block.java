package com.zhilyn.app.wumpusworld.world.pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 4/27/16.
 * represents a block on the game map
 */
public class Block implements Player.PlayerAction {

    //constant that states weather the block is a safe block or not
    private final boolean IS_SAFE_BLOCK;

    List<GamePiece> content;
    private Point point;

    public Block(boolean isSafeBlock){
        this.IS_SAFE_BLOCK = isSafeBlock;
        this.content = new ArrayList<>();
    }

    /**
     * adds an item to the block returns false if the block has a pit, gold, or wumpus
     * @return  true if successfully added false if the piece cant be added because there is already
     * a pit, gold, or wumpus
     * */
    public boolean addPiece(GamePiece piece){
        if(isAdded(piece.getType())){
            return false;
        }

        GamePiece.Type type = piece.getType();
        boolean isWGP = piece.isWGP();
        if ((isWGP && this.hasWGP()) || (IS_SAFE_BLOCK && piece.isWGP())) {
            return false;
        }

        this.content.add(piece);
        if(piece.getType() == GamePiece.Type.PLAYER){
            ((Player) piece).action = this;
        }

        return true;
    }

    private boolean hasWGP() {
        return this.hasWumpus() || this.hasPit() || this.hasGold();
    }

    /**
     * checks to see if the parameter is already added to the list of content on the block
     * @param type type to check
     * @return true if the type is already added to the block
     * */
    private boolean isAdded(GamePiece.Type type) {
        boolean check = false;

        for (GamePiece gamePiece : this.content) {
            if(gamePiece.getType() == type){
                check = true;
                break;
            }
        }

        return check;
    }

    public boolean hasPit(){
        return this.isAdded(GamePiece.Type.PIT);
    }

    public boolean hasBreeze(){
        return this.isAdded(GamePiece.Type.BREEZE);
    }

    public boolean hasWumpus(){
        return this.isAdded(GamePiece.Type.WUMPUS);
    }

    public boolean hasStench(){
        return this.isAdded(GamePiece.Type.STENCH);
    }

    public boolean hasGold(){
        return this.isAdded(GamePiece.Type.GOLD);
    }

    public boolean hasGlitter(){
        return this.isAdded(GamePiece.Type.GLITTER);
    }

    /**
     * returns a list of the game pieces
     * */
    public List<GamePiece> getPieces() {
        return content;
    }

    /**
     * moves the player to the next block and removes the player from the list of pieces
     */
    public void movePlayer(Block next) {
        for (GamePiece gamePiece : content) {

        }
    }

    @Override
    public void onPercept() {

    }

    @Override
    public void onGrab() {
        if(hasGold()){
            System.out.println("You win!");
        }
    }

    public void plot(int x, int y) {
        this.point = new Point(x, y);
    }

    public Point getPoint() {
        return this.point;
    }

    public void killWumpus() {
        for (GamePiece gamePiece : content) {
            if(gamePiece.getType() == GamePiece.Type.WUMPUS){
                content.remove(gamePiece);
            }
        }
    }

    public Player getPlayer() {
        for (GamePiece gamePiece : content) {
            if(gamePiece.getType() == GamePiece.Type.PLAYER){
                return (Player) gamePiece;
            }
        }
        return null;
    }

    public Player removePlayer() {
        for (int i = 0; i < content.size(); i++) {
            GamePiece gamePiece = content.get(i);
            if (gamePiece.getType() == GamePiece.Type.PLAYER) {
                return (Player) content.remove(i);
            }
        }
        return null;
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
