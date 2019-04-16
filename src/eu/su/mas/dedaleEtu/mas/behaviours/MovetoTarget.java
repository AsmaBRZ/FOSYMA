package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class MovetoTarget extends OneShotBehaviour {
	
	private Agent agent;
	private static int cpt=0;
	private String targetposition;
	private String myPosition;
	private static final long serialVersionUID = -4590903119174947799L;
	private boolean finished=false;
	private int exitValue;
	public MovetoTarget(Agent agent,String targetposition) {
		super();
		this.targetposition=targetposition;
		this.agent = agent;
	}
	
	@Override
	public void action() {
		((MyAgent)this.agent).moveTo(this.targetposition);
	}
	public boolean getmovevalue(){
		return ((MyAgent)this.agent).moveTo(this.targetposition);
	}

}
