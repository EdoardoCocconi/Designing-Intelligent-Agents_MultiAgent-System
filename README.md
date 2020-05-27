![Nottingham University](Assets/University.png)

<br>

# Designing Intelligent Agents: Multi-Agent System

<br>

<div align="center">
  <img src="Assets/MultiAgent.gif" alt="Multi-Agent System Animation">
</div>

<br>

<p align="center">
  <b><i>Multi-Agent Simulation Output.</i></b>
</p>

<br>

<div align="justify">
  The Multi-Agent Project builds on the Single Agent Project and uses the same 2D environment and task. However, in the second project, multiple cooperating agents must collect waste and recycling from litter bins. Waste must be taken to waste stations and recycling to recycling stations. The goal of the agents is to transport as much waste and recycling as possible in a fixed period of time.
</div>

<br>

## Getting Started

<div align="justify">
  <ul>
    <li>The java source code of the <b>improved agent</b> can be found <a href="src/uk/ac/nott/cs/g53dia/multiagent/DemoLitterAgent.java">here</a>.</li>
    <br>
    <li>The <b>java classes</b> programmed for the functioning of the <b>fleet</b> can be found in the <a href="src/uk/ac/nott/cs/g53dia/multiagent">multiagent package</a>.</li>
    <br>
    <li>The individual agent has been improved since the <a href="https://github.com/EdoardoCocconi/Intelligent-SingleAgent-System">Single Agent Project</a>.</li>
    <br>
    <li>The source code of the <b>environment</b> in which the agent operates can be found in the <a href="src/uk/ac/nott/cs/g53dia/multilibrary">multilibrary package</a>.</li>
    <br>
    <li>To see the agents in action, run the <a href="src/uk/ac/nott/cs/g53dia/multisimulator/MultiSimulator.java">MultiSimulator.java</a>.</li>
    <br>
    <li>To measure the agents performance over 10 runs, run the <a href="src/uk/ac/nott/cs/g53dia/multisimulator/MultiEvaluator.java">MultiEvaluator.java</a>.</li>
  </ul>
</div>

<br>

## Environment

<div align="justify">
  <ul>
    <li>The environment is an infinite 2D grid that contains randomly distributed recycling and waste bins, waste and recycling stations, and recharging points.</li>
    <li>The environment is an infinite 2D grid that contains randomly distributed recycling and waste bins, waste and recycling stations, and recharging points.</li>
    <li>Bins periodically generate tasks to transport a specified amount of recycling or waste (max 100 litres).</li>
    <li>Tasks persist until they are achieved (a bin has at most one task at any time).</li>
    <li>Recycling and waste stations can accept an infinite amount of recycling and waste respectively.</li>
    <li>The agent can recharge at a recharging point.</li>
    <li>The agent's battery capacity is 500.</li>
    <li>Recycling and waste must not be mixed – if the agent has loaded waste it must be taken to a waste station before it collects recycling, and if it has loaded.</li>
    <li>Recycling must be taken to a recycling station before waste is loaded.</li>
    <li>The agent can see any bins, stations and recharging points within 30 cells of its current position.</li>
    <li>If a bin is visible, the agent can see if it has a task, and if so, how much recycling or waste is to be disposed of.</li>
    <li>Move actions take one timestep and consume 1 unit of battery.</li>
    <li>Collecting recycling and waste from a bin and unloading recycling and waste at a station takes one timestep (and consumes no battery).</li>
    <li>The agent starts out at a recharging point with 500 units of battery and no recycling or waste.</li>
    <li>A run lasts 10,000 timesteps unless the agent runs out of battery, in which case the run is terminated.</li>
    <li>The success (score) of the agent is determined by the total amount of recycling and waste collected.</li>
  </ul>
</div>

<div align="justify">
    The multi-agent system must contain at least two agents.
</div>

<br>

## Individual Agent Architecture

<div align="justify">
  The agent has a reactive architecture with hierarchical control. The hierarchy is implemented in the Sense method of DemoLitterAgent as a series of if-conditions. The higher the priority the earlier the condition is checked. If a condition is met, the corresponding behavior is triggered. The behaviors are listed here from highest priority to lowest priority:
  <br><br>
  <ul>
    <li><b>RechargeBehaviour:</b> if the agent is on the target RechargeStation perform a RechargeAction, otherwise MoveTowards RechargeStation.</li>
    <li><b>CollectBehaviour:</b> if the agent is on the target LitterBin perform a LoadAction, otherwise MoveTowards LitterBin.</li>
    <li><b>DisposeBehaviour:</b> if the agent is on the target Station perform a DisposeAction, otherwise MoveTowards Station.</li>
    <li><b>ExploreBehaviour:</b> If the agent is at distance <= 30 from the origin get away from the origin, else MoveTowards origin.</li>
  </ul>
  <br><br>
  The Sense method receives percepts from 3 Sensors:
  <br><br>
  <ul>
    <li><b>RechargeDetector:</b> detects recharge stations inside a radius that is related to how much battery the agent has already lost. If there are recharge stations in this radius, RechargeBehaviour is triggered.</li>
    <li><b>LitterDetector:</b> detects the bin with the highest litter over distance ratio within the specified field of view.</li>
    <li><b>StationDetector:</b> detects the closest station.</li>
  </ul>
  <br><br>
  The sense method runs at every timestep inside senseAndAct and selects a Behaviour. If nothing is detected, the ExploreBehaviour is selected. The selected behaviors is carried out by the Act method inside senseAndAct.
</div>

<div align="justify">
    To learn more about the individual agent click <a href="https://github.com/EdoardoCocconi/Intelligent-SingleAgent-System/blob/master/README.md">here</a>.
</div>

<br>

## How has the individual agent been improved since the first project?

<div align="justify">
    The project 1 agent needed to be optimized to succeed in project 2 because run times were largely exceeding 10 minutes with multiple agents. To increase efficiency, the map has been modified from a single HashMap including all the explored cells to HashMaps and ArrayLists including only non-empty cells of a specific kind. This decreases the number of both read and write operations. During construction, the agents are assigned an “agentID” that distinctively identifies them and a unique exploration direction under the name of “errorDestination”.
</div>

<br>

## Are the agents specialised?

<div align="justify">
    The agents are all of the same kind. In real world applications, different robots usually leverage their unique hardware capabilities to perform the task they excel at. However, in a simulation every agent can be “general purpose” and just read the situation at every instant to determine the best course of action. Therefore, limiting each agent to specific tasks decreases their effectiveness and the overall performance of the fleet.
</div>

<br>

## How many agents are there in the Multi-Agent System (MAS)?

<div align="justify">
    The MAS is composed by 2 agents in total. The agents compete for the same tasks and an increase in the number of agents results in a decrease in the points scored per agent. Therefore, since the agents are not specialized, there is no reason to increase their number above 2. Having only 2 agents also allows for a quick run time, which should be below 10 minutes on most computers.
</div>

<br>

## Organizational & Control Structure

<div align="justify">
    The MAS is a redundant generalist organization. The control structure of the agent is distributed as none of the agents have control over the others and there is no hierarchy.
</div>

<br>

## Agent Communication

<div align="justify">
    Each agent shares with the other agents its current destination and the content of all the non-empty cells it has already encountered. This information is updated every time the “senseAndAct” function is run. This information is updated by calling the respective functions “updateDestinationList” and updateMap, both defined in the “fleetControlCentre” class.
</div>

<br>

## Agent Task Allocation

<div align="justify">
    The agent finds the task with the highest litter over distance ratio in the specified field of view. The field of view is inversely proportional to the current litter capacity of the agent. If the agent is 90% full, the field of view is reduced by 90%. Therefore, the agent won’t look for high value tasks that are far away because the agent could not complete them anyways. Instead, the agent prefers to go to the tasks close to it that can be completed before going to the station. 
    <br>
    The bin with the highest litter over distance ratio is found by the readSensor method of the LitterDetector class. The readSensor method is called in the sense method in DemoLitterAgent. When the field of view is so small that the agent does not see any task, or when the agent is full, the CollectBehaviour does not run anymore.
</div>


<br>

## Why the fleet architecture is appropriate for the project

<div align="justify">
    As explained above, the current design of the “fleetControlCentre” allows to coordinate the agents in such a way that they never target the same task and they never fall victim of changes of plans. Further experiments included preventing an agent to claim a task that close to a task that has already been claimed by another agent. This was done to prevent competition between agents, however no matter the distancing radius, this approach always yielded inferior results. Throughout the project, giving the individual agents the freedom to take decisions based on the current situation has been rewarded with higher scores most of the times.
    <br>
    The performance of the MAS would be greatly decreased if the environment only contains one main task and a number of secondary low value tasks. Only one agent can claim one task and the agents are currently not able to cooperate on a single high value task.
    <br>
    The performance would be increased if the agents could spawn at different points of the environment so they don’t have to compete with each other.
</div>

<br>

## Performance

<div align="justify">
  The performance of the single agent is measured in liters of litter collected during the 10000 timesteps simulation. This measure can be used to put in perspective the average performance of the agents in the Multi-Agent System. The average of the performances is inevitably reduced as the agents compete for the same resources.
</div>

<br>
<br>

<div align="center">
  <img src="Assets/SAscore.jpg" alt="Individual Agent Score Screenshot">
</div>

<br>

<p align="center">
  <b><i>Agent's average score with 1 agent: 3.707E04</i></b>
</p>

<br>

<div align="center">
  <img src="Assets/MAscore.jpg" alt="MAS Score Screenshot">
</div>

<br>

<p align="center">
  <b><i>Agent's average score with 2 agents: 3.475E04</i></b>
</p>

<br>
<br>

> *©  2019  Edoardo  M.  Cocconi  All  Rights  Reserved*
