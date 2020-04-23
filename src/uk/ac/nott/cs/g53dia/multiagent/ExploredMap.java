package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploredMap {

    public ArrayList<Point> rechargePoints = new ArrayList<>();
    public Map<Point, Cell> wasteCells = new HashMap<>();
    public Map<Point, Cell> recyclingCells = new HashMap<>();
    public ArrayList<Point> wasteStationPoints = new ArrayList<>();
    public ArrayList<Point> recyclingStationPoints = new ArrayList<>();

    public void updateMap(Cell[][] view) {

        for (Cell[] row : view) {
            for (Cell cell : row) {
                if (cell instanceof RechargePoint) {
                    rechargePoints.add(cell.getPoint());
                } else if (cell instanceof WasteBin && !wasteCells.containsKey(cell.getPoint()) && ((WasteBin) cell).getTask() != null) {
                    wasteCells.put(cell.getPoint(), cell);
                } else if (cell instanceof RecyclingBin) {
                    recyclingCells.put(cell.getPoint(), cell);
                } else if (cell instanceof WasteStation && !wasteStationPoints.contains(cell.getPoint())) {
                    wasteStationPoints.add(cell.getPoint());
                } else if (cell instanceof RecyclingStation && !recyclingStationPoints.contains(cell.getPoint())) {
                    recyclingStationPoints.add(cell.getPoint());
                }
            }
        }

    }


}
