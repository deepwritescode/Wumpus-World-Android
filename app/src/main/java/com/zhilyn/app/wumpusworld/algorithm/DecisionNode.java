package com.zhilyn.app.wumpusworld.algorithm;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.Util;
import com.zhilyn.app.wumpusworld.world.pieces.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 5/2/16.
 * This class represents a decision made on a block
 *      this class calculates the cost to make a move,
 *      and the cost to get to the goal from the decision made
 */
public class DecisionNode {

    //number values for moves
    private static final int MOVE_GRAB_GOLD = 0;
    private static final int MOVE_UDLR = 1;
    private static final int MOVE_SHOOT = 10;
    private static final int MOVE_DANGER = 1000;

    public DecisionNode parent = null;

    //the block to make the move on
    private Block block;
    //the neighbors of the current node block
    private List<DecisionNode> neighbors = null;

    //distance to the goal from this block and cost to move
    private int heuristic = 0;
    //distance from the beginning
    private int gCost = 0;
    //final cost (g+h)
    private int fCost = 0;

    public boolean isTraversable(){
        return block != null;
    }

    /**
     * A Star says f(n) = g(n) + h(n)
     * */
    public int calculateF() {
        this.heuristic = calculateHeuristic();
        this.gCost = calculateG();
        return this.fCost = this.heuristic + this.gCost;
    }

    /**
     * calculates cost to reach this node
     * @return the cost to get to this node
     * */
    public int calculateG() {
        if(parent != null){
            return MOVE_UDLR + parent.calculateG();
        }
        return 0;
    }

    /**
     * finds the cost to get to goal from this node
     * if there is a pit or wumpus then it will calculate accordingly
     * @return heuristic cost to move from this
     */
    public int calculateHeuristic() {
        int costToGoal = GameMap.instance.getCostToGoalFromBlock(this.block);

        if(this.block.hasGold()) {
            return this.heuristic = 0;
        }

        if(this.block.hasPit()){
            return this.heuristic = MOVE_DANGER + costToGoal;
        }

        if(this.block.hasWumpus()){
            return this.heuristic = MOVE_SHOOT + costToGoal;
        }

        return this.heuristic = costToGoal;
    }

    /**
     * @return the total cost
     * */
    public int fCost(){
        return this.fCost;
    }

    public int hCost() {
        return this.heuristic;
    }

    public int gCost() {
        return this.gCost;
    }

    /**
     * generates the neighboring nodes
     * */
    public void generateNeighbors(GameMap map) {
        List<DecisionNode> neighbors = new ArrayList<>();
        List<Block> blocks = map.getAdjacentBlocks(this.block);

        for (Block neighbor : blocks) {
            DecisionNode node = new DecisionNode(neighbor);
            node.parent = this;
            neighbors.add(node);
        }

        this.neighbors = neighbors;
    }

    //move that can be made
    public enum Move {
        UP, DOWN,
        LEFT, RIGHT,
        SHOOT, GRAB
    }

    /**
     * @param current       the block linked to this node
     */
    public DecisionNode(Block current) {
        this.block = current;
    }

    /**
     * @param current   the block this node is linked to
     * @param map       the game map
     * @param gCost     the cost so far to get to the block
     * */
    public static DecisionNode make(GameMap map, Block current, int gCost){
        List<DecisionNode> neighbors = new ArrayList<>();
        List<Block> blocks = map.getAdjacentBlocks(current);

        for (Block neighbor : blocks) {
            neighbors.add(new DecisionNode(neighbor));
        }

        return new DecisionNode(current);
    }

    /**
     * @return list of neighboring decision nodes for this current node
     * */
    public List<DecisionNode> getNeighbors() {
        return neighbors;
    }

    /**
     * the block the node is making decisions on/for
     * */
    public Block getBlock() {
        return block;
    }

    /**
     * @return the best move suggested to be made
     * */
    public Move getBestMove(){
        Move m = Move.GRAB;
        //decision node for the best decision that can be made
        DecisionNode leastCost = neighbors.get(0);

        //loop through the neighboring nodes to find the decision node with least F cost
        for (DecisionNode neighbor : neighbors) {
            if(neighbor.fCost() > leastCost.fCost()){
                continue;
            }

            if(neighbor.fCost() < leastCost.fCost()){
                leastCost = neighbor;
                continue;
            }

            if(neighbor.fCost() == leastCost.fCost()){
                if(neighbor.hCost() < leastCost.hCost()) {
                    leastCost = neighbor;
                    continue;
                }

                if(neighbor.hCost() == leastCost.hCost()){
                    //the f cost and the h cost are equal, choose a block with the least dangers
                    Block least = leastCost.block;
                    Block neighborBlock = neighbor.block;

                    if(neighborBlock.hasGold() || neighborBlock.hasGlitter()){
                        leastCost = neighbor;
                        break;
                    }
                }
            }
        }

        if(Util.isAboveBlock(this.block, leastCost.block)){
            m = Move.UP;
        }
        if(Util.isBelowBlock(this.block, leastCost.block)){
            m = Move.DOWN;
        }
        if(Util.isLeftBlock(this.block, leastCost.block)){
            m = Move.LEFT;
        }
        if(Util.isRightBlock(this.block, leastCost.block)){
            m = Move.RIGHT;
        }

        for (DecisionNode neighbor : neighbors) {
            Block neighborBlock = neighbor.block;

            if(neighborBlock.hasPit()){
                continue;
            }

            if(Util.isAboveBlock(this.block, neighborBlock)){
                m = Move.UP;
            }
            if(Util.isBelowBlock(this.block, neighborBlock)){
                m = Move.DOWN;
            }
            if(Util.isLeftBlock(this.block, neighborBlock)){
                m = Move.LEFT;
            }
            if(Util.isRightBlock(this.block, neighborBlock)){
                m = Move.RIGHT;
            }

        }
        return m;
    }
}
