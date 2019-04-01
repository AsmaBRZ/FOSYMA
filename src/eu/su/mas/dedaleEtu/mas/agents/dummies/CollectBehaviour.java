package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.Pair;
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
		List<Couple<String,List<Couple<Observation,Integer>>>> obj=((ExploratorAgent)agent).getObjetcsFound();
		//get the best observation
		List<Pair<String,Integer>> max_per_couple=new ArrayList<Pair<String,Integer>>();
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
			//add best pair from a couple<>
			Pair<String,Integer> p=new Pair<String,Integer>(pos,maxIdx);
			max_per_couple.add(p);
		}
		//search for the best observation 
		List<Integer> total=new ArrayList<Integer>();
		for(int j=0;j<max_per_couple.size();j++) {
			total.add(max_per_couple.get(j).getRight());
		}
		int maxVal = Collections.max(total); 
		Integer maxIdx = total.indexOf(maxVal);
		Pair<String,Integer>  target =max_per_couple.get(maxIdx);
		System.out.println("my target is"+target.getLeft());
		List<String>  pathToTarget=((ExploratorAgent)this.agent).getSPath(this.myPosition, target.getLeft());
		System.out.println("mon graphe"+((ExploratorAgent)this.agent).getMap().getNodes().toString());/*
		//System.out.println("Path to target"+pathToTarget);
		System.out.println("Ma liste"+pathToTarget.size());
		boolean suc=true;
		int k=0;
		while(suc==true && k<pathToTarget.size()) {
			((ExploratorAgent)this.agent).moveTo(pathToTarget.get(k));
			System.out.println("we go too"+pathToTarget.get(k));
			k++;
		}*/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
