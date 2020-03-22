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

    final int agentID;
    Point errorDestination;
    final int errorDestinationX;
    final int errorDestinationY;

    RechargeBehaviour rechargeBehaviour;
    ExploreBehaviour exploreBehaviour;
    CollectBehaviour collectBehaviour;
    DisposeBehaviour disposeBehaviour;
    LitterDetector litterDetector;
    RechargeDetector rechargeDetector;
    StationDetector stationDetector;

    ExploredMap exploredMap;
    final int finalTime = 10000;
    public Point agentDestination;
    Point origin;


    public DemoLitterAgent(int agentID) {

        this.agentID = agentID;

        errorDestination = choseErrorDestination();
        errorDestinationX = errorDestination.getX();
        errorDestinationY = errorDestination.getY();

        rechargeBehaviour = new RechargeBehaviour(this);
        exploreBehaviour = new ExploreBehaviour(this);
        collectBehaviour = new CollectBehaviour(this);
        disposeBehaviour = new DisposeBehaviour(this);
        litterDetector = new LitterDetector(this);
        rechargeDetector = new RechargeDetector(this);
        stationDetector = new StationDetector(this);

        exploredMap = new ExploredMap();
        origin = new Point(0, 0);

    }


    private Point choseErrorDestination() {
        Point errorDestination = null;
        switch (this.agentID) {
            case 0:
                errorDestination = new Point(99999999, 99999999);
                break;
            case 1:
                errorDestination = new Point(-99999999, 99999999);
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

        exploredMap.updateMap(view);

        BehaviourType nextState = sense(exploredMap, timestep);
        return act(nextState);

    }
}
