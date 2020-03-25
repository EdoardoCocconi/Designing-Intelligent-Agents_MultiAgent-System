package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multiagent.Behaviour.BehaviourType;
import uk.ac.nott.cs.g53dia.multilibrary.*;

import static java.lang.Math.abs;


public class DemoLitterAgent extends LitterAgent {

    final FleetControlCentre fleetControlCentre;
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

    final int finalTime = 10000;
    public Point agentDestination;
    final Point origin = new Point(0, 0);

    public BehaviourType previousBehaviour;
    public BehaviourType nextBehaviour;


    public DemoLitterAgent(FleetControlCentre fleetControlCentre, int agentID) {

        this.fleetControlCentre = fleetControlCentre;
        this.agentID = agentID;

        errorDestination = fleetControlCentre.choseErrorDestination(agentID);
        errorDestinationX = errorDestination.getX();
        errorDestinationY = errorDestination.getY();

        rechargeBehaviour = new RechargeBehaviour(this);
        exploreBehaviour = new ExploreBehaviour(this);
        collectBehaviour = new CollectBehaviour(this);
        disposeBehaviour = new DisposeBehaviour(this);
        litterDetector = new LitterDetector(this);
        rechargeDetector = new RechargeDetector(this);
        stationDetector = new StationDetector(this);

    }


    private BehaviourType sense(ExploredMap exploredMap, long timestep) {

        double currentLitter = this.getLitterLevel();
        BehaviourType nextBehaviour;

        if (rechargeDetector.isRechargeInRange(exploredMap, timestep)) {
            nextBehaviour = BehaviourType.BATTERY_BEHAVIOUR;
        } else if (currentLitter != (double) LitterAgent.MAX_LITTER && (!litterDetector.readSensor(exploredMap).equals(errorDestination))) {
            nextBehaviour = BehaviourType.COLLECT_BEHAVIOUR;
        } else if (currentLitter != 0 && !stationDetector.readSensor(exploredMap).equals(errorDestination)) {
            nextBehaviour = BehaviourType.DUMP_BEHAVIOUR;
        } else {
            nextBehaviour = BehaviourType.EXPLORE_BEHAVIOUR;
        }

        return nextBehaviour;

    }


    private Action act(BehaviourType nextState) {
        Action resultAction = null;

        switch (nextState) {

            case BATTERY_BEHAVIOUR:
                resultAction = rechargeBehaviour.act(fleetControlCentre.exploredMap);
                break;
            case EXPLORE_BEHAVIOUR:
                resultAction = exploreBehaviour.act(fleetControlCentre.exploredMap);
                break;
            case COLLECT_BEHAVIOUR:
                resultAction = collectBehaviour.act(fleetControlCentre.exploredMap);
                break;
            case DUMP_BEHAVIOUR:
                resultAction = disposeBehaviour.act(fleetControlCentre.exploredMap);
                break;
        }


        return resultAction;
    }


    public Action senseAndAct(Cell[][] view, long timestep) {

//        System.out.println("Agent " + agentID + this.getPosition() + " score: " + this.getScore() + " map size: " + exploredMap.map.size());

        fleetControlCentre.exploredMap.updateMap(view);
        previousBehaviour = nextBehaviour;
        nextBehaviour = sense(fleetControlCentre.exploredMap, timestep);

//        if (timestep % 100 == 0)
//            System.out.println(timestep);

        if (abs(this.getPosition().getX()) > 200 || abs(this.getPosition().getY()) > 200) {
            System.out.println(this.getPosition());
            System.out.println("Agent: " + agentID + " " + nextBehaviour);
        }
        return act(nextBehaviour);

    }
}
