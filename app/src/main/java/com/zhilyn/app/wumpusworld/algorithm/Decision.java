package com.zhilyn.app.wumpusworld.algorithm;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Player;

/**
 * Created by Deep on 5/2/16.
 * This class represents a decision on a block
 */
public class Decision {
    //if the move cant be made
    private static final int NOT_POSSIBLE = -2;
    //if there is no heuristic value for the decision
    private static final int NOT_ADVISED = -1;

    //if there is a heuristic value for the decision
    private static final int MOVE_UDLR = 1;
    private static final int MOVE_SHOOT = 10;
    private static final int MOVE_GRAB = -1000;

    //the move to make
    private final Move move;
    //the block to make the move on
    private final Block block;

    //heuristic cost
    private final int heuristic;
    //cost to get to the goal
    private int goalCost;

    private final GameMap map;

    public enum Move {
        UP, DOWN, LEFT, RIGHT, SHOOT, GRAB
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
        this.heuristic = calculateHeuristicCost();
    }

    /**
     * @return the cost it takes to make the move to the block
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
     * @return the total cost of making the move
     * */
    public int getTotalCost(){
        return this.heuristic + this.goalCost;
    }

    public static Decision up(Block block, GameMap map) {
        return new Decision(Move.UP, block, map);
    }

    public static Decision down(Block block, GameMap map) {
        Decision decision = new Decision(Move.DOWN, block, map);
        return decision;
    }

    public static Decision left(Block block, GameMap map) {
        return new Decision(Move.LEFT, block, map);
    }

    public static Decision right(Block block, GameMap map) {
        return new Decision(Move.RIGHT, block, map);
    }

    public static Decision shoot(Block block, GameMap map) {
        return new Decision(Move.SHOOT, block, map);
    }

    public static Decision grab(Block block, GameMap map) {
        return new Decision(Move.GRAB, block, map);
    }

    /**
     * @return heuristic cost to execute this.move on the this.block
     */
    public int calculateHeuristicCost() {
        switch (move) {
            case GRAB:
                if (block.hasGlitter()) {
                    return MOVE_GRAB;
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
                    return MOVE_SHOOT;
                } else {
                    willHit = map.willHitWumpus(block, Player.Position.UP);
                    if (willHit) {
                        return MOVE_SHOOT;
                    } else {
                        willHit = map.willHitWumpus(block, Player.Position.LEFT);
                        if (willHit) {
                            return MOVE_SHOOT;
                        } else {
                            willHit = map.willHitWumpus(block, Player.Position.DOWN);
                            if (willHit) {
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
