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
		System.out.println("je m'appelle "+((AbstractDedaleAgent)this.myAgent).getLocalName()+" je suis dans le behaviour random search");
		//Example to retrieve the current position
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		System.out.println(this.myAgent.getLocalName()+" -- myCurrentPosition is: "+myPosition);
		if (myPosition!=null){
			//List of observable from the agent's current position
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
			/*System.out.println(this.myAgent.getLocalName()+" -- list of observables: "+lobs);
			//list of observations associated to the currentPosition
			List<Couple<Observation,Integer>> lObservations= lobs.get(0).getRight();

			//example related to the use of the backpack for the treasure hunt
			Boolean b=false;
			for(Couple<Observation,Integer> o:lObservations){
				switch (o.getLeft()) {
				case DIAMOND:case GOLD:
					
					System.out.println(this.myAgent.getLocalName()+" - My treasure type is : "+((AbstractDedaleAgent) this.myAgent).getMyTreasureType());
					System.out.println(this.myAgent.getLocalName()+" - My current backpack capacity is:"+ ((AbstractDedaleAgent) this.myAgent).getBackPackFreeSpace());
					System.out.println(this.myAgent.getLocalName()+" - Value of the treasure on the current position: "+o.getLeft() +": "+ o.getRight());
					//System.out.println(this.myAgent.getLocalName()+" - The agent grabbed :"+((AbstractDedaleAgent) this.myAgent).pi());
					System.out.println(this.myAgent.getLocalName()+" - the remaining backpack capacity is: "+ ((AbstractDedaleAgent) this.myAgent).getBackPackFreeSpace());
					b=true;
					break;
				default:
					break;
				}
			}

			//If the agent picked (part of) the treasure
			if (b){
				List<Couple<String,List<Couple<Observation,Integer>>>> lobs2=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
				System.out.println(this.myAgent.getLocalName()+" - State of the observations after trying to pick something "+lobs2);
			}*/
			try {
				Thread.sleep(1000);
				System.out.println("I am sleeeping");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
		
			//Random move from the current position
			Random r= new Random();
			int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move
			System.out.println(this.myAgent.getLocalName()+" I move to "+moveId);
			
			//The move action (if any) should be the last action of your behaviour
			((AbstractDedaleAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
	}
}
}
