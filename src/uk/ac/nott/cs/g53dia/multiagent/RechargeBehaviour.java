package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class RechargeBehaviour extends Behaviour {

    public RechargeBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


    public Action act(ExploredMap exploredMap){

        agent.agentDestination = new RechargeDetector(agent).readSensor(exploredMap);

        if (agent.getPosition().equals(agent.agentDestination)) {
            return new RechargeAction();
        }
        return new MoveTowardsAction(agent.agentDestination);

    }

}