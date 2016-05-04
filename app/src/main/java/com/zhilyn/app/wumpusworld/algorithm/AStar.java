package com.zhilyn.app.wumpusworld.algorithm;

import android.util.Log;

import com.zhilyn.app.wumpusworld.ListAdapter;
import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 5/2/16.
 * the implementation of the A* algorithm
 */
public class AStar {
    private static final String TAG = "AStar";

    //the least cost to the goal, from (0, 0) to the block with glitter (x1, y1)
    private final int LEAST_COST;
    private final Block startingBlock;

    public AStar(Block start, GameMap map, ListAdapter adapter) {
        this.startingBlock = start;
        this.LEAST_COST = map.getCostToGoalFromBlock(startingBlock);

        Player player = startingBlock.getPlayer();

        List<DecisionNode.Move> solution = getSolution(map, map.getPlayerBlock(), map.getDestinationBlock());
        for (DecisionNode.Move move : solution) {
            switch (move) {
                case RIGHT:
                    map.movePlayerRight();
                    break;
                case UP:
                    map.movePlayerUp();
                    break;
                case LEFT:
                    map.movePlayerLeft();
                    break;
                case DOWN:
                    map.movePlayerDown();
                    break;
                case GRAB:
                    adapter.onWin();
                    continue;
                case SHOOT:
                    map.killWumpus();
                    break;
            }
            adapter.updateData(map);
        }
    }

    private List<DecisionNode.Move> getSolution(GameMap map, Block start, Block end) {
        List<DecisionNode> tree = makePathTree(map);
        List<DecisionNode.Move> moves = new ArrayList<>();

        //gets the last node
        DecisionNode current = tree.get(tree.size() - 1);

        boolean continueLooping = true;
        while (continueLooping){
            if(current.parent == null){
                continueLooping = false;
            }
            DecisionNode.Move move = current.getBestMove();
            moves.add(move);

            current = current.parent;
        }

        return moves;
    }

    /**
     * core of the implementation of A*
     * */
    private List<DecisionNode> makePathTree(GameMap map) {
        Block start = map.getPlayerBlock();
        Block end = map.getDestinationBlock();
        if(start == null || end == null){
            Log.e("makePathTree(map)", "start or end is null");
        }

        //nodes that are opened but need to be solved
        List<DecisionNode> opened = new ArrayList<>();
        //nodes that are already solved and have their valued calculated
        List<DecisionNode> closed = new ArrayList<>();


        //add starting node to list of opened nodes
        DecisionNode startNode = new DecisionNode(start);
        opened.add(startNode);

        int loopCount = 0;
        while (!opened.isEmpty() || !shouldStop(loopCount)) {
            DecisionNode current = nodeWithLowestF(opened);
            if(current == null){
                break;
            }
            //removed current from the open list
            opened.remove(current);
            closed.add(current);

            //generate the neighbors for the current node and set the neighbor parent as the current node
            current.generateNeighbors();

            Block currentNodeBlock = current.getBlock();

            //the path is already found
            if(currentNodeBlock.hasGold() || currentNodeBlock.hasGlitter() || (currentNodeBlock == end)){
                break;
            }

            //for each neighboring node try to calculate the path and costs
            List<DecisionNode> neighbors = current.getNeighbors();
            for (DecisionNode neighbor : neighbors) {
                //if the neighbor is not traversable or it is in the closed list
                Block neighboringBlock = neighbor.getBlock();
                boolean notTraversable = neighboringBlock == null || neighboringBlock.hasPit();
                if(notTraversable || closed.contains(neighbor)){
                    continue;
                }

                //calculate the new final path value of the neighbor node
                int newFValue = neighbor.calculateF();

                //if the new path to the neighbor is shorter or the neighbor is not in the open list
                if((newFValue < current.fCost()) || !opened.contains(neighbor)){
                    //calculate the neighbor's F cost
                    neighbor.calculateF();

                    //set parent of the neighbor to current node so it can be traversed
                    neighbor.parent = current;

                    //add the neighbor to opened list if it's not in the opened list already
                    if(!opened.contains(neighbor)){
                        opened.add(neighbor);
                    }
                }
            }

        }

        return closed;

    }

    private DecisionNode nodeWithLowestF(List<DecisionNode> opened) {
        DecisionNode selected = opened.get(0);
        //iterate through the decision nodes
        for (DecisionNode node : opened) {
            //if the f cost is == the final cost of the selected
            if(node.fCost() == selected.fCost()){
                //and if the h cost is less than selected
                if(node.hCost() < selected.hCost()){
                    selected = node;
                }
            }else if(node.fCost() < selected.fCost()){
                selected = node;
            }
        }
        return selected;
    }

    private boolean shouldStop(int loopCount) {
        return loopCount > 1000;
    }
}
