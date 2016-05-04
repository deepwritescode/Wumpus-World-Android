package com.zhilyn.app.wumpusworld.algorithm;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Player;

/**
 * Created by Deep on 5/2/16.
 * This class represents a decision made on a block
 *      this class calculates the cost to make a move,
 *      and the cost to get to the goal from the decision made
 */
public class Decision {
    //if the move cant be made
    private static final int NOT_POSSIBLE = -2;
    //if there is no heuristic value for the decision
    private static final int NOT_ADVISED = -1;

    //if there is a heuristic value for the decision
    private static final int MOVE_UDLR = 1;
    private static final int MOVE_SHOOT = 10;
    private static final int MOVE_GRAB = 0;

    //the move to make
    private final Move move;
    //the block to make the move on
    private final Block block;

    //heuristic cost, the cost to make the move to the block
    private final int heuristic;
    //cost to get to the goal from the block that the player is on without obstacles
    private int goalCost;

    //the game map
    private final GameMap map;

    public Move getMove() {
        return move;
    }

    //move that can be made
    public enum Move {
        UP, DOWN,
        LEFT, RIGHT,
        SHOOT, GRAB
    }

    /**
     * @param currentBlock the block to make the move on
     * @param move         the move to make on the block
     */
    public Decision(Move move, Block currentBlock, GameMap map) {
        this.map = map;
        this.block = currentBlock;
        this.move = move;
        this.goalCost = map.getCostToGoalFromBlock(block);
        this.heuristic = calculateHeuristic();
    }

    /**
     * @return the distance cost to get to the goal
     * */
    public int getHeuristic(){
        return this.heuristic;
    }

    /**
     * @return the cost it takes to get to the destination
     * */
    public int getGoalCost(){
        return this.goalCost;
    }

    /**
     * @return the total cost
     * */
    public int getTotalCost(){
        return this.heuristic + this.goalCost;
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to move up, with all the calculations
     * */
    public static Decision up(Block block, GameMap map) {
        return new Decision(Move.UP, block, map);
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to move down, with all the calculations
     * */
    public static Decision down(Block block, GameMap map) {
        return new Decision(Move.DOWN, block, map);
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to move left, with all the calculations
     * */
    public static Decision left(Block block, GameMap map) {
        return new Decision(Move.LEFT, block, map);
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to move right, with all the calculations
     * */
    public static Decision right(Block block, GameMap map) {
        return new Decision(Move.RIGHT, block, map);
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to shoot from, with all the calculations
     * */
    public static Decision shoot(Block block, GameMap map) {
        return new Decision(Move.SHOOT, block, map);
    }

    /**
     * @param block the block to make the move on
     * @param map the game map that the block is on
     * @return the decision to try to grab on, with all the calculations
     * */
    public static Decision grab(Block block, GameMap map) {
        return new Decision(Move.GRAB, block, map);
    }

    /**
     * @return heuristic cost to execute this.move on the this.block
     */
    public int calculateHeuristic() {
        switch (move) {
            case GRAB:
                if (block.hasGlitter()) {
                    return MOVE_GRAB;
                }else{
                    return NOT_POSSIBLE;
                }
            case RIGHT:
                Block blockRight = map.getBlockRight(block);
                if (blockRight == null) {
                    return NOT_POSSIBLE;
                }
                if (blockRight.hasWumpus() || blockRight.hasPit()) {
                    return NOT_ADVISED;
                }
                goalCost = map.getCostToGoalFromBlock(blockRight);
                return MOVE_UDLR;
            case UP:
                Block blockUp = map.getBlockUp(block);
                if (blockUp == null) {
                    return NOT_POSSIBLE;
                }
                if (blockUp.hasWumpus() || blockUp.hasPit()) {
                    return NOT_ADVISED;
                }
                goalCost = map.getCostToGoalFromBlock(blockUp);
                return MOVE_UDLR;
            case LEFT:
                Block blockLeft = map.getBlockLeft(block);
                if (blockLeft == null) {
                    return NOT_POSSIBLE;
                }
                if (blockLeft.hasWumpus() || blockLeft.hasPit()) {
                    return NOT_ADVISED;
                }
                goalCost = map.getCostToGoalFromBlock(blockLeft);
                return MOVE_UDLR;
            case DOWN:
                Block blockDown = map.getBlockDown(block);
                if (blockDown == null) {
                    return NOT_POSSIBLE;
                }
                if (blockDown.hasWumpus() || blockDown.hasPit()) {
                    return NOT_ADVISED;
                }
                goalCost = map.getCostToGoalFromBlock(blockDown);
                return MOVE_UDLR;
            case SHOOT:
                boolean willHit = map.willHitWumpus(block, Player.Position.RIGHT);
                if (willHit) {
                    goalCost = map.getCostToGoalFromBlock(block);
                    return MOVE_SHOOT;
                } else {
                    willHit = map.willHitWumpus(block, Player.Position.UP);
                    if (willHit) {
                        goalCost = map.getCostToGoalFromBlock(block);
                        return MOVE_SHOOT;
                    } else {
                        willHit = map.willHitWumpus(block, Player.Position.LEFT);
                        if (willHit) {
                            goalCost = map.getCostToGoalFromBlock(block);
                            return MOVE_SHOOT;
                        } else {
                            willHit = map.willHitWumpus(block, Player.Position.DOWN);
                            if (willHit) {
                                goalCost = map.getCostToGoalFromBlock(block);
                                return MOVE_SHOOT;
                            }
                        }
                    }
                }
                break;
        }

        return NOT_ADVISED;
    }
}
