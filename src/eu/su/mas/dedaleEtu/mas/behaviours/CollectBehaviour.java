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
		this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
		List<Couple<String,List<Couple<Observation,Integer>>>> obj=((MyAgent)agent).getObjetcsFound();
		//get the best observation
		List<Triple<String,Observation,Integer>> max_per_couple=new ArrayList<Triple<String,Observation,Integer>>();
		
		for(int i=0;i<obj.size();i++) {
			//[<Gold, 31>]
			String pos=obj.get(i).getLeft();
			List<Couple<Observation,Integer>> element=obj.get(i).getRight();
			//get all integer
			List<Integer> list=new ArrayList<Integer>();
			for(int j=0;j<element.size();j++) {
				list.add(element.get(j).getRight());
			}
			//get observation with max value
			int maxVal = Collections.max(list); 
			Integer maxIdx = list.indexOf(maxVal);
			//search for observation associated
			
			//add best pair from a couple<>
			Triple<String,Observation,Integer> p=new Triple<String,Observation,Integer>(pos,element.get(maxIdx).getLeft(),maxIdx);
			max_per_couple.add(p);
		}
		//search for the best observation 
		List<Integer> total=new ArrayList<Integer>();
		for(int j=0;j<max_per_couple.size();j++) {
			total.add(max_per_couple.get(j).getRight());
		}
		int maxVal = Collections.max(total); 
		Integer maxIdx = total.indexOf(maxVal);
		Triple<String,Observation,Integer>  target =max_per_couple.get(maxIdx);
		System.out.println("my target is"+target.getLeft());
		List<String>  pathToTarget=((MyAgent)this.agent).getSPath(this.myPosition, target.getLeft());
		boolean suc=true;
		int k=0;
		while(suc==true && k<pathToTarget.size()) {
			((MyAgent)this.agent).moveTo(pathToTarget.get(k));
			System.out.println("we go o o"+pathToTarget.get(k));
			k++;
		}
		this.myPosition=((AbstractDedaleAgent)this.agent).getCurrentPosition();
		//I am on my goal
		
		//remove the obj if success: picking it
		//check if I am pretty well in my target
		
		if(this.myPosition==target.getLeft()) {
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
	}

}
