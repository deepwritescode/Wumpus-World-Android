package com.zhilyn.app.wumpusworld.algorithm;

import android.util.Log;

import com.zhilyn.app.wumpusworld.world.GameMap;
import com.zhilyn.app.wumpusworld.world.pieces.Block;
import com.zhilyn.app.wumpusworld.world.pieces.Player;

/**
 * Created by Deep on 5/2/16.
 * the implementation of the A* algorithm
 */
public class AStar {
    private static final String TAG = "AStar";

    //the least cost to the goal, from (0, 0) to the block with glitter (x1, y1)
    private final int LEAST_COST;
    private final Block startingBlock;

    public AStar(GameMap map){
        this.startingBlock = map.getBlock(0,0);
        this.LEAST_COST = map.getCostToGoalFromBlock(startingBlock);

        Player player = startingBlock.getPlayer();

        Decision right = Decision.right(startingBlock, map);
        Decision up = Decision.up(startingBlock, map);
        Decision left = Decision.left(startingBlock, map);
        Decision down = Decision.down(startingBlock, map);
        Decision shoot = Decision.shoot(startingBlock, map);
        Decision grab = Decision.grab(startingBlock, map);

        Log.e(right.getMove().toString(), "T:"+right.getTotalCost());
        Log.e(up.getMove().toString(), "T:"+up.getTotalCost());
        Log.e(left.getMove().toString(), "T:"+left.getTotalCost());
        Log.e(down.getMove().toString(), "T:"+down.getTotalCost());
        Log.e(shoot.getMove().toString(), "T:"+shoot.getTotalCost());
        Log.e(grab.getMove().toString(), "T:"+grab.getTotalCost());

        Log.e(TAG, "--");

        Log.e(right.getMove().toString(), "H:"+right.getHeuristic());
        Log.e(up.getMove().toString(), "H:"+up.getHeuristic());
        Log.e(left.getMove().toString(), "H:"+left.getHeuristic());
        Log.e(down.getMove().toString(), "H:"+down.getHeuristic());
        Log.e(shoot.getMove().toString(), "H:"+shoot.getHeuristic());
        Log.e(grab.getMove().toString(), "H:"+grab.getHeuristic());

        Log.e(TAG, "--");

        Log.e(right.getMove().toString(), "G:"+right.getGoalCost());
        Log.e(up.getMove().toString(), "G:"+up.getGoalCost());
        Log.e(left.getMove().toString(), "G:"+left.getGoalCost());
        Log.e(down.getMove().toString(), "G:"+down.getGoalCost());
        Log.e(shoot.getMove().toString(), "G:"+shoot.getGoalCost());
        Log.e(grab.getMove().toString(), "G:"+grab.getGoalCost());

        //int rightCost = right.
    }
}
