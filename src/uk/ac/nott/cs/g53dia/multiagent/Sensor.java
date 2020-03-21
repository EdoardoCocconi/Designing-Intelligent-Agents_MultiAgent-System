package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.LitterAgent;
import uk.ac.nott.cs.g53dia.multilibrary.Point;


public abstract class Sensor {

    protected DemoLitterAgent agent;


    public Sensor(LitterAgent agent) {
        this.agent = (DemoLitterAgent)agent;
    }


    public abstract Point readSensor(ExploredMap exploredMap);

}
