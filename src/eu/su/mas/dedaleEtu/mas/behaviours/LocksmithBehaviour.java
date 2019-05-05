package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class LocksmithBehaviour extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent agent;
	@SuppressWarnings("unused")
	private String myPosition;
	private static final long serialVersionUID = -4590903119174947799L;
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
								
								//j'appelle mes collegues collecteurs pour collecter 
							}else{
								System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" I need help");
								//Je vais appeler mes collegues collecteurs pour m'aider a ouvrir
								this.exitValue=3;
							}

							break;
						default:
							break;
						}
					}
					
				}else{
					((MyAgent)this.agent).setIndex_last_tr(((MyAgent)this.agent).getIndex_last_tr()+1);

				}
				
			
			

		
			
			
			//test if there is no treasor left:
			int somA = (Integer)((MyAgent)this.agent).getIndex_last_tr();
			int somB = (Integer)1;
			if (((MyAgent)this.agent).getmytr().size()!=((Integer)somA) && this.exitValue!=3){
				System.out.println("taille"+((MyAgent)this.agent).getmytr().size()+"index"+((Integer)somA+somB));
				((MyAgent)this.agent).createmycurrentpath();
				System.out.println("je m'appelle "+((MyAgent)this.agent).getLocalName()+" Liste de mes trésors "+((MyAgent)this.agent).getmytr()+"-----------"+"je vais vers le trésor numéro: "+((MyAgent)this.agent).getIndex_last_tr());
				this.exitValue=1;
			}
			else{
				if(this.exitValue!=3){
					((MyAgent)this.agent).setIndex_last_tr(0);
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
