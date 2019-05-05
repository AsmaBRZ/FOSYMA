package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class CollectBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 699871999896402811L;
	private Agent myAgent;
	private int exitValue;
	
	public CollectBehaviour(Agent agent) {
		super();
		this.myAgent = agent;
	}


	@Override
	public void action() {
		System.out.println("je rentre dans le behaviour collecteur");
		List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
		List<Couple<Observation,Integer>> lObservations= lobs.get(0).getRight();

		for(Couple<Observation,Integer> o:lObservations){
			if ((o.getLeft()==Observation.GOLD || o.getLeft()==Observation.DIAMOND)  && o.getRight()>0 ){
				//si j'ai un sac vide 
				if(((AbstractDedaleAgent)this.myAgent).getBackPackFreeSpace()>0){
					int pick=((AbstractDedaleAgent)this.myAgent).pick();
					if(pick==0){
						System.out.println();
						System.out.println();
						System.out.println();

						System.out.println("je n'ai pas reussi a prendre une partie du trésor ----------------- "+pick);
						System.out.println();
						System.out.println();

						this.exitValue=1;
					}else{
						System.out.println();
						System.out.println();
						System.out.println();

						System.out.println("j'ai reussi a prendre une partie du trésor ----------------- "+pick);
						System.out.println();
						System.out.println();

						this.exitValue=2;
					}
				}
				else{
					System.out.println("je nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn'aaaaaaaaaaaaaaaaaaiiiiiiiiiiiiii passsssssssssssss d'espace"+((AbstractDedaleAgent)this.myAgent).getBackPackFreeSpace());
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
