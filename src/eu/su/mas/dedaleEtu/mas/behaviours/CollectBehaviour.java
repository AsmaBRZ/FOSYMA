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

public class CollectBehaviour extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent agent;
	private static int cpt=0;
	private String myPosition;
	private static final long serialVersionUID = -4590903119174947799L;
	private boolean finished=false;
	private int exitValue;
	public CollectBehaviour(Agent agent) {
		super();
		this.agent = agent;
	}

	@Override
	public void action() {
			
			boolean probleme=false;
			
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			//pathToTarget=((MyAgent)this.agent).getTheNearestTrs(this.myPosition);
			String target="init";
			List<Couple<String,List<Couple<Observation,Integer>>>> myTr=((MyAgent)this.agent).getmyTr();
			((MyAgent)this.agent).createmycurrentpath();
			
		
			//pick up the treasor 
			
			//test if there is no treasor left:
			if (myTr!=null){
				System.out.println("il reste des trésors ");
				this.exitValue=1;
			}
			else{
				System.out.println("------------------------- ");
				System.out.println("il reste plus des trésors ");
				this.exitValue=2;
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
