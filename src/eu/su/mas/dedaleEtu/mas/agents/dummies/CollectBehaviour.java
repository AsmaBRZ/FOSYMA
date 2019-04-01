package eu.su.mas.dedaleEtu.mas.agents.dummies;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class CollectBehaviour extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent agent;
	
	private static final long serialVersionUID = -4590903119174947799L;

	public CollectBehaviour(Agent agent) {
		super();
		this.agent = agent;
	}

	@Override
	public void action() {
		System.out.println(agent.getLocalName()+" I am collecting "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
		
		
	}

}
