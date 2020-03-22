package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.ceil;


public class LitterDetector extends Sensor {


    public LitterDetector(DemoLitterAgent agent) {
        super(agent);
    }


    public boolean betterBin(Cell cell, Point previousDestination, int previousScore, double currentCapacity) {
        int previousDistance = agent.getPosition().distanceTo(previousDestination);
        int distance = agent.getPosition().distanceTo(cell.getPoint());
        LitterBin litterBin = (LitterBin) cell;
        if (litterBin.getTask() != null) {
            int score = litterBin.getTask().getRemaining();
            if (score < currentCapacity) {
                if (score / (distance + 1) > previousScore / (previousDistance + 1))
                    return TRUE;
            }
        }
        return FALSE;
    }


    public Point readSensor(ExploredMap exploredMap) {

        int currentWaste = agent.getWasteLevel();
        int currentRecycling = agent.getRecyclingLevel();
        int currentLitter = agent.getLitterLevel();
        int maxCapacity = LitterAgent.MAX_LITTER;
        double currentCapacity = maxCapacity - currentLitter;
        double capacityPercentage = currentCapacity / maxCapacity;
        int viewField = (int) ceil(30 * capacityPercentage);

        Point position = agent.getPosition();
        Cell[][] view = exploredMap.getView(position, viewField);
        Point destination = agent.errorDestination;
        int score = 0;

        for (Cell[] row : view) {
            for (Cell cell : row) {
                if (currentWaste != 0 && currentRecycling == 0) {
                    if (cell instanceof WasteBin) {
                        if (betterBin(cell, destination, score, currentCapacity)) {
                            LitterBin litterBin = (LitterBin) cell;
                            score = litterBin.getTask().getRemaining();
                            destination = cell.getPoint();
                        }
                    }
                } else if (currentWaste == 0 && currentRecycling != 0) {
                    if (cell instanceof RecyclingBin) {
                        RecyclingBin litterBin = (RecyclingBin) cell;
                        if (litterBin.getTask() != null) {
                            if (betterBin(cell, destination, score, currentCapacity)) {
                                score = litterBin.getTask().getRemaining();
                                destination = cell.getPoint();
                            }
                        }
                    }
                } else {
                    if (cell instanceof LitterBin) {
                        LitterBin litterBin = (LitterBin) cell;
                        if (litterBin.getTask() != null) {
                            if (betterBin(cell, destination, score, currentCapacity)) {
                                score = litterBin.getTask().getRemaining();
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
