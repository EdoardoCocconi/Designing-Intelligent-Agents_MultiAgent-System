package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;


public class StationDetector extends Sensor {


    public StationDetector(DemoLitterAgent agent) {
        super(agent);
    }


    public Point readSensor(ExploredMap exploredMap) {

        int size = 30;
        Point position = agent.getPosition();
        Cell[][] view = exploredMap.getView(position, size);
        Point destination = agent.errorDestination;

        if (agent.getWasteLevel() != 0) {

            for (Cell[] row : view) {
                for (Cell cell : row) {
                    if (agent.exploredMap.isCellAllowed(agent, cell)) {
                        if (cell instanceof WasteStation) {
                            if (agent.getPosition().distanceTo(cell.getPoint()) < agent.getPosition().distanceTo(destination)) {
                                destination = cell.getPoint();
                            }
                        }
                    }
                }
            }

        } else {


            for (Cell[] row : view) {
                for (Cell cell : row) {
                    if (agent.exploredMap.isCellAllowed(agent, cell)) {
                        if (cell instanceof RecyclingStation) {
                            if (agent.getPosition().distanceTo(cell.getPoint()) < agent.getPosition().distanceTo(destination)) {
                                destination = cell.getPoint();
                            }
                        }
                    }
                }
            }

        }

        return destination;

    }

}
