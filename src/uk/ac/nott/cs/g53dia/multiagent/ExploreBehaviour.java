package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import static java.lang.Math.abs;


public class ExploreBehaviour extends Behaviour {


    public ExploreBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


    public Action act(ExploredMap exploredMap) {

        if (agent.previousBehaviour != BehaviourType.EXPLORE_BEHAVIOUR || abs(agent.getPosition().getX()) > 199 || abs(agent.getPosition().getY()) > 199) {
            if (agent.getPosition().distanceTo(agent.origin) > 190) {
                agent.agentDestination = agent.origin;
            } else {
                agent.agentDestination = agent.errorDestination;
            }
        }

        return new MoveTowardsAction(agent.agentDestination);

    }


}
