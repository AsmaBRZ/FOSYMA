package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;

public class MovetoTarget extends OneShotBehaviour {
	
	private Agent agent;
	private String targetposition;
	private static final long serialVersionUID = -4590903119174947799L;
	private int exitValue;
	private boolean succ;
	String myPosition;
	String myPos;
	public MovetoTarget(Agent agent) {
		super();
		//this.targetposition="init";
		this.agent = agent;
	}
	
	@Override
	public void action() {
		if(((AbstractDedaleAgent)this.agent).getCurrentPosition()==((MyAgent)this.agent).getNodeToVisit()){
			((MyAgent)this.agent).setmoved(succ);
			System.out.println("on va modifier le noeud a visiter");
			((MyAgent)this.agent).setcurrentpath();
			
		}
		myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
		System.out.println("je vais vers la position "+((MyAgent)this.agent).getNodeToVisit());
		System.out.println("je suis a la position :"+myPosition);
		succ=((AbstractDedaleAgent)this.myAgent).moveTo(((MyAgent)this.agent).getNodeToVisit());
		if(((MyAgent)this.agent).getmycurrentpath()==null){
			System.out.println("on est arrivé au trésor");
			this.exitValue=1;
		}else{
			System.out.println("Nous allons faire un 2 eme mouvement");
			this.exitValue=2;
		}
	}

	@Override
	public int onEnd() {
		System.out.println("je m'appelle "+((MyAgent)this.agent).getLocalName());
		System.out.println(((AbstractDedaleAgent)this.agent).getCurrentPosition()+"***************************************************move end"+((MyAgent)this.agent).getNodeToVisit());
		return this.exitValue;
	}


}
