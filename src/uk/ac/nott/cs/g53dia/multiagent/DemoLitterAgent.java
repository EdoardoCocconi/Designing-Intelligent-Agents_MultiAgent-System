package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multiagent.Behaviour.BehaviourType;
import uk.ac.nott.cs.g53dia.multilibrary.*;

import java.util.Random;

/**
 * A simple example LitterAgent
 *
 * @author Julian Zappala
 */
/*
 * Copyright (c) 2011 Julian Zappala
 *
 * See the file "license.terms" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
public class DemoLitterAgent extends LitterAgent {

    int agentID;

    RechargeBehaviour rechargeBehaviour = new RechargeBehaviour(this);
    ExploreBehaviour exploreBehaviour = new ExploreBehaviour(this);
    CollectBehaviour collectBehaviour = new CollectBehaviour(this);
    DisposeBehaviour disposeBehaviour = new DisposeBehaviour(this);

    LitterDetector litterDetector = new LitterDetector(this);
    RechargeDetector rechargeDetector = new RechargeDetector(this);
    StationDetector stationDetector = new StationDetector(this);

    ExploredMap exploredMap = new ExploredMap();
    int finalTime = 10000;

    public Point agentDestination;
    Point errorDestination;


    Point origin = new Point(0, 0);


    public DemoLitterAgent(int agentID) {
        this.agentID = agentID;
        errorDestination = choseErrorDestination();
    }



    private Point choseErrorDestination() {
        Point errorDestination = null;
        System.out.println("this.agentID");
        System.out.println(this.agentID);
        switch (this.agentID) {
            case 0:
                errorDestination = new Point(99999999, 99999999);
                System.out.println("case 0");
                break;
            case 1:
                errorDestination = new Point(-99999999, 99999999);
                System.out.println("case 1");
                break;
            case 2:
                errorDestination = new Point(-99999999, -99999999);
                break;
            case 3:
                errorDestination = new Point(99999999, -99999999);
                break;
        }

        return errorDestination;
    }


    private BehaviourType sense(ExploredMap exploredMap, long timestep) {

        double maxCapacity = LitterAgent.MAX_LITTER;
        double currentLitter = this.getLitterLevel();
        BehaviourType nextState;

        if (rechargeDetector.isRechargeInRange(exploredMap, timestep)) {
            nextState = BehaviourType.BATTERY_BEHAVIOUR;
        } else if (currentLitter != maxCapacity && (currentLitter == 0 || !litterDetector.readSensor(exploredMap).equals(errorDestination))) {
            nextState = BehaviourType.COLLECT_BEHAVIOUR;
        } else if (!stationDetector.readSensor(exploredMap).equals(errorDestination)) {
            nextState = BehaviourType.DUMP_BEHAVIOUR;
        } else {
            nextState = BehaviourType.EXPLORE_BEHAVIOUR;
        }

        return nextState;

    }


    private Action act(BehaviourType nextState) {
        Action resultAction = null;

        switch (nextState) {

            case BATTERY_BEHAVIOUR:
                resultAction = rechargeBehaviour.act(exploredMap);
                break;
            case EXPLORE_BEHAVIOUR:
                resultAction = exploreBehaviour.act(exploredMap);
                break;
            case COLLECT_BEHAVIOUR:
                resultAction = collectBehaviour.act(exploredMap);
                break;
            case DUMP_BEHAVIOUR:
                resultAction = disposeBehaviour.act(exploredMap);
                break;
        }


        return resultAction;
    }


    public Action senseAndAct(Cell[][] view, long timestep) {

        System.out.println(agentID);
        System.out.println("x: " + errorDestination.getX() + "y: " + errorDestination.getY());

        exploredMap.updateMap(view);

        BehaviourType nextState = sense(exploredMap, timestep);
        return act(nextState);

    }
}
