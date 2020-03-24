package uk.ac.nott.cs.g53dia.multiagent;

import uk.ac.nott.cs.g53dia.multilibrary.LitterAgent;
import uk.ac.nott.cs.g53dia.multilibrary.Point;

import static sun.security.pkcs11.wrapper.PKCS11Constants.FALSE;
import static sun.security.pkcs11.wrapper.PKCS11Constants.TRUE;


public class RechargeDetector extends Sensor {


    public RechargeDetector(DemoLitterAgent agent) {
        super(agent);
    }


    public Point readSensor(ExploredMap exploredMap) {

        Point position = agent.getPosition();
        Point closestRecharge = agent.exploredMap.rechargePoints.get(0);
        int distanceToRecharge = position.distanceTo(closestRecharge);


        for (Point rechargePoint : agent.exploredMap.rechargePoints) {
            if (position.distanceTo(rechargePoint) < distanceToRecharge) {
                distanceToRecharge = position.distanceTo(rechargePoint);
                closestRecharge = rechargePoint;
            }
        }


        return closestRecharge;

    }


    public boolean isRechargeInRange(ExploredMap exploredMap, long timestep) {

        boolean recharge = FALSE;
        Point closestRecharge = agent.rechargeDetector.readSensor(exploredMap);
        int distance = agent.getPosition().distanceTo(closestRecharge);

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
