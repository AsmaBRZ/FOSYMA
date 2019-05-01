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
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			//System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" je suis dans le behaviour locksmith");
			//System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" ma liste de trésors "+((MyAgent)this.agent).getmytr());

			//open the lock
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((MyAgent)this.agent).observe();
			List<Couple<Observation,Integer>> lObservations= lobs.get(0).getRight();
			//System.out.println("observe "+lobs);
			int i=0;
			boolean trConfirmed=true;
			((MyAgent)this.agent).createmyTr();
			try {
				Thread.sleep(1000);
				System.out.println("I am sleeeping");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
			System.out.println("--------------");
			System.out.println(((MyAgent)this.agent).getmytr().get(((MyAgent)this.agent).getIndex_last_tr()).getLeft());
			System.out.println("--------------");
			System.out.println(lobs.get(0).getLeft());
			System.out.println("--------------");
			// Si je suis bien au trésor target 
			//System.out.println("mimine"+((MyAgent)this.agent).getmytr().get(((MyAgent)this.agent).getIndex_last_tr()));
			if(this.myPosition.contentEquals((CharSequence) ((MyAgent)this.agent).getmytr().get(((MyAgent)this.agent).getIndex_last_tr()).getLeft())) {
				//System.out.println("----!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! here");
				// si le trésor n'a pas été deplacé par le wumpus 
				if(!lobs.get(i).getRight().isEmpty()) {
					Boolean b=false;
					for(Couple<Observation,Integer> o:lObservations){
						switch (o.getLeft()) {
						case DIAMOND:case GOLD:
							
							Observation type=o.getLeft();
							//System.out.println(type+" tyeeeee");
							boolean bool=((AbstractDedaleAgent)this.agent).openLock(type);
							if(bool==true){
								((MyAgent)this.agent).setIndex_last_tr(((MyAgent)this.agent).getIndex_last_tr()+1);
								//System.out.println("myExpertise");
								//System.out.println(((MyAgent)this.agent).getMyExpertise());
								System.out.println("je suis "+((MyAgent)this.agent).getLocalName()+"  j'ai reussi a ouvrir le trésor ------------------------------");
								//System.out.println("-------------------");
								//j'appelle mes potes collecteurs pour collecter 
								//this.exitValue=3;
							}else{
								System.out.println(((AbstractDedaleAgent)this.agent).getMyExpertise() );
								System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" j'ai besoin d'aide ");
								//System.out.println(((AbstractDedaleAgent)this.agent).getLocalName()+" my Expertise "+((MyAgent)this.agent).getMyExpertise());
								//Je vais appeler mes potes collecteur pour m'aider a ouvrir
								this.exitValue=3;
							}
							b=true;
							break;
						default:
							break;
						}
					}
					//les trois dernières elements sont : lochisOpen, LockPicking et strengths
					for (int j=0;j<lobs.get(i).getRight().size()-3;j++){
						
						//Dans les deux cas il faut que j'appelle les collecteurs 
						//soit pour ramasser le trésor ou bien pour m'aider a ouvrir 
					}
				}
				
			}
	
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			//pathToTarget=((MyAgent)this.agent).getTheNearestTrs(this.myPosition);
			String target="init";

			//System.out.println(((MyAgent)this.agent).getLocalName()+"mmmmmmmm "+((MyAgent)this.agent).getmytr())	;		
		
			
			
			//test if there is no treasor left:
			if (((MyAgent)this.agent).getmytr().size()!=((MyAgent)this.agent).getIndex_last_tr() && this.exitValue!=3){
				((MyAgent)this.agent).createmycurrentpath();
				System.out.println("je m'appelle "+((MyAgent)this.agent).getLocalName()+" Liste de mes trésors "+((MyAgent)this.agent).getmytr()+"-----------"+"je vais vers le trésor numéro: "+((MyAgent)this.agent).getIndex_last_tr());
				this.exitValue=1;
			}
			else{
				if(this.exitValue!=3){
					System.out.println("nous avons fini d'ouvrir les trésors ");
					this.exitValue=2;
				}
			}
			
			
			//application du behaviour move:
			/*System.out.println(((MyAgent)this.agent).getmycurrentpath());
			List<String> pathToTarget=((MyAgent)this.agent).getmycurrentpath();
			int suc=1;
			int k=0;
			while(suc== 1 && k<pathToTarget.size()) {
				Behaviour b=new MovetoTarget(this.agent,pathToTarget.get(k));
				b.action();
				suc=b.onEnd();
				
				//suc=((MyAgent)this.agent).moveTo(pathToTarget.get(k));
				//System.out.println(this.myPosition);
				
				if(suc==0){
					System.out.println("on ne peut pas accéder a la case :"+pathToTarget.get(k));
					//gérer l'interblockage
					probleme=true;
				}else{
					System.out.println(this.agent.getLocalName()+"je suis a la case "+this.myPosition);
					((MyAgent)this.agent).moveTo(pathToTarget.get(k));
				}
				
				
				k++;
				
			}
			if(this.myPosition.equals(pathToTarget)){
				System.out.println("on est sur la bonne case ");
			}
			//System.out.println("je m'appelle "+this.agent.getLocalName());
			//System.out.println("ma destination est "+((MyAgent)this.agent).getTheNearestTrs(this.myPosition));
			//System.out.println("je suis à "+this.myPosition);
			//Je suis arrivée sur mon target, je verifie qu'il est bien là
			if(probleme==false){
			
				List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.agent).observe();
				int i=0;
				boolean trConfirmed=true;
				while(trConfirmed==true && i<lobs.size()) {
					System.out.println(lobs);
					System.out.println(lobs.get(i).getLeft().contentEquals(this.myPosition));
					if(lobs.get(i).getLeft().contentEquals(this.myPosition)) {
						System.out.println();
						if(!lobs.get(i).getRight().isEmpty()) {
							Observation type=((AbstractDedaleAgent)this.agent).getMyTreasureType();
							System.out.println("we have the treasor "+lobs.get(i)+ " "+type);
						}else{
							System.out.println("there is no treasor here");
						}
					}
					i++;
				}
				this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
				//I am on my goal
				
				//remove the obj if success: picking it
				//check if I am pretty well in my target
				
				if(this.myPosition==target)) {
					//try to pick it
					System.out.println("Im on my goal youhouu !");
					//((ExploratorAgent)agent).removeObjectFound(target);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
	}
	@Override
	public int onEnd() {
		return this.exitValue;
	}
	
	
}
