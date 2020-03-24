package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static sun.security.pkcs11.wrapper.PKCS11Constants.FALSE;
import static sun.security.pkcs11.wrapper.PKCS11Constants.TRUE;


public class ExploredMap {

    public Map<Point, Cell> map = new HashMap<Point, Cell>();
    public ArrayList<Point> rechargePoints = new ArrayList<Point>();
    public ArrayList<Cell> wasteCells = new ArrayList<Cell>();
    public ArrayList<Cell> recyclingCells = new ArrayList<Cell>();
    public ArrayList<Point> wasteStationPoints = new ArrayList<Point>();
    public ArrayList<Point> recyclingStationPoints = new ArrayList<Point>();

    public void updateMap(Cell[][] view) {

        for (Cell[] row : view) {
            for (Cell cell : row) {
                if (isCellAllowed(cell)) {
                    this.map.put(cell.getPoint(), cell);
                    if (cell instanceof RechargePoint && !rechargePoints.contains(cell.getPoint())) {
                        rechargePoints.add(cell.getPoint());
                    } else if (cell instanceof WasteBin && ((WasteBin) cell).getTask() != null && !wasteCells.contains(cell)) {
                        wasteCells.add(cell);
                    } else if (cell instanceof RecyclingBin && ((RecyclingBin) cell).getTask() != null && !recyclingCells.contains(cell)) {
                        recyclingCells.add(cell);
                    } else if (cell instanceof WasteStation && !wasteStationPoints.contains(cell.getPoint())) {
                        wasteStationPoints.add(cell.getPoint());
                    } else if (cell instanceof RecyclingStation && !recyclingStationPoints.contains(cell.getPoint())) {
                        recyclingStationPoints.add(cell.getPoint());
                    }
                }
            }
        }

    }


    public Cell[][] getView(Point pos, int size) {
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


    public boolean isCellAllowed(Cell cell) {
        if (cell != null) {
            if (cell.getPoint().getX() <= 200 && cell.getPoint().getX() >= -200) {
                if (cell.getPoint().getY() <= 200 && cell.getPoint().getY() >= -200)
                    return TRUE;
            }
        }
        return FALSE;
    }


}
