package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class DisposeBehaviour extends Behaviour {


    public DisposeBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


    public Action act(ExploredMap exploredMap) {

        agent.agentDestination = agent.stationDetector.readSensor(exploredMap);

        if (agent.getPosition().equals(agent.agentDestination)) {
            return new DisposeAction();
        } else {
            return new MoveTowardsAction(agent.agentDestination);
        }

    }

}