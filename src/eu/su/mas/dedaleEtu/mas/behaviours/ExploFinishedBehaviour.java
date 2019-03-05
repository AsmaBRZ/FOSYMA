package eu.su.mas.dedaleEtu.mas.behaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class ExploFinishedBehaviour extends SimpleBehaviour{
	private boolean finished=false;
	private static final long serialVersionUID = -45264489114908982L;
	private Agent agent;
	
	public ExploFinishedBehaviour(Agent agent) {
		super();
		this.myAgent=agent;
	}

	@Override
	public void action() {
		finished=true;
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finished;
	}

}
