package eu.su.mas.dedaleEtu.mas.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class ExploFinishedBehaviour extends OneShotBehaviour{
	private static final long serialVersionUID = -45264489114908982L;
	private Agent agent;
	
	public ExploFinishedBehaviour(Agent agent) {
		super();
		this.myAgent=agent;
	}

	@Override
	public void action() {
		System.out.println("End of exploration confirmed");	
		
	}
	
}
