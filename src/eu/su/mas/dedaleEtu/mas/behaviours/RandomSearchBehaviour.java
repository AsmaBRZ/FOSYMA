package eu.su.mas.dedaleEtu.mas.behaviours;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class RandomSearchBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = 2755999550633064737L;
	private Agent myAgent;
	
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
				Thread.sleep(5);
				System.out.println("I am sleeeping");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
		
			//Random move from the current position
			
			Random r= new Random();
			int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
			System.out.println(this.myAgent.getLocalName()+" I move to "+moveId+" ma position "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
			
			//The move action (if any) should be the last action of your behaviour
			((AbstractDedaleAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
	}
}
}
