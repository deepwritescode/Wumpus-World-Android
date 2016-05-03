package com.zhilyn.app.wumpusworld.world;


import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Breeze;
import com.zhilyn.app.wumpusworld.world.pieces.GamePiece;
import com.zhilyn.app.wumpusworld.world.pieces.Glitter;
import com.zhilyn.app.wumpusworld.world.pieces.Gold;
import com.zhilyn.app.wumpusworld.world.pieces.Pit;
import com.zhilyn.app.wumpusworld.world.pieces.Player;
import com.zhilyn.app.wumpusworld.world.pieces.Stench;
import com.zhilyn.app.wumpusworld.world.pieces.Wumpus;

import java.util.Random;

/**
 * Created by Deep on 4/27/16.
 * class containing the Wumpus world map with various constants
 */
public class GameMap {

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

        return map;
    }

    public Block[][] getGrid() {
        return grid;
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

        for (int i = 0; i < 3; i++) {
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

}
