package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.Triple;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class CollectBehaviour extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent agent;
	private String myPosition;
	private static final long serialVersionUID = -4590903119174947799L;

	public CollectBehaviour(Agent agent) {
		super();
		this.agent = agent;
	}

	@Override
	public void action() {
		List<String>  pathToTarget;
		if(agent.getLocalName().equals("e1") ){
			//Je suis l'agent c'est moi qui decide quel trésor chercher
			System.out.println("Je suis lagent 1 ! "+agent.getLocalName());
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			//Je cherche le trésor le plus proche de moi
			List<Couple<String,List<Couple<Observation,Integer>>>> obj=((MyAgent)agent).getObjetcsFound();
			System.out.println(agent.getLocalName()+" "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition()+"Tous les trésors découvert sur la carte"+obj);

			List<String> myTargets=new ArrayList<String>();	
			for(int i=0;i<obj.size();i++) {
				String pos=obj.get(i).getLeft();
				List<Couple<Observation,Integer>> element=obj.get(i).getRight();
				myTargets.add(pos);
			}
			//Cette liste contient les distances vers tous les noeuds contenant des trésros
			List<Integer> listDistToTre=new ArrayList<Integer>();
			List<List<String>> listPathToTarget=new ArrayList<List<String>>();
			for(int i=0;i<myTargets.size();i++) {
				//je calcule le chemin le plus plus courts vers mon node myTargets[i]
				pathToTarget=((MyAgent)this.agent).getSPath(this.myPosition, myTargets.get(i));
				listDistToTre.add(pathToTarget.size());	
				listPathToTarget.add(pathToTarget);	
			}
			//recuperer l'index du noeud le plus proche
			int minDis = Collections.min(listDistToTre); 
			Integer minId = listDistToTre.indexOf(minDis);
			//Je recupre mon noeud but contenant le tresorle plus proche de moi
			String target=myTargets.get(minId);
			//je recupere le chemin vers mon target
			pathToTarget=listPathToTarget.get(minId);
			
			
			boolean suc=true;
			int k=0;
			while(suc==true && k<pathToTarget.size()) {
				suc=((MyAgent)this.agent).moveTo(pathToTarget.get(k));
				if(suc) {
					pathToTarget.remove(k);
				}
				System.out.println("we go o o"+pathToTarget.get(k));
				k++;
			}
			
			this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
			//I am on my goal
			
			//remove the obj if success: picking it
			//check if I am pretty well in my target
			
		/*	if(this.myPosition==target)) {
				//try to pick it
				System.out.println("Im on my goal youhouu !");
				//((ExploratorAgent)agent).removeObjectFound(target);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		else {
			System.out.println("Je ne suis pas l'agent 1 ! J'attends qu'on me demande de l'aide!"+agent.getLocalName());
		}
		
	}

}
