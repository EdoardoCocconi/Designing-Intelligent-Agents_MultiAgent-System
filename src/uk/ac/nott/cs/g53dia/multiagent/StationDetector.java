package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class StationDetector extends Sensor {


    public StationDetector(DemoLitterAgent agent) {
        super(agent);
    }


    public Point readSensor(ExploredMap exploredMap) {

        int maxDistance = 30;
        Point position = agent.getPosition();
        Point closestStation = agent.errorDestination;
        int distanceToStation = position.distanceTo(closestStation);

        if (agent.getWasteLevel() != 0) {

            for (Point wasteStation : agent.fleetControlCentre.exploredMap.wasteStationPoints) {
                if (position.distanceTo(wasteStation) < distanceToStation) {
                    distanceToStation = position.distanceTo(wasteStation);
                    closestStation = wasteStation;
                }
            }

        } else {

            for (Point recyclingStation : agent.fleetControlCentre.exploredMap.recyclingStationPoints) {
                if (position.distanceTo(recyclingStation) < distanceToStation) {
                    distanceToStation = position.distanceTo(recyclingStation);
                    closestStation = recyclingStation;
                }
            }

        }

        if (distanceToStation > maxDistance)
            closestStation = agent.errorDestination;

        return closestStation;

    }

}
