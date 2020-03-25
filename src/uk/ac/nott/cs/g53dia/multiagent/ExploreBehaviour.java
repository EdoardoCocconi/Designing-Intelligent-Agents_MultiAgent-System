package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class ExploreBehaviour extends Behaviour {


    public ExploreBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


    public Action act(ExploredMap exploredMap) {

        if (agent.previousBehaviour != BehaviourType.EXPLORE_BEHAVIOUR) {
            if (agent.getPosition().distanceTo(agent.origin) > 190) {
                agent.agentDestination = agent.origin;
            } else {
                agent.agentDestination = agent.errorDestination;
            }
        }

        return new MoveTowardsAction(agent.agentDestination);

    }


}
