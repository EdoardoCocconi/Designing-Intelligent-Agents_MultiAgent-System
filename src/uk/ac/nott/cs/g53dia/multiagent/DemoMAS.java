package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class DemoMAS extends MAS {

    private static int MAS_SIZE = 2;
    FleetControlCentre fleetControlCentre;


    public DemoMAS() {

        fleetControlCentre = new FleetControlCentre();

        // Create the agents
        for (int agentID = 0; agentID < MAS_SIZE; agentID++) {
            this.add(new DemoLitterAgent(fleetControlCentre, agentID));
        }
    }
}
