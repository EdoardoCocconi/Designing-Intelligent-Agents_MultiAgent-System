package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.ceil;


public class LitterDetector extends Sensor {


    public LitterDetector(DemoLitterAgent agent) {
        super(agent);
    }


    public boolean bestBin(Cell cell, Point previousDestination, int previousScore, double currentCapacity, int viewField) {
        if(agent.fleetControlCentre.isAlreadyOccupied(cell))
            return FALSE;
        int previousDistance = agent.getPosition().distanceTo(previousDestination);
        int distance = agent.getPosition().distanceTo(cell.getPoint());
        if (distance <= viewField) {
            LitterBin litterBin = (LitterBin) cell;
            if (litterBin.getTask() != null) {
                int score = litterBin.getTask().getRemaining();
                if (score < currentCapacity) {
                    if (score / (distance + 1) > previousScore / (previousDistance + 1))
                        return TRUE;
                }
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

        Point bestBin = agent.errorDestination;
        int score = 0;

        if (currentWaste != 0 && currentRecycling == 0) {
            for (Map.Entry<Point, Cell> entry : agent.fleetControlCentre.exploredMap.wasteCells.entrySet()) {
                Cell wasteBin = entry.getValue();
                if (bestBin(wasteBin, bestBin, score, currentCapacity, viewField)) {
                    LitterBin litterBin = (LitterBin) wasteBin;
                    score = litterBin.getTask().getRemaining();
                    bestBin = wasteBin.getPoint();
                }
            }
        } else if (currentWaste == 0 && currentRecycling != 0) {
            for (Map.Entry<Point, Cell> entry : agent.fleetControlCentre.exploredMap.recyclingCells.entrySet()) {
                Cell recyclingBin = entry.getValue();
                if (bestBin(recyclingBin, bestBin, score, currentCapacity, viewField)) {
                    LitterBin litterBin = (LitterBin) recyclingBin;
                    score = litterBin.getTask().getRemaining();
                    bestBin = recyclingBin.getPoint();
                }
            }
        } else {

            for (Map.Entry<Point, Cell> entry : agent.fleetControlCentre.exploredMap.wasteCells.entrySet()) {
                Cell wasteBin = entry.getValue();
                if (bestBin(wasteBin, bestBin, score, currentCapacity, viewField)) {
                    LitterBin litterBin = (LitterBin) wasteBin;
                    score = litterBin.getTask().getRemaining();
                    bestBin = wasteBin.getPoint();
                }
            }

            for (Map.Entry<Point, Cell> entry : agent.fleetControlCentre.exploredMap.recyclingCells.entrySet()) {
                Cell recyclingBin = entry.getValue();
                if (bestBin(recyclingBin, bestBin, score, currentCapacity, viewField)) {
                    LitterBin litterBin = (LitterBin) recyclingBin;
                    score = litterBin.getTask().getRemaining();
                    bestBin = recyclingBin.getPoint();
                }

            }

        }

        return bestBin;

    }


}
