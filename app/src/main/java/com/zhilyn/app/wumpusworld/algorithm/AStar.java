package com.zhilyn.app.wumpusworld.algorithm;

import android.util.Log;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deep on 5/2/16.
 * the implementation of the A* algorithm
 */
public class AStar {
    private static final String TAG = "AStar";

    private GameMap map;

    private AStar(GameMap map) {
        this.map = map;
    }

    public static List<DecisionNode> getSolution(GameMap map) {
        AStar aStar = new AStar(map);

        //list of decision nodes in order
        List<DecisionNode> solution = new ArrayList<>();
        //list of decision nodes in reverse order
        List<DecisionNode> path = new ArrayList<>();

        DecisionNode current = aStar.makeNodePath();
        while (current != null){
            path.add(current);
            current = current.parent;
        }

        //loops from end to beginning adding the values to the ordered array
        for (int i = path.size() - 1; i >= 0; i--) {
            DecisionNode node = path.get(i);
            solution.add(node);
        }

        solution.add(new DecisionNode(map.getDestinationBlock()));

        return solution;
    }

    /**
     * core of the implementation of A*
     * @return tree to the solution
     * */
    private DecisionNode makeNodePath() {
        Block start = this.map.getPlayerBlock();
        Block end = this.map.getDestinationBlock();
        if(start == null || end == null){
            Log.e("makeNodePath(map)", "start or end is null");
        }

        //nodes that are opened but need to be solved
        List<DecisionNode> opened = new ArrayList<>();
        //nodes that are already solved and have their valued calculated
        List<DecisionNode> closed = new ArrayList<>();

        DecisionNode solution = null;

        //add starting node to list of opened nodes
        DecisionNode startNode = new DecisionNode(start);
        opened.add(startNode);

        int loopCount = 0;
        while (!opened.isEmpty()) {
            loopCount += 1;
            if(shouldStop(loopCount)){
                break;
            }
            DecisionNode current = nodeWithLowestF(opened);
            if(current == null){
                break;
            }
            //removed current from the open list
            opened.remove(current);
            closed.add(current);

            //generate the neighbors for the current node and set the neighbor parent as the current node
            current.generateNeighbors(map);

            Block currentNodeBlock = current.getBlock();

            //the path is already found
            if(currentNodeBlock.hasGold() || (currentNodeBlock == end)){
                Log.e("A-star", "found solution");
                return current;
            }

            //for each neighboring node try to calculate the path and costs
            List<DecisionNode> neighbors = current.getNeighbors();
            for (DecisionNode neighbor : neighbors) {
                //if the neighbor is not traversable or it is in the closed list
                boolean traversable = neighbor.isTraversable() ;
                if(!traversable || closed.contains(neighbor)){
                    continue;
                }

                //add the neighbor to opened list if it's not in the opened list already
                if(!opened.contains(neighbor)){
                    opened.add(neighbor);
                }

                //calculate the new final path value of the neighbor node
                int nextF = neighbor.calculateF();

                //if the new path to the neighbor is shorter or the neighbor is not in the open list
                if((nextF < current.fCost()) || !opened.contains(neighbor) || !closed.contains(neighbor)){
                    //calculate the neighbor's F cost
                    neighbor.calculateF();

                    //set parent of the neighbor to current node so it can be traversed
                    neighbor.parent = current;

                }
            }

            closed.add(current);

        }

        Log.e("opened", ""+opened.size());
        Log.e("closed", ""+closed.size());
        Log.e("loopCount", ""+loopCount);

        return solution;

    }

    private DecisionNode nodeWithLowestF(List<DecisionNode> opened) {
        DecisionNode selected = null;
        //iterate through the decision nodes
        for (DecisionNode node : opened) {
            if(selected == null){
                selected = node;
                continue;
            }
            //if the f cost is == the final cost of the selected
            if(node.fCost() == selected.fCost()){
                //and if the h cost is less than selected
                if(node.hCost() < selected.hCost()){
                    selected = node;
                }
                //selected = node;
            }else if(node.fCost() < selected.fCost()){
                selected = node;
            }
        }
        return selected;
    }

    private boolean shouldStop(final int loopCount) {
        return loopCount >= 1000;
    }
}
