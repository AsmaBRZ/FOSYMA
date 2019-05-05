package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
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
	private static int cpt;
	public MovetoTarget(Agent agent) {
		super();
		//this.targetposition="init";
		this.agent = agent;
		this.cpt=0;
	}
	
	@Override
	public void action() {
	
		if(((AbstractDedaleAgent)this.agent).getCurrentPosition().equals(((MyAgent)this.agent).getNodeToVisit())){
			((MyAgent)this.agent).setmoved(succ);
			
			if(((MyAgent)this.agent).getmycurrentpath().size()>0)
				((MyAgent)this.agent).setcurrentpath();

			
		}
		
		if (((MyAgent)this.agent).getmycurrentpath().size()>0){
			myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			succ=((AbstractDedaleAgent)this.myAgent).moveTo(((MyAgent)this.agent).getNodeToVisit());
			if(succ==false ){
				cpt+=1;
			}
			if(cpt>1){
				Random r= new Random();
				List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
				int moveId=1+r.nextInt(lobs.size()-1);
				((MyAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
				myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
				((MyAgent)this.myAgent).setcurrentpathh(((MyAgent)this.myAgent).getShortestPath(myPosition,((MyAgent)this.agent).getNodeToVisit()));
				cpt=0;
			}
			this.exitValue=2;
		}
		else{
			//si on est passé par tous les trésors :
			
			this.exitValue=1;
			
		}
	}

	@Override
	public int onEnd() {
		return this.exitValue;
	}


}
