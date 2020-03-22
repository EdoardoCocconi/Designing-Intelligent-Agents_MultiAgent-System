package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class CollectBehaviour extends Behaviour {


    public CollectBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


     public Action act(ExploredMap exploredMap) {

         agent.agentDestination = agent.litterDetector.readSensor(exploredMap);

        if (agent.getPosition().equals(agent.agentDestination)) {
            LitterBin currentBin = (LitterBin) exploredMap.map.get(agent.agentDestination);
            return new LoadAction(currentBin.getTask());
        }

        return new MoveTowardsAction(agent.agentDestination);

    }

}


