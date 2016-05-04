package com.zhilyn.app.wumpusworld.world;


import android.util.Log;

import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Breeze;
import com.zhilyn.app.wumpusworld.world.pieces.GamePiece;
import com.zhilyn.app.wumpusworld.world.pieces.Glitter;
import com.zhilyn.app.wumpusworld.world.pieces.Gold;
import com.zhilyn.app.wumpusworld.world.pieces.Pit;
import com.zhilyn.app.wumpusworld.world.pieces.Player;
import com.zhilyn.app.wumpusworld.world.pieces.Stench;
import com.zhilyn.app.wumpusworld.world.pieces.Wumpus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Deep on 4/27/16.
 * class containing the Wumpus world map with various constants
 */
public class GameMap {
    public static GameMap instance;

    private static final int X_COUNT = 4;
    private static final int Y_COUNT = 4;
    private static Random randomNumberGen;
    private Block[][] grid;
    private Player player;
    private Gold gold;

    private GameMap() {
        grid = new Block[X_COUNT][Y_COUNT];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                grid[x][y] = new Block(false);
            }
        }

        grid[0][0] = new Block(true);
        grid[0][1] = new Block(true);
        grid[1][0] = new Block(true);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                grid[x][y].plot(x, y);
            }
        }
    }

    public static GameMap init() {
        randomNumberGen = new Random();

        GameMap map = new GameMap();
        map.loadPlayer();
        map.loadGold();
        map.loadPit();
        map.loadWumpus();

        GameMap.instance = map;

        return map;
    }

    public Block[][] getGrid() {
        return grid;
    }

    /**
     * @param point the point you want to get the block for
     * @return the block at Point(x, y);
     * */
    public Block getBlock(Block.Point point){
        int x = point.getX();
        int y = point.getY();

        if(inBounds(x) && inBounds(y)){
            return grid[x][y];
        }else{
            return null;
        }
    }

    /**
     * @param x the x position of the point
     * @param y the y position of the point
     *
     * @return  the block at (x, y)
     * */
    public Block getBlock(final int x, final int y){
        if(inBounds(x) && inBounds(y)){
            return grid[x][y];
        }else{
            return null;
        }
    }

    public Block getBlockUp(Block block){
        Block.Point p = block.getPoint();
        int x = p.getX();
        int y = p.getY();
        return getBlock(x, y + 1);
    }

    public Block getBlockRight(Block block){
        Block.Point p = block.getPoint();
        int x = p.getX();
        int y = p.getY();
        return getBlock(x + 1, y);
    }

    public Block getBlockLeft(Block block){
        Block.Point p = block.getPoint();
        int x = p.getX();
        int y = p.getY();
        return getBlock(x - 1, y);
    }

    public Block getBlockDown(Block block){
        Block.Point p = block.getPoint();
        int x = p.getX();
        int y = p.getY();
        return getBlock(x, y - 1);
    }

    /**
     * adds the Player to the map
     */
    private void loadPlayer() {
        System.out.print("Loading Player... ");

        this.player = new Player();
        Block b = grid[0][0];
        b.addPiece(player);

        System.out.println("Done!");
    }

    /**
     * adds Gold to the map and also loads the Glitter to the map
     */
    private void loadGold() {
        System.out.print("Loading Gold... ");

        this.gold = new Gold();
        int randX = randomNumberGen.nextInt(4 - 2) + 2;
        int randY = randomNumberGen.nextInt(4 - 2) + 2;
        System.out.print("(" + randX + ":" + randY + ") ");
        Block b = grid[randX][randY];
        b.addPiece(gold);

        //loads glitters items perpendicular to Gold
        Glitter glitter = new Glitter();
        b.addPiece(glitter);

        System.out.println("Done!");
    }

    /**
     * adds Pits to the map and also loads the breezes to the map
     */
    private void loadPit() {
        System.out.print("Loading Pits... ");

        for (int i = 0; i < 1; i++) {
            Pit pit = new Pit();
            boolean couldAddPiece = false;

            //try to add a pit to the block if it can't be added generate another point to add the block item
            while (!couldAddPiece) {
                int randX = randomNumberGen.nextInt(4);
                int randY = randomNumberGen.nextInt(4);
                Block block = grid[randX][randY];
                couldAddPiece = block.addPiece(pit);
                if (couldAddPiece) {
                    System.out.print("(" + randX + ":" + randY + ") ");
                    loadPerpendicular(randX, randY, new Breeze());
                }
            }
        }

        System.out.println("Done!");
    }

    /**
     * adds Wumpus to the map and also loads the stenches to the map
     */
    private void loadWumpus() {
        System.out.print("Loading Wumpus... ");

        Wumpus wumpus = new Wumpus();
        boolean couldAddPiece = false;

        //try to add a wumpus to the block if it can't be added it generates more points to add the block item
        while (!couldAddPiece) {
            int randX = randomNumberGen.nextInt(4);
            int randY = randomNumberGen.nextInt(4);
            Block block = grid[randX][randY];
            couldAddPiece = block.addPiece(wumpus);
            if (couldAddPiece) {
                System.out.print("(" + randX + ":" + randY + ") ");
                loadPerpendicular(randX, randY, new Stench());
            }
        }

        System.out.println("Done!");
    }

    /**
     * adds the items that are supposed to be perpendicular to a Pit, Wumpus, and Gold
     *
     * @param centerX x location of the Pit or Wumpus
     * @param centerY y location of the Pit or Wumpus
     * @param piece   piece to add to the map
     */
    private void loadPerpendicular(final int centerX, final int centerY, GamePiece piece) {
        int x = centerX;
        int y = centerY + 1;
        if (inBounds(y) && inBounds(x)) {
            grid[x][y].addPiece(piece);
        }

        x = centerX;
        y = centerY - 1;
        if (inBounds(y) && inBounds(x)) {
            grid[x][y].addPiece(piece);
        }

        x = centerX + 1;
        y = centerY;
        if (inBounds(y) && inBounds(x)) {
            grid[x][y].addPiece(piece);
        }

        x = centerX - 1;
        y = centerY;
        if (inBounds(y) && inBounds(x)) {
            grid[x][y].addPiece(piece);
        }
    }

    /**
     * checks if the int is between 0 and 3
     *
     * @param value value to check
     * @return true if value is 0 to 3
     */
    private boolean inBounds(final int value) {
        return (0 <= value) && (value < 4);
    }

    /**
     * gets the block that has the gold
     * @return the block that is the destination
     * */
    public Block getDestinationBlock(){
        Block destination = null;

        boolean isGoal = false;
        for (int xI = 0; xI < 4; xI++) {
            if(isGoal){
                break;
            }
            for (int yI = 0; yI < 4; yI++) {
                isGoal = grid[xI][yI].hasGlitter();
                if(isGoal){
                    destination = grid[xI][yI];
                    break;
                }
            }
        }

        return destination;
    }

    /**
     * gets the distance from the block to the goal
     *
     * @param block the block to check
     * @return the cost to get from the block to the goal
     * */
    public int getCostToGoalFromBlock(Block block) {
        Block.Point start = block.getPoint();
        Block.Point end = this.getDestinationBlock().getPoint();
        return Util.getDistance(start, end);
    }

    /**
     * @return true if the shooting the arrow with the position will kill wumpus
     * @param pos the direction the player is facing and shooting the arrow
     * @param block the block the arrow is being shot from that the the player is on
     * */
    public boolean willHitWumpus(Block block, Player.Position pos) {
        int x = block.getPoint().getX();
        int y = block.getPoint().getY();

        boolean isHit = false;

        switch (pos){
            case UP:
                for (int y1 = y; y1 < 4; y1++) {
                    isHit = grid[x][y1].hasWumpus();
                }
                break;
            case DOWN:
                for (int y1 = y; y1 >= 0; y1--) {
                    isHit = grid[x][y1].hasWumpus();
                }
                break;
            case LEFT:
                for (int x1 = x; x1 >= 0; x1--) {
                    isHit = grid[x1][y].hasWumpus();
                }
                break;
            case RIGHT:
                for (int x1 = x; x1 < 4; x1++) {
                    isHit = grid[x1][y].hasWumpus();
                }
                break;
        }


        return isHit;
    }

    public void movePlayerRight(){
        Block playerBlock = getPlayerBlock();
        Block.Point point = playerBlock.getPoint();
        boolean result = movePlayer(point.getX(), point.getY(), point.getX() + 1, point.getY());

        if(!result) Log.e("movePlayerRight()", ""+result);
    }

    public void movePlayerUp(){
        Block playerBlock = getPlayerBlock();
        Block.Point point = playerBlock.getPoint();
        boolean result = movePlayer(point.getX(), point.getY(), point.getX(), point.getY() + 1);

        if(!result) Log.e("movePlayerUp()", ""+result);
    }

    public void movePlayerLeft(){
        Block playerBlock = getPlayerBlock();
        Block.Point point = playerBlock.getPoint();
        boolean result = movePlayer(point.getX(), point.getY(), point.getX() - 1, point.getY());

        if(!result) Log.e("movePlayerLeft()", ""+result);
    }

    public void movePlayerDown(){
        Block playerBlock = getPlayerBlock();
        Block.Point point = playerBlock.getPoint();
        boolean result = movePlayer(point.getX(), point.getY(), point.getX(), point.getY() - 1);

        if(!result) Log.e("movePlayerDown()", ""+result);
    }

    /***
     * gets the block the player is on
     * */
    public Block getPlayerBlock() {
        boolean foundPlayer = false;
        Block block = null;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                block = grid[x][y];
                foundPlayer = block.hasPlayer();
                if(foundPlayer){
                    return block;
                }
            }
        }
        return block;
    }

    /**
     * @return list of blocks that are adjacent to the block
     * */
    public List<Block> getAdjacentBlocks(Block block){
        return getAdjacentBlocks(block.getPoint().getX(), block.getPoint().getY());
    }

    /**
     * @return list of blocks that are adjacent to the block
     * */
    public List<Block> getAdjacentBlocks(final int x, final int y){
        List<Block> blocks = new ArrayList<>();

        Block b = getBlock(x + 1, y);
        blocks.add(b);

        b = getBlock(x, y + 1);
        blocks.add(b);

        b = getBlock(x - 1, y);
        blocks.add(b);

        b = getBlock(x, y - 1);
        blocks.add(b);

        return blocks;
    }

    /**
     * moves the player from (x0, y0) to (x1, y1)
     * returns true if the player was moved
     * */
    private boolean movePlayer(final int x0, final int y0, final int x1, final int y1){
        if(!inBounds(x0) || !inBounds(x1) || !inBounds(y0) || !inBounds(y1)){
            return false;
        }
        Block from = this.getBlock(x0, y0);
        Block to = this.getBlock(x1, y1);

        final Player player = from.removePlayer();

        return to.addPiece(player);
    }

    /**
     * @return the list of blocks to put into the ListAdapter in the order needed
     * */
    public List<Block> getListOfBlocks(){
        List<Block> data = new ArrayList<>();

        for (int y = 3; y >= 0; y--) {
            for (int x = 0; x < 4; x++) {
                Block b = this.grid[x][y];
                data.add(b);
            }
        }

        return data;
    }

    /**
     * removes wumpus from the game map
     * */
    public void killWumpus() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Block b = grid[x][y];
                if(b.hasWumpus()){
                    b.killWumpus();
                }
            }
        }
    }
}
