package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.Cell;
import uk.ac.nott.cs.g53dia.multilibrary.LitterAgent;
import uk.ac.nott.cs.g53dia.multilibrary.Point;
import uk.ac.nott.cs.g53dia.multilibrary.RechargePoint;

import static sun.security.pkcs11.wrapper.PKCS11Constants.FALSE;
import static sun.security.pkcs11.wrapper.PKCS11Constants.TRUE;


public class RechargeDetector extends Sensor{


    public RechargeDetector(LitterAgent agent) {
        super(agent);
    }


    public Point readSensor(ExploredMap exploredMap){

        Point position = agent.getPosition();
        Point destination = new Point(99999999, 99999999);
        int size = 30;

        while (destination.getX() == 99999999 && destination.getY() == 99999999) {

            Cell[][] view = exploredMap.getView(position, size);

            for (Cell[] row : view) {
                for (Cell cell : row) {
                    if (cell instanceof RechargePoint) {
                        if (agent.getPosition().distanceTo(cell.getPoint()) < agent.getPosition().distanceTo(destination)) {
                            destination = cell.getPoint();
                        }
                    }
                }
            }

            size += 10;

        }

        return destination;

    }



    public boolean isRechargeInRange(ExploredMap exploredMap, long timestep) {

        boolean recharge = FALSE;
        Point destination;
        destination = agent.rechargeDetector.readSensor(exploredMap);
        int distance = agent.getPosition().distanceTo(destination);

        double charge = agent.getChargeLevel();
        double maxCharge = LitterAgent.MAX_CHARGE;


        if (agent.agentDestination != null) {

            if (charge <= distance + 2) {
                recharge = TRUE;
            } else if (charge <= maxCharge * 0.9 && distance <= 3) {
                recharge = TRUE;
            } else if (charge <= maxCharge * 0.6 && distance <= 4) {
                recharge = TRUE;
            }
        }

        // Doesn't charge at the end. Only cares about points
        if ((agent.finalTime - timestep) < maxCharge && charge > (agent.finalTime - timestep)) {
            recharge = FALSE;
        }

        return recharge;
    }



}
