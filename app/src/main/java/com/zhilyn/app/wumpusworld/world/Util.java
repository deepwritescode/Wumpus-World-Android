package com.zhilyn.app.wumpusworld.world;

import com.zhilyn.app.wumpusworld.world.pieces.Block;

/**
 * Created by Deep on 5/4/16.
 * utilities class that helps with common functions of the game map
 */
public class Util {


    /**
     * checks if the other block is above the center block
     * @return true if other is above center
     * @param center    the center block to use as an anchor
     * @param other     the other block to check
     * */
    public static boolean isAboveBlock(Block center, Block other){
        if(other == null){
            return false;
        }
        final int x0 = center.getPoint().getX();
        final int y0 = center.getPoint().getY();
        final int x1 = other.getPoint().getX();
        final int y1 = other.getPoint().getY();

        if((y0 + 1) == y1){
            if(x1 == x0) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the other block is below the center block
     * @return true if other is above center
     * @param center    the center block to use as an anchor
     * @param other     the other block to check
     * */
    public static boolean isBelowBlock(Block center, Block other){
        if(other == null){
            return false;
        }
        final int x0 = center.getPoint().getX();
        final int y0 = center.getPoint().getY();
        final int x1 = other.getPoint().getX();
        final int y1 = other.getPoint().getY();

        if((y0 - 1) == y1){
            if(x1 == x0) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the other block is to the right of the center block
     * @return true if other is above center
     * @param center    the center block to use as an anchor
     * @param other     the other block to check
     * */
    public static boolean isRightBlock(Block center, Block other){
        if(other == null){
            return false;
        }
        final int x0 = center.getPoint().getX();
        final int y0 = center.getPoint().getY();
        final int x1 = other.getPoint().getX();
        final int y1 = other.getPoint().getY();

        if(y1 == y0){
            if((x0 + 1) == x1) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the other block is to the left of the center block
     * @return true if other is above center
     * @param center    the center block to use as an anchor
     * @param other     the other block to check
     * */
    public static boolean isLeftBlock(Block center, Block other){
        if(other == null){
            return false;
        }
        final int x0 = center.getPoint().getX();
        final int y0 = center.getPoint().getY();
        final int x1 = other.getPoint().getX();
        final int y1 = other.getPoint().getY();

        if(y1 == y0){
            if((x0 - 1) == x1){
                return true;
            }
        }
        return false;
    }

    /**
     * returns the distance from point a to point b
     * @param pointA the starting point
     * @param pointB the ending point
     * */
    public static int getDistance(Block.Point pointA, Block.Point pointB){
        int x1 = pointA.getX() + 1;
        int y1 = pointA.getY() + 1;

        int x2 = pointB.getX() + 1;
        int y2 = pointB.getY() + 1;
        return ((x2 - x1) + (y2 - y1));
    }
}
