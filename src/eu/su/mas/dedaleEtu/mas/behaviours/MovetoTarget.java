package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class MovetoTarget extends OneShotBehaviour {
	
	private Agent agent;
	private String targetposition;
	private static final long serialVersionUID = -4590903119174947799L;
	private int exitValue;
	private boolean succ;
	String myPosition;
	public MovetoTarget(Agent agent,String targetposition) {
		super();
		this.targetposition=targetposition;
		this.agent = agent;
		this.exitValue=0;
	}
	
	@Override
	public void action() {
		myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
		System.out.println(myPosition+"***************************************************move action"+this.targetposition);
		succ=((MyAgent)this.agent).moveTo(this.targetposition);
		if(succ) {
			this.exitValue=1;
		}
	}

	@Override
	public int onEnd() {
		System.out.println(myPosition+"***************************************************move end"+this.targetposition);
		return this.exitValue;
	}

}
