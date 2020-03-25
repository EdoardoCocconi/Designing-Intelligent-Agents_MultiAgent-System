package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.Point;


public abstract class Sensor {

    protected DemoLitterAgent agent;


    public Sensor(DemoLitterAgent agent) {
        this.agent = agent;
    }


    public abstract Point readSensor(ExploredMap exploredMap);

}
