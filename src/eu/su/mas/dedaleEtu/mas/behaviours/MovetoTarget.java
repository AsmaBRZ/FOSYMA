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
		if(((AbstractDedaleAgent)this.agent).getCurrentPosition().equals(((MyAgent)this.agent).getNodeToVisit())){
			((MyAgent)this.agent).setmoved(succ);
			
			System.out.println(((MyAgent)this.agent).getLocalName()+"---------"+((AbstractDedaleAgent)this.agent).getCurrentPosition()+"    "+((MyAgent)this.agent).getmycurrentpath());
			((MyAgent)this.agent).setcurrentpath();

			
		}
		
		if (((MyAgent)this.agent).getmycurrentpath().size()>0){
			myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			System.out.println("je m'appelle "+((MyAgent)this.agent).getLocalName()+" je suis entrain de me déplacer vers le trésors: "+((MyAgent)this.agent).getmycurrentpath().get(((MyAgent)this.agent).getmycurrentpath().size()-1));
			succ=((AbstractDedaleAgent)this.myAgent).moveTo(((MyAgent)this.agent).getNodeToVisit());
			this.exitValue=2;
		}
		else{
			System.out.println("Je suis sur le trésor");
			//si on est passé par tous les trésors :
			
				((MyAgent)this.agent).setIndex_last_tr(((MyAgent)this.agent).getIndex_last_tr()+1);
				this.exitValue=1;
			
		}
	}

	@Override
	public int onEnd() {
		return this.exitValue;
	}


}
