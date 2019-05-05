package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class GoToTankerBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1830223073945721766L;
	private Agent myAgent;
	
	
	public GoToTankerBehaviour(Agent myAgent) {
		super();
		this.myAgent = myAgent;
	}


	@Override
	public void action() {
		String tankerPos=((MyAgent)this.myAgent).getTankerPos();
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		String positionTarget=tankerPos;
		int cpt=0;
		List<String>  pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget); 
		if(pathToTarget.size()>0){
			for(int k=0;k<pathToTarget.size()-1;k++) {
				myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
				pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget);
				k=0;
				boolean succ=((MyAgent)this.myAgent).moveTo(pathToTarget.get(k));
				if(succ==false ){
					cpt+=1;
					k=k-1;
				}
				if(cpt>1){
					Random r= new Random();
					List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
					
					int moveId=1+r.nextInt(lobs.size()-1);
					((MyAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
					myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
					pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget);
					k=0;
					cpt=0;
				}
			}
			//I am arrived 
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaavantttt"+((AbstractDedaleAgent)this.myAgent).getBackPackFreeSpace());
			((MyAgent)this.myAgent).emptyMyBackPack(tankerPos);
			System.out.println("aaaaaaaaaaaapresssssssssssssssss"+((AbstractDedaleAgent)this.myAgent).getBackPackFreeSpace());
		}
		
	}

}
