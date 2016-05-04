package com.zhilyn.app.wumpusworld.algorithm;

/**
 * Created by Deep on 5/2/16.
 *
 */
public class DecisionTree {

    private Decision decision = null;

    public static DecisionTree UP = null;
    public static DecisionTree DOWN = null;
    public static DecisionTree LEFT = null;
    public static DecisionTree RIGHT = null;
    public static DecisionTree SHOOT = null;
    public static DecisionTree GRAB = null;

    private DecisionTree(Decision decision){
        this.decision = decision;
    }






}
