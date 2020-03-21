package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public abstract class Behaviour {

    protected DemoLitterAgent agent;

    public enum BehaviourType {
        BATTERY_BEHAVIOUR,
        EXPLORE_BEHAVIOUR,
        COLLECT_BEHAVIOUR,
        DUMP_BEHAVIOUR;
    }


    public Behaviour(LitterAgent agent) {
        this.agent = (DemoLitterAgent)agent;
    }


    public abstract Action act(ExploredMap exploredMap);

}
