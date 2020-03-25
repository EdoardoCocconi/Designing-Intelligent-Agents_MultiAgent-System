package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class ExploreBehaviour extends Behaviour {

    private Point explorationDestination;


    public ExploreBehaviour(DemoLitterAgent agent) {
        super(agent);
    }


    public Action act(ExploredMap exploredMap) {

        if (agent.previousBehaviour != BehaviourType.EXPLORE_BEHAVIOUR) {
            if (agent.getPosition().distanceTo(agent.origin) > 170) {
                explorationDestination = agent.origin;
            } else {
                explorationDestination = agent.errorDestination;
            }
        }

        return new MoveTowardsAction(explorationDestination);

    }


}
