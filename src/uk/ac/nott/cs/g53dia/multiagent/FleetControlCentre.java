package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.Point;

import java.util.HashMap;
import java.util.Map;


public class FleetControlCentre {

    ExploredMap exploredMap;
    public Map<Integer, Point> destinationList = new HashMap<>();
    int currentAgentID;

    public FleetControlCentre() {
        exploredMap = new ExploredMap();
    }


    public Point choseErrorDestination(int agentID) {
        Point errorDestination = null;
        switch (agentID) {
            case 0:
                errorDestination = new Point(99999999, 99999999);
                break;
            case 1:
                errorDestination = new Point(-99999999, 99999999);
                break;
            case 2:
                errorDestination = new Point(-99999999, -99999999);
                break;
            case 3:
                errorDestination = new Point(99999999, -99999999);
                break;
        }

        return errorDestination;
    }


    public void updateDestinationList(int currentAgentID, Point currentAgentDestination) {

        if (!destinationList.containsValue(currentAgentDestination))
            destinationList.put(currentAgentID, currentAgentDestination);

    }


    public void setCurrentAgentID(int currentAgentID) {
        this.currentAgentID = currentAgentID;
    }


}

