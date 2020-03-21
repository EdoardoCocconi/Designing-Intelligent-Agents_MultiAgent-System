package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class DemoMAS extends MAS {

    private static int MAS_SIZE = 4;


    public DemoMAS() {
        // Create the agents
        for (int i = 0; i < MAS_SIZE; i++) {
            this.add(new DemoLitterAgent(i));
        }
    }
}
