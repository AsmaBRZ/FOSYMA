package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.Triple;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class LocksmithBehaviour extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent agent;
	private String myPosition;
	private static final long serialVersionUID = -4590903119174947799L;
	private boolean finished=false;
	private int exitValue;
	public LocksmithBehaviour(Agent agent) {
		super();
		this.agent = agent;
	}

	@Override
	public void action() {
			//pathToTarget=((MyAgent)this.agent).getTheNearestTrs(this.myPosition);
			
			this.exitValue=30;
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" je suis dans le behaviour locksmith");
			//open the lock
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((MyAgent)this.agent).observe();
			List<Couple<Observation,Integer>> lObservations= lobs.get(0).getRight();
			System.out.println("observe "+lobs);
			
				if(!lobs.get(0).getRight().isEmpty()) {
					for(Couple<Observation,Integer> o:lObservations){
						switch (o.getLeft()) {
						case DIAMOND:case GOLD:
							
							Observation type=o.getLeft();
							System.out.println(type+" tyeeeee");
							boolean bool=((AbstractDedaleAgent)this.agent).openLock(type);
							if(bool==true){
								System.out.println("je suis "+((MyAgent)this.agent).getLocalName()+"  j'ai reussi a ouvrir le trésor. la liste des trésors "+((MyAgent)this.agent).getmytr()+" index du dernier tresor "+((MyAgent)this.agent).getIndex_last_tr());
								System.out.println("-------------------");
								((MyAgent)this.agent).setIndex_last_tr(((MyAgent)this.agent).getIndex_last_tr()+1);
								
								//j'appelle mes potes collecteurs pour collecter 
							}else{
								System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" j'ai besoin d'aide yaaa");
								//Je vais appeler mes potes collecteur pour m'aider a ouvrir
								this.exitValue=3;
							}

							break;
						default:
							break;
						}
					}
					
				}
				
			
			

		
			
			
			//test if there is no treasor left:
			
			if (((MyAgent)this.agent).getmytr().size()!=((MyAgent)this.agent).getIndex_last_tr() && this.exitValue!=3){
				((MyAgent)this.agent).createmycurrentpath();
				System.out.println("je m'appelle "+((MyAgent)this.agent).getLocalName()+" Liste de mes trésors "+((MyAgent)this.agent).getmytr()+"-----------"+"je vais vers le trésor numéro: "+((MyAgent)this.agent).getIndex_last_tr());
				this.exitValue=1;
			}
			else{
				if(this.exitValue!=3){
					System.out.println("je vais faire un random search");
					this.exitValue=2;
				}
			}
			
			
	}
	@Override
	public int onEnd() {
		return this.exitValue;
	}
	
	
}
