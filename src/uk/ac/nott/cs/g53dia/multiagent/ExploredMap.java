package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static sun.security.pkcs11.wrapper.PKCS11Constants.FALSE;
import static sun.security.pkcs11.wrapper.PKCS11Constants.TRUE;


public class ExploredMap {

    public ArrayList<Point> rechargePoints = new ArrayList<>();
    public Map<Point, Cell> wasteCells = new HashMap<>();
    public Map<Point, Cell> recyclingCells = new HashMap<>();
    public ArrayList<Point> wasteStationPoints = new ArrayList<>();
    public ArrayList<Point> recyclingStationPoints = new ArrayList<>();
    final private int mapSize = 200;


    public boolean isCellAllowed(Cell cell) {
        if (cell != null) {
            if (abs(cell.getPoint().getX()) <= mapSize && abs(cell.getPoint().getY()) <= mapSize)
                return TRUE;
        }
        return FALSE;
    }


    public void updateMap(Cell[][] view) {

        for (Cell[] row : view) {
            for (Cell cell : row) {
                if (isCellAllowed(cell)) {
                    if (cell instanceof RechargePoint && !rechargePoints.contains(cell.getPoint())) {
                        rechargePoints.add(cell.getPoint());
                    } else if (cell instanceof WasteBin) {
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


    public Cell[][] getView(Map<Point, Cell> map, Point pos, int size) {
        Cell[][] res = new Cell[size * 2 + 1][size * 2 + 1];
        for (int x = pos.getX() - size; x <= pos.getX() + size; x++) {
            for (int y = pos.getY() - size; y <= pos.getY() + size; y++) {
                int i = x - (pos.getX() - size);
                int j = (2 * size) - (y - (pos.getY() - size));
                res[i][j] = map.get(new Point(x, y));
            }
        }
        return res;
    }


}
