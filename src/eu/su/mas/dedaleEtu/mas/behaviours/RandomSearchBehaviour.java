package eu.su.mas.dedaleEtu.mas.behaviours;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class RandomSearchBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 2755999550633064737L;
	private Agent myAgent;
	private int exitValue;
	
	public RandomSearchBehaviour(Agent agent) {
		super();
		this.myAgent = agent;
	}

	@Override
	public void action() {
		//Example to retrieve the current position
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		if (myPosition!=null){
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
			try {
				Thread.sleep(100);
				System.out.println(((AbstractDedaleAgent)this.myAgent).getLocalName()+"I am sleeeping in random search");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
		
			//Random move from the current position
			
			Random r= new Random();
			int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
			System.out.println(this.myAgent.getLocalName()+" I move to "+moveId+" ma position "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition()+"youhhhouuu");
			
			//The move action (if any) should be the last action of your behaviour
			((AbstractDedaleAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
			 lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
			List<Couple<Observation,Integer>> lObservations= lobs.get(0).getRight();
			System.out.println(this.myAgent.getLocalName()+" observation "+lobs);
			//Si je suis sur un trésor 
			if(!lobs.get(0).getRight().isEmpty()) {
				//Si je suis un explorateur et que je trouve un trésor je dois l'ouvrir 
				if(myAgent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentExplo){
					for(Couple<Observation,Integer> o:lObservations){
						if (o.getLeft()==Observation.LOCKSTATUS && o.getRight()==(1) ) 
							this.exitValue=1;
					}
					if(this.exitValue!=1){
						((MyAgent) this.myAgent).createmyTr2(lobs.get(0));
						((MyAgent) this.myAgent).setIndex_last_tr(0);
						System.out.println("je rentre dans open lock a partir de random search");
						this.exitValue=2;
					}
				}
			}else{
				
				this.exitValue=1;
			}
				
			
			
			
		
		}
			
	}
	public int onEnd() {
		return this.exitValue;
	}
}
